package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    static {
        STORAGE_LIMIT = Integer.MAX_VALUE - 8; // size() is int, do like ListStorage
    }

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    protected int getIndex (Resume r) {
        return -1;  // not allowed here
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected boolean isExist(Resume r) {
        return storage.containsKey(r.getUuid());
    }

    @Override
    protected void doUpdate(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public void doSave(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    public void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume doGet(String uuid) {
        return storage.get(uuid);
    }
}
