package ru.javawebinar.basejava.model;

public enum Contacts {
    TEL ("Телефон"),
    SKYPE ("Skype"),
    EMAIL ("Почта"),
    LINKEDIN ("Профиль LinkedIn"),
    GITHUB ("Профиль GitHub"),
    STACKOVERFLOW ("Профиль StackOverFlow"),
    HOMEPAGE ("Домашняя страница");

    private String title;

    private Contacts(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
