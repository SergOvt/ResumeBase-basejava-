package ru.javawebinar.basejava.model;

import java.util.List;

public class MultiTextSection extends Section {

    private List<String> section;

    public MultiTextSection(List<String> section) {
        this.section = section;
    }

    public List<String> getSection() {
        return section;
    }

    @Override
    public String toString() {
        return section.toString();
    }
}
