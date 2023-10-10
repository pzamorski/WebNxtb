package com.webnxtb.DataBase.hibernate;

import com.webnxtb.DataBase.Getidlabel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public abstract class EncjaDAO<T>  implements Getidlabel{

    private final Class<T> entityType;
    Session session;


    public EncjaDAO(Class<T> entityType) {
        this.entityType = entityType;
    }


    // Metoda do dodawania encji do bazy danych
    public void insert() {
            session = HibernateConfig.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(this); // Zapisanie encji do bazy danych
            transaction.commit(); // Potwierdzenie transakcji
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Anulowanie transakcji w przypadku błędu
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    // Metoda do odczytywania wszystkich encji
    public List<T> pobierzListe() {
        Session session = HibernateConfig.getSessionFactory().openSession();

        try {
            // Tworzenie zapytania HQL do pobrania wszystkich encji
            String hql = "FROM " + entityType.getName();
            Query<T> query = session.createQuery(hql, entityType);
            return query.getResultList(); // Zwrócenie listy encji
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    // Metoda do pobierania jednej encji na podstawie identyfikatora
    public T select(int id) {
        Session session = HibernateConfig.getSessionFactory().openSession();

        try {
            return session.get(entityType, id); // Pobieranie encji na podstawie identyfikatora
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void usunEncjeById(int id) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Pobierz encję na podstawie identyfikatora
            T encja = session.get(entityType, id);

            if (encja != null) {
                session.delete(encja); // Usuń encję z bazy danych
            }

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

    public T update() {

        Session session = HibernateConfig.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Pobierz encję na podstawie identyfikatora
            T entity = session.get(entityType, getID());

            if (entity != null) {
                // Zaktualizuj encję lub wykonaj inne operacje
                // np. entity.setNazwa("Nowa nazwa");
                // ...

                session.update(entity); // Aktualizacja encji w bazie danych
                transaction.commit(); // Zatwierdź transakcję
            }

            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Wycofaj transakcję w przypadku błędu
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    public T update(int id) {

        Session session = HibernateConfig.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Pobierz encję na podstawie identyfikatora

            T entity = session.get(entityType, id);

            if (entity != null) {


                session.update(entity); // Aktualizacja encji w bazie danych
                transaction.commit(); // Zatwierdź transakcję
            }

            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Wycofaj transakcję w przypadku błędu
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
