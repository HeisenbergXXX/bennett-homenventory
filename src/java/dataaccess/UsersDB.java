package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Users;

public class UsersDB {

        public List<Users> getAll() {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            try {
                List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
                return users;
            } finally {
                em.close();
            }
        }

        public Users get(String username) {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            try {
                Users user = em.find(Users.class, username);
                return user;
            } finally {
                em.close();
            }
        }

        public void insert(Users user) {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(user);
                em.merge(user);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
            } finally {
                em.close();
            }
        }

    public void update(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}
