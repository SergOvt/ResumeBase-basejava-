package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExperienceType {
    private String title;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String content;

    public ExperienceType(String title, LocalDate beginDate, LocalDate endDate, String content) {
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return title + '\n' + beginDate.format(DateTimeFormatter.ofPattern("yyyy/MM")) + '-' +
                endDate.format(DateTimeFormatter.ofPattern("yyyy/MM")) + '\t' + content;
    }
}
