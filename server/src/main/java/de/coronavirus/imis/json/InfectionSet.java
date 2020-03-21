package de.coronavirus.imis.json;

public class InfectionSet {

    private final long id;
    private final String content;

    public InfectionSet(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
