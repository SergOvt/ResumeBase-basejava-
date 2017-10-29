package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestResume {

    public static void main(String[] args) {

        Resume r = new Resume("Вася Пупкин");
        r.getContacts().put("E-mail", "Pupkin@mail.ru");
        r.getSections().put(SectionType.PERSONAL, r.new SingleTextSection("Уверен в себе"));
        r.getSections().put(SectionType.OBJECTIVE, r.new SingleTextSection("Директор"));
        List<String> achievements = new ArrayList<>();
        achievements.add("нету");
        r.getSections().put(SectionType.ACHIEVEMENT, r.new MultiTextSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("руководить");
        qualifications.add("управлять");
        r.getSections().put(SectionType.QUALIFICATIONS, r.new MultiTextSection(qualifications));
        List<Resume.ExperienceType> experience = new ArrayList<>();
        experience.add(r.new ExperienceType("Дом", "всю жизнь", "Просмотр телевизора, потребление пищи"));
        r.getSections().put(SectionType.EXPERIENCE, r.new ExperienceTypeSection(experience));
        List<Resume.ExperienceType> education = new ArrayList<>();
        education.add(r.new ExperienceType("Сельская школа", "09/1990 - 10/1990", "Надоело учиться"));
        r.getSections().put(SectionType.EDUCATION, r.new ExperienceTypeSection(education));

        for (Map.Entry<String, String> map : r.getContacts().entrySet()) {
            System.out.println(map.getKey() + " : " + map.getValue());
        }
        for (Map.Entry<SectionType, Resume.Section> map : r.getSections().entrySet()) {
            System.out.println(map.getKey().getTitle());
            System.out.println(map.getValue().toString());
        }
    }
}
