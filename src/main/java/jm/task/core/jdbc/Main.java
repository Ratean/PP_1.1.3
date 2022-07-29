package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
        dao.createUsersTable();
        dao.saveUser("Vanya", "Ivanov", (byte) 20);
        dao.saveUser("Vanya", "Ivanov", (byte) 20);
        dao.saveUser("Vanya", "Ivanov", (byte) 20);
        dao.saveUser("Vanya", "Ivanov", (byte) 20);
        List<User> list = dao.getAllUsers();
        System.out.println(list.size());
        for (User u: list) {
            System.out.println(u);
        }
        dao.cleanUsersTable();
        dao.dropUsersTable();

    }
}
