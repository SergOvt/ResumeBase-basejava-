package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    public void save(Resume r) {
        if (r == null || r.getUuid() == null || get(r.getUuid()) != null) {
            System.out.println("Недопустимый элемент");
            return;
        }
        if (size < 10000) storage[size++] = r;
        else System.out.println("Переполнение");
    }

    public void update(Resume r) {
        if (r == null || get(r.getUuid()) == null) {
            System.out.println("Недопустимый элемент");
            return;
        }
        delete(r.getUuid());
        save(r);
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++)
            if (uuid != null && storage[i].getUuid().equals(uuid)) return storage[i];
        System.out.println("Недопустимый элемент");
        return null;
    }

    public void delete(String uuid) {
        if (uuid == null || get(uuid) == null) {
            System.out.println("Недопустимый элемент");
            return;
        }
        for (int i = 0; i < size; i++)
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[--size];
                storage[size] = null;
                break;
            }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    public int size() {
        return size;
    }
}
