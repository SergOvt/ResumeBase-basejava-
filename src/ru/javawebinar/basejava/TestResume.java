package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestResume {

    public static void main(String[] args) {

        Resume r = new Resume("Вася Пупкин");
        r.getContacts().put(Contacts.EMAIL, "Pupkin@mail.ru");
        r.getSections().put(SectionType.PERSONAL, new SingleTextSection("Уверен в себе"));
        r.getSections().put(SectionType.OBJECTIVE, new SingleTextSection("Директор"));
        List<String> achievements = new ArrayList<>();
        achievements.add("нету");
        r.getSections().put(SectionType.ACHIEVEMENT, new MultiTextSection(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("руководить");
        qualifications.add("управлять");
        r.getSections().put(SectionType.QUALIFICATIONS, new MultiTextSection(qualifications));
        List<ExperienceType> experience = new ArrayList<>();
        experience.add(new ExperienceType("Дом", LocalDate.of(1980, 1, 1), LocalDate.now(),"Просмотр телевизора, потребление пищи"));
        r.getSections().put(SectionType.EXPERIENCE, new ExperienceTypeSection(experience));
        List<ExperienceType> education = new ArrayList<>();
        education.add(new ExperienceType("Сельская школа", LocalDate.of(1990, 9, 1), LocalDate.of(1990, 10, 1), "Надоело учиться"));
        r.getSections().put(SectionType.EDUCATION, new ExperienceTypeSection(education));

        for (Map.Entry<Contacts, String> map : r.getContacts().entrySet()) {
            System.out.println(map.getKey().getTitle() + " : " + map.getValue());
        }
        for (Map.Entry<SectionType, Section> map : r.getSections().entrySet()) {
            System.out.println(map.getKey().getTitle());
            System.out.println(map.getValue().toString());
        }
    }
}
