package ru.javawebinar.basejava;


public class MainDeadLock {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> method(lock1, lock2)).start();
        method(lock2, lock1);
    }

    static void method(Object lock1, Object lock2) {

        synchronized (lock1) {
            System.out.println("In first synchro befor sleep");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("In first synchro after sleep");

            synchronized (lock2) {
                System.out.println("In second synchro");
            }
        }
    }

}
