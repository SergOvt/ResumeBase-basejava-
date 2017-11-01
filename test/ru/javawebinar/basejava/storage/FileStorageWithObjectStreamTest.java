package ru.javawebinar.basejava.storage;

import java.io.File;

public class FileStorageWithObjectStreamTest extends AbstractStorageTest {

    public FileStorageWithObjectStreamTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamStorage()));
    }
}
