package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

// TODO implement
// TODO create new Mapmap with search key not uuid

public class MapUuidStorage extends AbstractStorage {
   
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected boolean isExist(Object key) {
        return map.containsKey(String.valueOf(key));
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        map.put((String)key, r);
    }

    @Override
    public void doSave(Resume r, Object key) {
        map.put((String)key, r);
    }

    @Override
    public void doDelete(Object key) {
        map.remove(String.valueOf(key));
    }

    @Override
    public Resume doGet(Object key) {
        return map.get(String.valueOf(key));
    }

    @Override
    protected String getSearchKey (String uuid) {
        return uuid;

    }
}
