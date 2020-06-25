package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.service.ComputerS;
import com.excilys.ui.Page;

@WebServlet(name = "FirstServlet", urlPatterns = "/dashboard")
public class FirstServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ComputerS CS = new ComputerS();
	private int nb = CS.count("computer");
	// List <Computer> compList=CS.getAllComputer();

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page p = new Page("computer");

		if (request.getParameter("pageNum") != null) {
			p.setPage(Integer.parseInt(request.getParameter("pageNum")));
		}

		if (request.getParameter("pageAmount") != null) {
			p.setAmount(Integer.parseInt(request.getParameter("pageAmount")));
		}
		p.calcPages();

		try {
			List<Computer> compList = CS.viewSomeComputers(p);
			request.setAttribute("nb", nb);
			request.setAttribute("compList", compList);
			request.setAttribute("pageTotal", p.getTotal());
			request.setAttribute("currentPage", p.getPage());
			request.setAttribute("items", p.getAmount());
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}
}