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
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

/**
 * The Class EditCompServlet.
 */
//TODO As of now it is not possible to edit a computer back to back without going through the dashboard in between
@WebServlet(name = "EditCompServlet", urlPatterns = "/editComputer")
public class EditCompServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	/** The company service. */
	@Autowired
	private CompanyService CS;


	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(EditCompServlet.class);

	/**
	 * Instantiates a new edits the comp servlet.
	 */
	public EditCompServlet() {
		super();

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

		RequestDispatcher dispatcher = request.getRequestDispatcher("static/views/editComputer.jsp");
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
		String compId = request.getParameter("computerId");
		request.setAttribute("compId", compId);
		try {
			List<CompanyDTO> compList = CS.getAllCompanies();
			request.setAttribute("compList", compList);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> messages = new HashMap<String, String>();

		String computerId = request.getParameter("id");

		String name = request.getParameter("computerName");
		if ((ComputerValidator.emptyName(name))) {
			messages.put("computerName", "Computer name cannot be left empty... :( ");
		}
		String intro = request.getParameter("introduced");
		String disco = request.getParameter("discontinued");

		if ((ComputerValidator.wrongFormat(intro))) {
			messages.put("introduced", "Your input for introduced has the wrong format :(");
		} else {
			if ((ComputerValidator.wrongFormat(disco)))

			{

				messages.put("discontinued", "Your input for discontinued has the wrong format :(");

			} else if ((ComputerValidator.wrongDate(intro, disco))) {
				messages.put("discontinued", "Discontinued date cannot be more recent than introduced date");
			}
		}

		String companyId = request.getParameter("companyId");
		if (messages.isEmpty()) {
			messages.put("success", "Update completed successfully!!!!");
		}

		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setId(companyId).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setId(computerId).setName(name).setIntro(intro)
				.setDisco(disco).setAny(anyDto).build();

		try {
			if (!(ComputerValidator.emptyName(name)) && !ComputerValidator.wrongFormat(intro)
					&& !ComputerValidator.wrongFormat(disco) && !ComputerValidator.wrongDate(intro, disco)) {
				ComputerService C = new ComputerService();
				C.updateComputer(ComputerMapper.toComputer(compDto));
			} else {
				logger.error("Update could not go through.");
			}

		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("messages", messages);

		doGet(request, response);
	}

}
