package ru.javawebinar.basejava.model;

public class SingleTextSection extends Section {

    private String section;

    public SingleTextSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return section;
    }
}
