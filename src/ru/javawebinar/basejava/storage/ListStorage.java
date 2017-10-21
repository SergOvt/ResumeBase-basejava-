package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
            storage.set(getExistIndex(r), r);
    }

    @Override
    public void save(Resume r) {
        int index = storage.indexOf(r);
        if (index != -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(getExistIndex(new Resume(uuid)));
    }

    @Override
    public void delete(String uuid) {
            storage.remove(getExistIndex(new Resume(uuid)));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    private int getExistIndex (Resume r) {
        int index = storage.indexOf(r);
        if (index == -1)
            throw new NotExistStorageException(r.getUuid());
        return index;
    }
}
