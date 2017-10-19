package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    public void deleteByIndex(int index) {
            int numMoved = size - index - 1;
            if (numMoved > 0)
            System.arraycopy(storage, index + 1, storage, index, numMoved);
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void saveByIndex(Resume r, int index) {
        int fillIndex = Math.abs(index) - 1;
        System.arraycopy(storage, fillIndex, storage, fillIndex + 1, size - fillIndex);
        storage[fillIndex] = r;
    }
}
