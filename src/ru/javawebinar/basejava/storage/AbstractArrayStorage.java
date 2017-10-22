package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    static {
        STORAGE_LIMIT = 10000;
    }

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
    }

    @Override
    protected void doUpdate(Resume r) {
        storage[getIndex(r)] = r;
    }

    @Override
    protected void doSave(Resume r) {
        insertElement(r, getIndex(r));
    }

    @Override
    protected void doDelete(String uuid) {
        fillDeletedElement(getIndex(new Resume(uuid)));
        storage[size - 1] = null;
    }

    @Override
    protected Resume doGet(String uuid){
        return storage[getIndex(new Resume(uuid))];
    }
}
