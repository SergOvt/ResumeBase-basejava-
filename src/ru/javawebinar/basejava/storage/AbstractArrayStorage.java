package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    final static int STORAGE_LIMIT = 10000;
    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected boolean isExist(Object index) {
        return (Integer)index > -1;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage[(Integer)index] = r;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertElement(r, (Integer)index);
            size++;
        }
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer)index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Object index){
        return storage[(Integer)index];
    }

    protected abstract Integer getKeyOrIndex(String uuid);
}
