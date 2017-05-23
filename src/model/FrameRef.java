package model;

import org.jetbrains.annotations.NotNull;

public class FrameRef {
    private String reference;

    public FrameRef(@NotNull Frame frame) {
        try {
            this.reference = frame.name();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(
                    "Frame ref constructor must receive a non-null frame");
        }
    }

    public Frame retrieve() {
        return KnowledgeBase.retrieve(this.reference);
    }
}
