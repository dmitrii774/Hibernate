package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS  users ( " +
                    "id BIGSERIAL PRIMARY KEY NOT NULL, " +
                    "name VARCHAR(233), " +
                    "lastname VARCHAR(233), " +
                    "age INT);";
            session.createSQLQuery(SQL)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table users create");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()){
            String SQLdr = "DROP TABLE IF EXISTS users";
            session.beginTransaction();
            session.createSQLQuery(SQLdr)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table users drop");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Users " + name + " added (HIB)");
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User " + id + " delete");
        }
    }

    @Override
    public List<User> getAllUsers() {
//        String SQLdr = "SELECT * FROM users";
        try(Session session = sessionFactory.openSession()){
            // SQL Query
//            List<User> allUsersSQL = session.createSQLQuery(SQLdr)
//                    .addEntity(User.class)
//                    .list();
//            return   allUsersSQL;
            // HQL Query
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        String SQLcle = "TRUNCATE TABLE users";
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createSQLQuery(SQLcle)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
