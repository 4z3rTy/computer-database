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

import com.excilys.dto.ComputerDTO;
import com.excilys.service.ComputerS;
import com.excilys.ui.Page;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardServlet.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cs. */
	private ComputerS CS = new ComputerS();
	
	/** The nb. */
	private int nb = CS.count("computer");
	// List <Computer> compList=CS.getAllComputer();

	/**
	 * Process request.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
			List<ComputerDTO> compList = CS.viewSomeComputers(p);
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

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}
}