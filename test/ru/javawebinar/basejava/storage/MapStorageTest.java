package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;


public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void saveOverflow() throws Exception {

    }

    @Test
    @Ignore
    public void getAll() throws Exception {
        // fail order
    }

}