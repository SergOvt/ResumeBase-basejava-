package ru.javawebinar.basejava.sql;


import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlHelper<T> {


    T accept(PreparedStatement ps) throws SQLException;

    static <T> T doExecute (ConnectionFactory connectionFactory, String sql, SqlHelper<T> consumer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return consumer.accept(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
