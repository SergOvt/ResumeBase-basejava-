package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid3";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid1";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

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
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        assertSize(3);
    }

    @Test
    public void getAll() throws Exception {
        assertSize(storage.getAll().length);
        assertOrder(storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume RESUME_0 = new Resume("uuid0");
        storage.save(RESUME_0);
        Assert.assertEquals(RESUME_0, storage.get("uuid0"));
        assertSize(4);
        assertPosition(RESUME_0, storage.getAll());
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.delete(UUID_2);
        assertSize(1);
        storage.delete(UUID_3);
        assertSize(0);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT - 2; i++) {
            storage.save(new Resume());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }


    abstract void assertOrder(Resume[] allResume);

    abstract void assertPosition(Resume resume, Resume[] allResume);

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

}