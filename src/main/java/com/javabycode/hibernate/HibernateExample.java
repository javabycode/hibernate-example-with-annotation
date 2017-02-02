package com.javabycode.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.javabycode.hibernate.model.Student;

/**
 * The program performs CRUD operation on database with Hibernate API's
 * 
 */
public class HibernateExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		HibernateExample application = new HibernateExample();

		/*
		 * Dump data by saving few objects with hibernate
		 */
		int id1 = application.saveStudent("Bill Gate", new Date(), "USA", "1111111");
		int id2 = application.saveStudent("Larry Page", new Date(), "France", "1111112");
		int id3 = application.saveStudent("Steve Job", new Date(), "England", "1111113");
		int id4 = application.saveStudent("David Pham", new Date(), "Canada", "1111114");

		/*
		 * Get all saved objects
		 */
		List<Student> students = application.getAllStudents();
		System.out.println("\n*******List of all persisted students*******");
		for (Student student : students) {
			System.out.println(student);
		}

		/*
		 * Update an object
		 */
		application.updateStudent(id3, "Tim Cook");

		/*
		 * Deletes an object
		 */
		application.deleteStudent(id2);

		/*
		 * Retrieve all saved objects
		 */
		List<Student> remaingStudents = application.getAllStudents();
		System.out.println("\n*******List of all remained persisted students*******");
		for (Student student : remaingStudents) {
			System.out.println(student);
		}

	}

	/**
	 * Save a Student object in database
	 */
	public int saveStudent(String name, Date enteringDate, String nationality, String code) {
		Student student = new Student();
		student.setName(name);
		student.setEnteringDate(enteringDate);
		student.setNationality(nationality);
		student.setCode(code);
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int id = (Integer) session.save(student);
		session.getTransaction().commit();
		return id;
	}

	/**
	 * List of all persisted Student objects from database
	 */
	public List<Student> getAllStudents() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Student> employees = (List<Student>) session.createQuery("FROM Student s ORDER BY s.id ASC").list();

		session.getTransaction().commit();
		return employees;
	}

	/**
	 * Update a specific Student object
	 */
	public void updateStudent(int id, String nationality) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Student student = (Student) session.get(Student.class, id);
		student.setNationality(nationality);
		session.update(student);
		session.getTransaction().commit();
	}

	/**
	 * Delete a specific Student object
	 */
	public void deleteStudent(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Student student = (Student) session.get(Student.class, id);
		session.delete(student);
		session.getTransaction().commit();
	}
}