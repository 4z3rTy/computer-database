package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

/**
 * The Class DashboardServlet.
 */
@Controller
@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	@Autowired
	private ComputerService service;
	private static final long serialVersionUID = 1L;
	private int sum;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


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
		
			Page p = new Page(sum);
			//p.calcPages();
		if (request.getParameter("pageNum") != null) {
			p.setPage(Integer.parseInt(request.getParameter("pageNum")));
		}

		if (request.getParameter("pageAmount") != null && request.getParameter("pageAmount") != "") {
			p.setAmount(Integer.parseInt(request.getParameter("pageAmount")));
		}
		p.calcPages();

		try {

			String search = request.getParameter("search");
			String searchType = request.getParameter("searchType");
			if (searchType == null) {

				if (search == null) {
					sum = service.count("computer");
					compList = service.viewSomeComputers(p);
				} else {
					sum = service.searchCount(search);
					p.setMax(sum);
					p.calcPages();
				}

				try {
					if (request.getParameter("searchName") != null) {
						compList = service.getSearchName(search, p);
						searchType = "searchName";
					} else if (request.getParameter("searchIntro") != null) {
						compList = service.getSearchIntro(search, p);
						searchType = "searchIntro";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {

				p.setMax(sum);
				p.calcPages();

				switch (searchType) {

				case "":
					compList = service.viewSomeComputers(p);
					break;

				case "searchName":
					compList = service.getSearchName(search, p);
					break;

				case "searchIntro":
					compList = service.getSearchIntro(search, p);
					break;
				}
			}

			request.setAttribute("sum", sum);
			request.setAttribute("compList", compList);
			request.setAttribute("pageTotal", p.getTotal());
			request.setAttribute("currentPage", p.getPage());
			request.setAttribute("items", p.getAmount());
			request.setAttribute("searchRes", search);
			request.setAttribute("searchType", searchType);
			request.setAttribute("pageAmount", p.getAmount());
		} catch (SQLException | ClassNotFoundException e) {
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