package javaframes;

public class Property<T> {
	private T value;
	
	public Property(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
	public Class<? extends Object> getType() {
		return this.value.getClass();
	}
}