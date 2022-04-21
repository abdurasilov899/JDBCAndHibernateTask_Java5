package peaksoft;

import peaksoft.dao.UserDao;
import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
//        Util util = new Util();
//        System.out.println(util.getConnection());

        UserDao user = new UserDaoJdbcImpl();
        //user.createUsersTable();
        //user.saveUser("Turatbek","Ibraev", (byte) 30);
        // user.removeUserById(1);
      //  user.getAllUsers();
        user.dropUsersTable();
    }
}

