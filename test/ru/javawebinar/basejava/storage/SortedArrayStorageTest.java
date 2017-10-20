package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Override
    protected void assertOrder(Resume[] allResume) {
        Assert.assertEquals("uuid1", allResume[0].getUuid());
        Assert.assertEquals("uuid2", allResume[1].getUuid());
        Assert.assertEquals("uuid3", allResume[2].getUuid());
    }

    @Override
    protected void assertPosition(Resume resume, Resume[] allResume) {
        Assert.assertEquals(resume, allResume[0]);
    }

}