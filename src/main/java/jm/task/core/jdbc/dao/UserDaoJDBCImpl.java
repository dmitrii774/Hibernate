package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
String SQL = "CREATE TABLE IF NOT EXISTS usersjdbc ( " +
        "id BIGSERIAL PRIMARY KEY NOT NULL, " +
        "name VARCHAR(233), " +
        "lastname VARCHAR(233), " +
        "age INT);";
        try (Connection connection = Util.open();
        Statement statement = connection.createStatement()){
            statement.execute(SQL);
            System.out.println("Table created (JDBC)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS usersjdbc";
        try (Connection connection = Util.open();
            Statement statement = connection.createStatement()){
            statement.execute(SQL);
            System.out.println("Table deleted (JDBC)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQLsu = "INSERT INTO usersjdbc (name, lastName, age) VALUES(?,?,?)";
        try(Connection connection = Util.open();
        PreparedStatement prep = connection.prepareStatement(SQLsu)) {
            prep.setString(1,name);
            prep.setString(2,lastName);
            prep.setByte(3,age);
            prep.executeUpdate();
            System.out.println("Users " + name + " added (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String SQLrem = "DELETE FROM usersjdbc WHERE id = ? ";
        try(Connection connection = Util.open();
        PreparedStatement preparedStatementRem = connection.prepareStatement(SQLrem)){
            preparedStatementRem.setLong(1, id);
            preparedStatementRem.executeUpdate();
            System.out.println("user id =" + id + " delete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM usersjdbc";
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();
             ResultSet resSe = statement.executeQuery(SQL)) {
            while (resSe.next()) {
                User user = new User();
                user.setId(resSe.getLong("id"));
                user.setName(resSe.getString("name"));
                user.setLastName(resSe.getString("lastname"));
                user.setAge(resSe.getByte("age"));
                users.add(user);
            }
            System.out.println("Users loaded (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String SQLclen = "TRUNCATE TABLE users";
        try( Connection connection = Util.open();
        Statement statement = connection.createStatement()){
            statement.execute(SQLclen);

            System.out.println("Table is clean");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
