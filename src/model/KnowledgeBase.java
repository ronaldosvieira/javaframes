package model;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class KnowledgeBase {
    private static HashMap<String, Frame> frames = new HashMap<>();

    public KnowledgeBase() {
        frames = new HashMap<>();
    }

    public static Frame retrieve(String name) {
        Frame res = frames.get(name);

        if (res == null) throw new NoSuchElementException();

        return res;
    }

    public static void put(Frame frame) {
        frames.put(frame.name(), frame);
    }
}
