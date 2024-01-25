package hibernate;

import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateExercise {
    static SessionFactory factory;

    // Main
    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();

        // Add
        insertStudent("Antonio","Martinez", 20, "antonio@email.com","971112312");
        insertStudent("Diego","Martinez",42,"diego@email.com","971888828");
        insertStudent("Lucas","Rodriguez",57,"lucas@email.com","971822827");
        insertStudent("Manolo","Iglesias",33,"manolo@email.com","971888821");
        System.out.println();

        // Update
        updateStudent(1,"Pepe","Martinez", 67,"antonio@email.com","971112312");
        System.out.println();

        // Delete
        deleteStudent(2);
        System.out.println();

        // Get one item
        System.out.println("Student 1 Name: " + getStudent(1).getName());
        System.out.println();

        // Print all students
        System.out.println("====== Students List ======");
        for (Student student:listStudents()) {
            System.out.println(student.getId()+": "+student.getName());
        }
        System.out.println();


        // Close and finish
        factory.close();
    }

    // Insert a student
    private static void insertStudent(String nombre, String lastname, int age, String email,String phone) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Student student = new Student(nombre, lastname, age, email, phone);

        session.save(student);
        tx.commit();
        session.close();

        System.out.println("Student "+ nombre + " " + lastname + " ("+ student.getId() + ") successfully inserted into database.");
    }

    // Update a student
    private static void updateStudent(int id,String nombre, String lastname, int age, String email,String phone) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);

        student.setName(nombre);
        student.setLastname(lastname);
        student.setAge(age);

        student.setEmail(email);
        student.setPhone(phone);

        session.update(student);
        tx.commit();
        session.close();

        System.out.println("Student '"+ id + "' successfully updated.");
    }

    // Delete a student
    private static void deleteStudent(int id) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);

        session.delete(student);
        tx.commit();
        session.close();

        System.out.println("Student '"+ id + "' successfully deleted.");
    }

    // Get one student by id
    private static Student getStudent(int id) {
        Session session = factory.openSession();
        Student student = session.get(Student.class, id);
        session.close();

        return student;
    }

    // Get all students
    private static List<Student> listStudents() {

        Session session = factory.openSession();
        List<Student> studentList = session.createQuery("FROM Student", Student.class).list();
        session.close();

        return studentList;
    }

}