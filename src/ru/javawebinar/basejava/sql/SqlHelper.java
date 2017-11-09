package ru.javawebinar.basejava.sql;


import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlHelper<T> {

    T accept(PreparedStatement ps) throws SQLException;

}
