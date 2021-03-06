package it.aldi.web.jdbc.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.aldi.web.jdbc.model.Student;
import it.aldi.web.jdbc.model.dao.StudentDao;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDao studentDao;

	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		studentDao = new StudentDao(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("command");

		if (command == null) {
			command = "LIST";
		}

		switch (command) {
		case "LIST":
			listStudents(request, response);
			break;
		case "ADD":
			addStudent(request, response);
			break;
		case "LOAD":
			loadStudent(request, response);
			break;
		case "UPDATE":
			updateStudent(request, response);
			break;
		case "DELETE":
			deleteStudent(request, response);
			break;
		default:
			listStudents(request, response);
		}

	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("studentId");

		studentDao.deleteStudent(studentId);

		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student student = new Student(id, firstName, lastName, email);

		studentDao.updateStudent(student);

		listStudents(request, response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("studentId");

		Student student = studentDao.getStudent(studentId);

		request.setAttribute("student", student);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student newStudent = new Student(firstName, lastName, email);

		studentDao.addStudent(newStudent);

		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students = studentDao.getStudents();

		request.setAttribute("student_list", students);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
