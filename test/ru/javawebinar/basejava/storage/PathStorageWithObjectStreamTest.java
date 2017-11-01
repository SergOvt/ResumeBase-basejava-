package ru.javawebinar.basejava.storage;


public class PathStorageWithObjectStreamTest extends AbstractStorageTest {

    public PathStorageWithObjectStreamTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
