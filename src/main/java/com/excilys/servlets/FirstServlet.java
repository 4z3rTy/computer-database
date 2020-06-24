package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.service.ComputerS;
import com.excilys.ui.Page;

@WebServlet(name = "FirstServlet", urlPatterns = "")
public class FirstServlet extends HttpServlet {

	 //FirstServlet () throws IOException, ClassNotFoundException, SQLException {}
	private static final long serialVersionUID = 1L;
	private ComputerS CS = new ComputerS();
	private Page p = new Page();
	private int nb =150;
	private ServletConfig config;

	// Setting JSP page

	String page = "DataPage.jsp";

	public void init(ServletConfig config)

			throws ServletException {

		this.config = config;

	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/dashboard.html");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("nb", 150);
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}
}