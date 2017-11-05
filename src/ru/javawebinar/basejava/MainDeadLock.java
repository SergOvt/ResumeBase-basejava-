package ru.javawebinar.basejava;


public class MainDeadLock {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        MainDeadLock dl = new MainDeadLock();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                dl.method1();
                dl.method2();
            }).start();
        }

    }

    void method1(){
        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println("Method1");
            }
        }
    }

    void method2(){
        synchronized (lock2) {
            synchronized (lock1) {
                System.out.println("Method2");
            }
        }
    }


}
