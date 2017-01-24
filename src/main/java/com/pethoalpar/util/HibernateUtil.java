package com.pethoalpar.util;

import com.pethoalpar.entity.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by petho on 2017-01-24.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try{
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Person.class);

            //driver
            configuration.setProperty("connection.driver_class","com.mysql.jdbc.Driver");

            //url
            configuration.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/test");

            //user name
            configuration.setProperty("hibernate.connection.username","root");

            //password
            configuration.setProperty("hibernate.connection.password","qwertyui");

            //dialect
            configuration.setProperty("dialect","org.hibernate.dialect.MySQLDialect");

            //schema auto update
            configuration.setProperty("hibernate.hbm2ddl.auto","update");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        }catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
