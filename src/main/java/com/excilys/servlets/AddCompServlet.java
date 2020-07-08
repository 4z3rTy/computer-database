package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyS;
import com.excilys.service.ComputerS;
import com.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class secondServlet.
 */

@WebServlet(name = "AddCompServlet", urlPatterns = "/addComputer")
public class AddCompServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new add computer servlet.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCompServlet() {
		super();

	}

	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/** The company service. */
	@Autowired
	private CompanyS CS;


	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(AddCompServlet.class);

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

		RequestDispatcher dispatcher = request.getRequestDispatcher("static/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<CompanyDTO> compList = CS.getAllCompanies();
			// System.out.println(compList.size());
			request.setAttribute("compList", compList);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		processRequest(request, response);
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();

		String name = request.getParameter("computerName");
		if ((ComputerValidator.emptyName(name))) {
			messages.put("computerName", "Computer name cannot be left empty... :( ");
		}

		String intro = request.getParameter("introduced");
		String disco = request.getParameter("discontinued");

		if (!(ComputerValidator.emptyDate(intro))) {

			if ((ComputerValidator.wrongFormat(intro))) {
				messages.put("introduced", "Your input for introduced has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(disco))) {

			if ((ComputerValidator.wrongFormat(disco)))

			{
				messages.put("discontinued", "Your input for discontinued has the wrong format :(");
			}
		}

		if (!(ComputerValidator.emptyDate(intro)) && !(ComputerValidator.emptyDate(disco))) {
			if (ComputerValidator.wrongDate(intro, disco)) {
				messages.put("discontinued", "Discontinued date cannot be more recent than introduced date");
			}
		}

		String companyId = request.getParameter("companyId");

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setName(name).setIntro(intro).setDisco(disco)
				.setAny(anyDto).build();

		if (messages.isEmpty()) {
			messages.put("success", "Insertion completed successfully!!!!");

			try {
				ComputerS C = new ComputerS();
				C.insertComputer(ComputerMapper.toComputer(compDto));

			} catch (ClassNotFoundException | SQLException | IOException e) {
				logger.error("Insertion did not go through.", e);
				e.printStackTrace();
			}
		}

		request.setAttribute("messages", messages);
		doGet(request, response);
	}

}
