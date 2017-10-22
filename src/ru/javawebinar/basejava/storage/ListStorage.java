package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    static {
        STORAGE_LIMIT = Integer.MAX_VALUE - 8; // Like java.util.ArrayList
    }

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected int getIndex (Resume r) {
        return storage.indexOf(r);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r) {
        storage.set(getIndex(r), r);
    }

    @Override
    public void doSave(Resume r) {
            storage.add(r);
    }

    @Override
    public void doDelete(String uuid) {
        storage.remove(getIndex(new Resume(uuid)));
    }

    @Override
    public Resume doGet(String uuid) {
        return storage.get(getIndex(new Resume(uuid)));
    }

}
