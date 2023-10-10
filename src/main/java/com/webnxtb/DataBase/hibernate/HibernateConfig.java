package com.webnxtb.DataBase.hibernate;

import com.webnxtb.DataBase.Konfiguracja;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateConfig {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Tworzenie ustawień Hibernate
            Properties hibernateProperties = new Properties();
            hibernateProperties.put(Environment.DRIVER, "org.h2.Driver");
            hibernateProperties.put(Environment.URL, "jdbc:h2:file:D:/zasoby/pz/WebNxtb/WebNxtb/test.db;DB_CLOSE_DELAY=-1");
            hibernateProperties.put(Environment.USER, "");
            hibernateProperties.put(Environment.PASS, "");
            hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            hibernateProperties.put(Environment.SHOW_SQL, "false");
            hibernateProperties.put(Environment.HBM2DDL_AUTO, "update");
            hibernateProperties.put(Environment.FORMAT_SQL, "false");


            // Tworzenie rejestru usług Hibernate
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(hibernateProperties)
                    .build();

            // Tworzenie metadanych na podstawie źródeł metadanych
            MetadataSources metadataSources = new MetadataSources(standardRegistry);
            metadataSources.addAnnotatedClass(Konfiguracja.class);

            // Tworzenie fabryki sesji Hibernate
            return metadataSources.buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}