package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserDao userDao = new UserDaoJDBCImpl();
    private static UserDao userDaoHi = new UserDaoHibernateImpl();
    public void createUsersTable() {
        userDao.createUsersTable();
        userDaoHi.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
        userDaoHi.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name,lastName,age);
        userDaoHi.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        userDaoHi.removeUserById(id);
    }

    public List<User> getAllUsers() {
//        List<User> users = userDao.getAllUsers();
//        users.forEach(System.out::println);

        List<User> users1 = userDaoHi.getAllUsers();
        users1.forEach(System.out::println);
        return users1;

    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        userDaoHi.cleanUsersTable();
    }
}
