package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Name4");

    static {
        RESUME_1.setContact(PHONE, "8(495)000-00-01");
        RESUME_1.setContact(MAIL, "example1@mail.ru");
        RESUME_2.setContact(PHONE, "8(495)000-00-02");
        RESUME_2.setContact(MAIL, "example2@mail.ru");
        RESUME_3.setContact(PHONE, "8(495)000-00-03");
        RESUME_3.setContact(MAIL, "example3@mail.ru");
        RESUME_4.setContact(PHONE, "8(495)000-00-04");
        RESUME_4.setContact(MAIL, "example4@mail.ru");

        RESUME_1.setSection(PERSONAL, new TextSection("Блабла1"));
        RESUME_1.setSection(OBJECTIVE, new TextSection("Должность1"));
        RESUME_1.setSection(ACHIEVEMENT, new ListSection(Arrays.asList("что-то1", "что-то2")));
        RESUME_1.setSection(QUALIFICATIONS, new ListSection(Arrays.asList("знания1", "знания2")));
        RESUME_1.setSection(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Организация1", null, "Организация1", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "работа1"))))));
        RESUME_1.setSection(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Заведение1", "URL1", "Заведение1", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "учеба1"))))));

        RESUME_2.setSection(PERSONAL, new TextSection("Блабла2"));
        RESUME_2.setSection(OBJECTIVE, new TextSection("Должность2"));
        RESUME_2.setSection(ACHIEVEMENT, new ListSection(Arrays.asList("что-то")));
        RESUME_2.setSection(QUALIFICATIONS, new ListSection(Arrays.asList("знания")));
        RESUME_2.setSection(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Организация2", "URL2", "Организация2", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), null))))));
        RESUME_2.setSection(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Заведение2", "URL2", "Заведение2", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "учеба2"))))));

        RESUME_3.setSection(PERSONAL, new TextSection("Блабла3"));
        RESUME_3.setSection(OBJECTIVE, new TextSection("Должность3"));
        RESUME_3.setSection(ACHIEVEMENT, new ListSection(Arrays.asList("что-то1", "что-то2")));
        RESUME_3.setSection(QUALIFICATIONS, new ListSection(Arrays.asList("знания1", "знания2")));
        RESUME_3.setSection(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Организация3", "URL3", "Организация3", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "работа3"))))));
        RESUME_3.setSection(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Заведение3", "URL3", "Заведение3", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), null))))));

        RESUME_4.setSection(PERSONAL, new TextSection("Блабла4"));
        RESUME_4.setSection(OBJECTIVE, new TextSection("Должность4"));
        RESUME_4.setSection(ACHIEVEMENT, new ListSection(Arrays.asList("что-то1", "что-то2", "что-то3")));
        RESUME_4.setSection(QUALIFICATIONS, new ListSection(Arrays.asList("знания1", "знания2")));
        RESUME_4.setSection(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Организация4", "URL4", "Организация4", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "работа4"))))));
        RESUME_4.setSection(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Заведение4", "URL4", "Заведение4", Arrays.asList(
                        new OrganizationContent(LocalDate.of(2000, 1,1),
                                LocalDate.of(2001, 1,1), "учеба4"))))));

    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}