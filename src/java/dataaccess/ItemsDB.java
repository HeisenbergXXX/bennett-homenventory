package dataaccess;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Items;
import models.Users;

public class ItemsDB {

        public List<Items> getAll() throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            try {
                return em.createNamedQuery("Items.findAll", Items.class).getResultList();
            } finally {
                em.close();
            }
        }

        public Items get(int itemID) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            try {
                return em.find(Items.class,itemID);
            } finally {
                em.close();
            }
        }

        public void insert(Items item) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try{
                Users user = item.getOwner();
                user.getItemsCollection().add(item);
                trans.begin();
                em.persist(item);
                em.merge(user);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
            } finally {
                em.close();
            }
        }

        public void update(Items item) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.merge(item);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
            } finally {
                em.close();
            }
        }

        public void delete(Items item) throws Exception {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                Users user = item.getOwner();
                user.getItemsCollection().remove(item);
                trans.begin();
                em.remove(em.merge(item));
                em.merge(user);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
            } finally {
                em.close();
            }
        }

}
