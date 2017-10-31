package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectoryDeeply(dir,0);
    }

    // TODO: make pretty output
        private static void printDirectoryDeeply(File dir, int pos) {
            File[] listFiles = dir.listFiles();
            if (listFiles == null) return;
            for (File file : listFiles) {
                for (int i = 0; i < pos; i++) {
                    System.out.print("\t");
                }
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
                else {
                    System.out.println("-" + file.getName());
                    int position = pos + 1;
                    printDirectoryDeeply(file, position);
                }
            }
    }
}
