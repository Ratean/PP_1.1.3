package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS usersschema.users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL," +
                    "age TINYINT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS usersschema.users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            if (e.getMessage().endsWith("doesn't exist")) {
                createUsersTable();
                saveUser(name, lastName, age);
            } else {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM usersschema.users WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try(Statement statement = connection.createStatement()) {
            list = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersschema.users");
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE usersschema.users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
