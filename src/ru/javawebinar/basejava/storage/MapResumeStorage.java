package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{

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
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        map.put(((Resume)resume).getUuid(), r);
    }

    @Override
    public void doSave(Resume r, Object resume) {
        map.put(((Resume)resume).getUuid(), r);
    }

    @Override
    public void doDelete(Object resume) {
        map.remove(((Resume)resume).getUuid());
    }

    @Override
    public Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected Resume getSearchKey (String uuid) {
        return map.get(uuid);
    }
}
