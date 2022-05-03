package peaksoft.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao,AutoCloseable {


    private final EntityManagerFactory entityManagerFactory = Util.createEntityManagerFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        System.out.println("GOOD JOB...!!!");
        entityManager.getTransaction().commit();

        entityManager.close();

    }

    @Override
    public void dropUsersTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("DROP TABLE USERS");
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeUserById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> listUser = entityManager.createNativeQuery("Select a from User a  ", User.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return listUser;
    }


    @Override
    public void cleanUsersTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE table users").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public boolean existsByFirstName(String firstName) {
        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("select case when count(u) > 0 " +
                    "then true " +
                    "else false end " +
                    "from User u where u.name = ?1", Boolean.class);
            query.setParameter(1, firstName);
            Boolean singleResult = (Boolean) query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return singleResult;
    }

    @Override
    public void close() throws Exception {

    }
}
