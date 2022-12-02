package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Categories;

public class CategoriesDB {

    public List<Categories> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Categories get(int categoryID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Categories.class, categoryID);
        } finally {
            em.close();
        }
    }

    public void update(Categories category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Categories category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.remove(em.merge(category));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void insert(Categories category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.merge(category);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}