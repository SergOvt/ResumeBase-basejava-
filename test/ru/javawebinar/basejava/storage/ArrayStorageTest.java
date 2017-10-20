package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest{

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    protected void assertOrder(Resume[] allResume) {
        // order not important
    }

    @Override
    protected void assertPosition(Resume resume, Resume[] allResume) {
        // position not important
    }


}