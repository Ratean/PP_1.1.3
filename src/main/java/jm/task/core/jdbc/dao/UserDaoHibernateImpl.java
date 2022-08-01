package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.OptimisticLockException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS usersschema.users (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "lastName VARCHAR(45) NOT NULL," +
                "age TINYINT NOT NULL)").executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS usersschema.users")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        } catch (SQLGrammarException e) {
            if (e.getCause().getMessage().endsWith("doesn't exist")) {
                createUsersTable();
                saveUser(name, lastName, age);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (OptimisticLockException ignore) {

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            users = session.createSQLQuery("SELECT * FROM usersschema.users")
                    .addEntity(User.class).getResultList();
            session.getTransaction().commit();
        } catch (SQLGrammarException ignore) {

        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE usersschema.users")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
