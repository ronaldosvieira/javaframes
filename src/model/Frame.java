package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.constraint.Constraint;
import model.constraint.ContainsConstraint;
import model.constraint.RangeConstraint;
import model.constraint.TypeConstraint;
import model.util.ClassTypeAdapterFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Frame implements Cloneable {
    private String name;
	protected FrameRef parent;
	private Map<String, Slot> slots;
	
	public Frame(String name) {
	    this.name = name;
	    this.slots = new HashMap<>();
	}
	
	public Frame(String name, GenericFrame parent) {
        this(name);
        this.parent = parent.ref();
    }

    public Frame(Frame frame) {
	    this.name = frame.name;
	    this.parent = frame.parent;
	    this.slots = new HashMap<>();

        for (String slot : frame.slots.keySet()) {
            this.slots.put(slot, new Slot(frame.slots.get(slot)));
        }
    }

    public String name() {return this.name;}
	public GenericFrame parent() {
	    return this.parent != null? (GenericFrame) this.parent.retrieve() : null;
	}
	public Set<String> slots() {return this.slots.keySet();}

	public void setName(String name) {this.name = name;}
    public void setParent(GenericFrame parent) {this.parent = parent.ref();}
    public void setParent(FrameRef ref) {this.parent = ref;}

    public boolean isA(String type) {
	    return name().equals(type) || (parent() != null && parent().isA(type));
    }

    public boolean isA(GenericFrame type) {return this.isA(type.name());}

    public boolean contains(String key) {
	    return slots.containsKey(key);
    }
    
    protected Slot find(String key) throws NoSuchElementException {
	    if (this.contains(key))
	        return slots.get(key);
	    else if (parent != null && parent.retrieve().contains(key))
	        return parent.retrieve().find(key);
	    else
	        throw new NoSuchElementException("Slot '" + key
                    + "' not found on frame " + name());
    }

	public Object get(String key) throws NoSuchElementException {
	    return this.find(key).getValue();
	}

	public <T> T get(String key, Class<T> type) throws ClassCastException {
        Object value = this.get(key);

	    try {
            return type.cast(value);
        } catch (ClassCastException e) {
            throw new ClassCastException("Slot '" + key + "' is of type "
                    + value.getClass().getSimpleName() + ". "
                    + type.getSimpleName() + " given.");
        }
	}
	
	public <T> void set(String key, T value) {
	    try {
	        Slot slot = this.find(key);
	        slot.setValue(value);

	        slots.put(key, slot);
        } catch (NoSuchElementException e) {
            slots.put(key, new Slot(value));
        }
	}

	public void ifAdded(String key, Consumer<Object> if_added) {
	    Slot slot;

	    try {
	        slot = this.find(key);
        } catch (NoSuchElementException e) {
	        slot = new Slot();
        }

	    slot.setIfAdded(if_added);
	    slots.put(key, slot);
    }

    public void ifNeeded(String key, Supplier<Object> if_needed) {
		Slot slot;

		try {
			slot = this.find(key);
		} catch (NoSuchElementException e) {
			slot = new Slot();
		}

		slot.setIfNeeded(if_needed);
		slots.put(key, slot);
	}

	public void addConstraint(String key, Constraint constraint) {
	    Slot slot;

	    try {
	        slot = this.find(key);
        } catch (NoSuchElementException e) {
	        slot = new Slot();
        }

        slot.addConstraint(constraint);
	    slots.put(key, slot);
    }

    public FrameRef ref() {return new FrameRef(this);}

    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        builder.serializeNulls();

	    return builder.create().toJson(this);
    }

    public static Frame fromJson(String json) {
	    Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonObject frameJson = parser.parse(json).getAsJsonObject();
        Frame frame = new GenericFrame(frameJson.get("name").getAsString());

        JsonElement parent = frameJson.get("parent");
        if (!parent.isJsonNull()) {
            frame.setParent(gson.fromJson(parent, FrameRef.class));
        }

        JsonObject slots = frameJson.get("slots").getAsJsonObject();
        for (Map.Entry<String, JsonElement> slot : slots.entrySet()) {
            String key = slot.getKey();
            JsonObject value = slot.getValue().getAsJsonObject();

            if (!value.get("value").isJsonNull())
                frame.set(key, gson.fromJson(value.get("value"), Object.class));

            /*
            if (!value.get("if_added").isJsonNull())
                frame.ifAdded(key, gson.fromJson(value.get("if_added"),
                        new TypeToken<Consumer<Object>>(){}.getType()));

            if (!value.get("if_needed").isJsonNull())
                frame.ifNeeded(key, gson.fromJson(value.get("if_needed"),
                        new TypeToken<Supplier<Object>>(){}.getType()));
            */

            if (!value.get("constraints").isJsonNull()) {
                for (JsonElement constraint : value.get("constraints").getAsJsonArray()) {
                    JsonObject constraintObj = constraint.getAsJsonObject();
                    String constrType = constraintObj.get("constraint").getAsString();
                    Constraint constr;
                    try {
                        switch (constrType) {
                            case "type":
                                String className = constraintObj.get("type").getAsString();
                                constr = new TypeConstraint(Class.forName(className));
                                break;

                            case "contains":
                                JsonArray accepts = constraintObj.get("accepts").getAsJsonArray();
                                constr = new ContainsConstraint(gson.fromJson(accepts, ArrayList.class));
                                break;

                            case "range":
                                Comparable lo = (Comparable) gson.fromJson(constraintObj.get("lo"), Object.class);
                                Comparable hi = (Comparable) gson.fromJson(constraintObj.get("hi"), Object.class);
                                JsonArray bounds = constraintObj.get("bounds").getAsJsonArray();
                                String inclusivity = bounds.get(0).getAsString() + bounds.get(1).getAsString();

                                constr = new RangeConstraint(lo, hi, inclusivity);
                                break;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return frame;
    }
}
