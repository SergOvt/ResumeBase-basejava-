package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) throw new StorageException("IO error", directory.getName());
        for (File file : files) {
            if (file.isFile()) {
                if (!file.delete()) throw new StorageException("IO error", file.getName());
            }
            else if (file.isDirectory()) deleteDirectory(file);
        }

    }

    @Override
    public int size() {
        return getSize(directory);
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            if (!file.createNewFile()) throw new StorageException("IO error", file.getName());
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) throw new StorageException("IO error", file.getName());
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFiles(directory);
    }

    private void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) throw new StorageException("IO error", dir.getName());
        if (files.length == 0) {
            if (!dir.delete()) throw new StorageException("IO error", dir.getName());
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) deleteDirectory(file);
            else if (!file.isFile() || !file.delete()) throw new StorageException("IO error", dir.getName());
        }
    }

    private int getSize(File dir) {
        int result = 0;
        File[] files = dir.listFiles();
        if (files == null) throw new StorageException("IO error", dir.getName());
        for (File file : files) {
            if (file.isFile()) result++;
            else if (file.isDirectory()) result += getSize(file);
        }
        return result;
    }

    private List<Resume> getFiles(File dir) {
        List<Resume> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null) throw new StorageException("IO error", dir.getName());
        for (File file : files) {
            if (file.isFile()) result.add(doGet(file));
            else if (file.isDirectory()) result.addAll(getFiles(file));
        }
        return result;
    }
}
