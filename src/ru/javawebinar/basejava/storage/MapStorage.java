package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    protected boolean isExist(Object key) {
        return storage.containsKey((String)key);
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.put((String)key, r);
    }

    @Override
    public void doSave(Resume r, Object key) {
        storage.put((String)key, r);
    }

    @Override
    public void doDelete(Object key) {
        storage.remove((String)key);
    }

    @Override
    public Resume doGet(Object key) {
        return storage.get((String)key);
    }

    @Override
    protected String getKeyOrIndex(String uuid) {
        return uuid;
    }


}
