package ru.javawebinar.basejava.sql;


import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Executor {

    private static ConnectionFactory connectionFactory;

    public Executor(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }


    public static <T> T doExecute(String sql, SqlHelper<T> consumer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return consumer.accept(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
