package ru.javawebinar.basejava.util;


import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class SqlHelper {

    static <T> void doExecute (ConnectionFactory connectionFactory, String sql, T param, Consumer<T> consumer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            consumer.accept(param);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
