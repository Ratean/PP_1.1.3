package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl usa = new UserServiceImpl();
        usa.createUsersTable();
        usa.saveUser("Vanya", "Ivanov", (byte) 20);
        usa.saveUser("Vanya", "Ivanov", (byte) 20);
        usa.saveUser("Vanya", "Ivanov", (byte) 20);
        usa.saveUser("Vanya", "Ivanov", (byte) 20);
        List<User> list = usa.getAllUsers();
        System.out.println(list.size());
        for (User u: list) {
            System.out.println(u);
        }
        usa.cleanUsersTable();
        usa.dropUsersTable();

    }
}
