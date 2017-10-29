package ru.javawebinar.basejava.model;

import java.util.List;

public class ExperienceTypeSection extends Section {

    private List<ExperienceType> section;

    public ExperienceTypeSection(List<ExperienceType> section) {
        this.section = section;
    }

    public List<ExperienceType> getSection() {
        return section;
    }

    @Override
    public String toString() {
        return section.toString();
    }
}
