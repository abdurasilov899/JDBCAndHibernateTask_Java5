package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private Connection connection;

    public UserDaoJdbcImpl() throws SQLException {
        connection = new Util().getConnection();
    }

    public void createUsersTable() {
        final String creat = """
                create table if not exists users (
                id serial primary key,
                name varchar not null,
                last_name varchar not null,
                age smallint not null
                );
                """;
        try (Statement stm = connection.createStatement()) {
            stm.executeUpdate(creat);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        final String drop = "drop table users";
        try (Statement stm = connection.createStatement()) {
            stm.executeUpdate(drop);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String save = "insert into users(name,last_name,age) values(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        final String remId = "DELETE From users where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(remId)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> alluser = new ArrayList<>();
        final String allusers = "Select * from users";
        try (Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(allusers)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte(4));
                alluser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alluser;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE table users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByFirstName(String firstName) {
        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.
        String query = "select case when name not null then true else false end from users where name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}