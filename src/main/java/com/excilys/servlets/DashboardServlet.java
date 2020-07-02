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

//
/**
 * The Class DashboardServlet.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cs. */
	private ComputerS service = new ComputerS();

	/** The nb. */
	private int nb = service.count("computer");
	// List <Computer> compList=CS.getAllComputer();

	/**
	 * Process request.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<ComputerDTO> compList = null;
		Page p = new Page("computer");

		if (request.getParameter("pageNum") != null) {
			p.setPage(Integer.parseInt(request.getParameter("pageNum")));
		}

		if (request.getParameter("pageAmount") != null) {
			p.setAmount(Integer.parseInt(request.getParameter("pageAmount")));
		}
		p.calcPages();

		try {
			String search = request.getParameter("search");
			if (search != null && search.isEmpty() == false) {
				nb = service.searchCount(search);
				p.setMax(nb);
				p.calcPages();
				try {

					compList = service.getSearch(search, p);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				nb = service.count("computer");
				compList = service.viewSomeComputers(p);
			}
			request.setAttribute("nb", nb);
			request.setAttribute("compList", compList);
			request.setAttribute("pageTotal", p.getTotal());
			request.setAttribute("currentPage", p.getPage());
			request.setAttribute("items", p.getAmount());
			request.setAttribute("searchRes", search);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

		processRequest(request, response);

	}

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String selection = request.getParameter("selection");
		String[] strings = selection.split(",");

		for (String i : strings) {
			try {
				service.deleteComputer(Integer.parseInt(i));
			} catch (NumberFormatException | ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}

		doGet(request, response);
		// processRequest(request, response);
	}
}