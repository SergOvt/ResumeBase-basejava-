package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        if (!storage.containsKey(r.getUuid())) throw new NotExistStorageException(r.getUuid());
        else storage.put(r.getUuid(), r);
    }

    @Override
    public void save(Resume r) {
        if (storage.containsKey(r.getUuid())) throw new ExistStorageException(r.getUuid());
        else storage.put(r.getUuid(), r);
    }

    @Override
    public Resume get(String uuid) {
        if (!storage.containsKey(uuid)) throw new NotExistStorageException(uuid);
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (!storage.containsKey(uuid)) throw new NotExistStorageException(uuid);
        else storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
