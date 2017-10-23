package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

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
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer)index > -1;
    }

    protected Integer getKeyOrIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++)
            if (storage.get(i).getUuid().equals(uuid)) return i;
        return -1;
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage.set((Integer)index, r);
    }

    @Override
    public void doSave(Resume r, Object index) {
            storage.add(r);
    }

    @Override
    public void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    public Resume doGet(Object index) {
        return storage.get((Integer)index);
    }

}
