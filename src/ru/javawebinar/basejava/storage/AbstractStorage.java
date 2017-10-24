package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractStorage implements Storage {

    public abstract int size();

    public abstract void clear();

    public void update(Resume r) {
        doUpdate(r, getExistKey(r.getUuid()));
    }

    public abstract Resume[] getAll();

    public void save(Resume r) {
        Object existKey = getKeyOrIndex(r.getUuid());
        if (isExist(existKey)) {
            throw new ExistStorageException(r.getUuid());
        } else doSave(r, existKey);
    }

    public void delete(String uuid) {
        doDelete(getExistKey(uuid));
    }

    public Resume get(String uuid) {
        return doGet(getExistKey(uuid));
    }

    protected abstract boolean isExist(Object key);

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract void doSave(Resume r, Object key);

    protected abstract void doDelete(Object key);

    protected abstract Resume doGet(Object key);

    protected abstract Object getKeyOrIndex(String uuid);

    private Object getExistKey (String uuid) {
        Object existKey = getKeyOrIndex(uuid);
        if (!isExist(existKey))
            throw new NotExistStorageException(uuid);
        return existKey;
    }

}
