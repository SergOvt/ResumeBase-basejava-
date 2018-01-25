package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;

public class ContactsUtil {

    public static String toHtml(ContactType type, String value) {
        if (value == null) return "";
        switch (type) {
            case SKYPE:
                return type.getTitle() + ": " + toLink("skype:" + value, value);
            case MAIL:
                return type.getTitle() + ": " + toLink("mailto:" + value, value);
            case LINKEDIN:
            case GITHUB:
            case STATCKOVERFLOW:
            case HOME_PAGE:
                return toLink(value, value);
            default:
                return type.getTitle() + ": " + value;
        }
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
