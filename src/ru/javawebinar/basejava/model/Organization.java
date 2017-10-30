package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class Organization {

    private final Link homePage;
    private final String title;
    private final List<OrganizationContent> organizationContent;

    public Organization(String name, String url, String title, List<OrganizationContent> organizationContent) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(organizationContent, "content must not be null");
        if (organizationContent.isEmpty()) throw new IllegalArgumentException("content must not be empty");
        this.homePage = new Link(name, url);
        this.title = title;
        this.organizationContent = organizationContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        if (!title.equals(that.title)) return false;
        return organizationContent.equals(that.organizationContent);
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + organizationContent.hashCode();
        return result;
    }

}
