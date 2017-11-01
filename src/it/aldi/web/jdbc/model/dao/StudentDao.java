package it.aldi.web.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.aldi.web.jdbc.model.Student;

public class StudentDao {

	private DataSource dataSource;

	public StudentDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() {
		List<Student> students = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		String sql = "select * from student";

		String firstName;
		String lastName;
		String email;

		Student tempStudent;
		try {
			connection = dataSource.getConnection();

			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");

				firstName = resultSet.getString("first_name");
				lastName = resultSet.getString("last_name");
				email = resultSet.getString("email");

				tempStudent = new Student(id, firstName, lastName, email);

				students.add(tempStudent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return students;
	}

	public Student getStudent(String studentId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int id = Integer.parseInt(studentId);

		Student student = null;

		String sql = "select * from student " + "where id = ?";
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setInt(1, id);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				student = new Student(id, firstName, lastName, email);
			} else {
				throw new SQLException("Could not find student with ID: " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}

		return student;
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student newStudent) {
		Connection connection = null;
		PreparedStatement statement = null;

		String sql = "insert into student " + "(first_name, last_name, email) " + "values (?, ?, ?)";

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, newStudent.getFirstName());
			statement.setString(2, newStudent.getLastName());
			statement.setString(3, newStudent.getEmail());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, null);
		}
	}

	public void updateStudent(Student student) {
		Connection connection = null;
		PreparedStatement statement = null;

		String sql = "update student " + "set first_name = ?, last_name = ?, email = ? " + "where id = ?";

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, null);
		}
	}
}
