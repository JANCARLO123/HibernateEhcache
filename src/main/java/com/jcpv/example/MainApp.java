package com.jcpv.example;

import com.jcpv.example.entity.Department;
import com.jcpv.example.entity.Employee;
import com.jcpv.example.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by JanCarlo on 03/07/2017.
 */
public class MainApp {
    private static final Logger logger = LogManager.getLogger(MainApp.class);
    public static void main(String[] args) {

        Session session = null;
        Transaction transaction= null;
        try{
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();

            Person person1 = session.get(Person.class, 1L);
            if (person1 != null) {
                logger.info(person1.getName());
            }

            // Obtain an entity using load() method
            Person person2 = session.load(Person.class, 2L);
            logger.info(person2.getName());

            // Obtain an entity using byId() method
            Person person3 = session.byId(Person.class).getReference(3L);
            logger.info(person3.getName());

            //Department
            Department department = new Department();
            department.setName("IT department2");
            session.saveOrUpdate(department);


            Employee employee = new Employee();
            employee.setName("Employee 1");
            employee.setDepartment(department);
            session.saveOrUpdate(employee);

            Employee employee2 = new Employee();
            employee2.setName("Employee 2");
            employee2.setDepartment(department);
            session.saveOrUpdate(employee2);

            Employee employee3 = new Employee();
            employee3.setName("Employee 3");
            employee3.setDepartment(department);
            session.saveOrUpdate(employee3);






            transaction.commit();
            session.close();

            //Ehcache
            logger.info("------Ehcache---------");
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();

            Person person4 = session.get(Person.class, 1L);
            if (person4 != null) {
                logger.info(person4.getName());
            }

            // Obtain an entity using load() method
            Person person5 = session.load(Person.class, 2L);
            logger.info(person5.getName());

            // Obtain an entity using byId() method
            Person person6 = session.byId(Person.class).getReference(3L);
            logger.info(person6.getName());



            transaction.commit();
            session.close();


            //Get Department from DATABASE
            logger.info("------Get Department from DATABASE---------");
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();

            Department department2=session.get(Department.class, 1l);
            logger.info("Department :"+department2.getName());
            List<Employee> employees=department2.getEmployees();
            for (Employee emp : employees) {
                logger.info("\tEmployee Name : "+emp.getName());
            }
            transaction.commit();
            session.close();


            //Get Department from EHCACHE
            logger.info("------Get Department from EHCACHE---------");
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();

            Department department3=session.get(Department.class, 1l);
            logger.info("Department :"+department3.getName());
            List<Employee> employees2=department3.getEmployees();
            for (Employee emp : employees2) {
                logger.info("\tEmployee Name : "+emp.getName());
            }
            transaction.commit();
            session.close();

            //Get Department from EHCACHE
            logger.info("------Get Employee from EHCACHE CREATE QUERY---------");
            session= HibernateUtil.getSessionFactory().openSession();
            transaction= session.getTransaction();

            transaction.begin();
            @SuppressWarnings("unchecked")
            List<Employee> employees3=session.createQuery("from Employee")
                    .setCacheable(true)
                    .setCacheRegion("employee.cache")
                    .list();

            for (Employee emp : employees3) {
                logger.info("\tEmployee Name : "+emp.getName());
            }
            transaction.commit();
            session.close();


        }catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();

            }
            logger.error("Failed to ..." + ex);
        }
        HibernateUtil.shutdown();
    }
}
