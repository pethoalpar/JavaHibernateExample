package com.pethoalpar;

import com.pethoalpar.entity.Person;
import com.pethoalpar.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by petho on 2017-01-24.
 */
public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String [] args){
        sessionFactory = HibernateUtil.getSessionFactory();

        insert(new Person("Petho","Alpar","petho@alpar.com",null));
        insert(new Person("John","Doe","john@doe.com",21));
        insert(new Person("Jane","Doe","jane@doe.com",18));

        printAllPerson();

        Person p = findByEmail("john@doe.com");
        deletePerson(p.getId());

        printAllPerson();

        p = findByEmail("jane@doe.com");
        p.setAge(22);
        updatePerson(p);

        printAllPerson();

    }

    private static void insert(Person person){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(person);
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    private static void printAllPerson(){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List<Person> persons = session.createQuery("select e from Person e",Person.class).list();
            persons.forEach(System.out::println);
            System.out.println();
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    private static Person findByEmail(String email){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Person person = null;
        try{
            tx = session.beginTransaction();
            person = session.createQuery("select e from Person e where e.email =:email",Person.class).setParameter("email",email).getSingleResult();
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return person;
    }

    private static void deletePerson(int id){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Person person = session.get(Person.class,id);
            session.delete(person);
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    private static void updatePerson(Person person){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Person p = session.get(Person.class,person.getId());
            p.setAge(person.getAge());
            session.update(p);
            tx.commit();
        }catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }
}
