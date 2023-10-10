package com.webnxtb.DataBase.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateBaseDao<T> {
    private static SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
    private final Class<T> entityType;

    public HibernateBaseDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public static <T> void dodaj(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> T pobierz(Class<T> entityType, int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(entityType, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void aktualizuj(int id, T updatedEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            T entity = session.get(entityType, id);
            if (entity != null) {
                session.merge(updatedEntity); // Używamy merge do aktualizacji encji
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void usun(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            T entity = session.get(entityType, id);
            if (entity != null) {
                session.delete(entity);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static <T> List<T> pobierzWszystkie(Class<T> entityType) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM " + entityType.getName();
            Query<T> query = session.createQuery(hql, entityType);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
