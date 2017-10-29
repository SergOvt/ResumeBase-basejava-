package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;


    private final Map<String, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }




    public abstract class Section {

        abstract Object getSection();
    }


    public class SingleTextSection extends Section{
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


    public class MultiTextSection extends Section{
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


    public class ExperienceTypeSection extends Section{
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


    public class ExperienceType{
        private String title;
        private String date;
        private String content;

        public ExperienceType(String title, String date, String content) {
            this.title = title;
            this.date = date;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return title + '\n' + date + '\t' + content;
        }
    }
}