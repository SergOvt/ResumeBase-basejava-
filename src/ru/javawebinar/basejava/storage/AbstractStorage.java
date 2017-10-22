package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractStorage implements Storage {

    protected static int STORAGE_LIMIT;
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        doClear();
        size = 0;
    }

    public void update(Resume r) {
        if (isExist(r)) {
            doUpdate(r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public abstract Resume[] getAll();

    public void save(Resume r) {
        if (isExist(r)) {
            throw new ExistStorageException(r.getUuid());
        } else if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            doSave(r);
            size++;
        }
    }

    public void delete(String uuid) {
        if (isExist(new Resume(uuid))) {
            doDelete(uuid);
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        if (!isExist(new Resume(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid);
    }

    protected abstract int getIndex(Resume r);

    protected abstract void doClear();

    protected boolean isExist(Resume r) {
        return getIndex(r) > -1;
    }

    protected abstract void doUpdate(Resume r);

    protected abstract void doSave(Resume r);

    protected abstract void doDelete(String uuid);

    protected abstract Resume doGet(String uuid);

}
