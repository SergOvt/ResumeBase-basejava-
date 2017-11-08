package ru.javawebinar.basejava.storage;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.doExecute(connectionFactory, "DELETE FROM resume", ps -> {
            ps.execute();
            LOG.info("Clear storage");
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.doExecute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new NotExistStorageException(uuid);
            LOG.info("Get " + uuid);
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        SqlHelper.doExecute(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() != 1) throw new NotExistStorageException(r.getUuid());
            LOG.info("Update " + r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        SqlHelper.doExecute(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            try {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                LOG.info("Save " + r);
                return null;
            } catch (PSQLException e) {
                throw new ExistStorageException(r.getUuid());
            }
        });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.doExecute(connectionFactory, "DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() != 1) throw new NotExistStorageException(uuid);
            LOG.info("Delete " + uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.doExecute(connectionFactory, "SELECT * FROM resume r ORDER BY r.full_name, r.uuid", ps -> {
            List<Resume> resultList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            LOG.info("Get all");
            return resultList;
        });
    }

    @Override
    public int size() {
        return SqlHelper.doExecute(connectionFactory, "SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
