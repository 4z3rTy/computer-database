package com.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyS;
import com.excilys.service.ComputerS;
import com.excilys.validator.ComputerValidator;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class secondServlet.
 */

@WebServlet(name = "AddCompServlet", urlPatterns = "/addComputer")
public class AddCompServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
     * Instantiates a new adds the comp servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public AddCompServlet() {
        super();
   
    }
    
    /** The cs. */
    private CompanyS CS = new CompanyS();
	
	public static Logger logger = LoggerFactory.getLogger(AddCompServlet.class);
    
    
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

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<Company> compList = CS.getAllCompanies();
			request.setAttribute("compList", compList);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		processRequest(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> messages = new HashMap<String, String>();
		
		String name=request.getParameter("computerName");
		if ((ComputerValidator.emptyName(name)))
		{
			messages.put("computerName", "Computer name cannot be left empty");
		}
			
		String intro=request.getParameter("introduced");
		String disco=request.getParameter("discontinued");
		String company_id=request.getParameter("companyId");
		
		if(messages.isEmpty())
		{
			messages.put("success","Insertion completed successfully");
		}
		ComputerDTO dto=new ComputerDTO(name,intro,disco,company_id);
		
		try {
			if(!(ComputerValidator.emptyName(name)) && !ComputerValidator.wrongFormat(intro, disco) && !ComputerValidator.wrongDate(intro, disco))
			{
				ComputerS C= new ComputerS();
				C.insertComputer(Mapper.toComputer(dto));
			}
			else
			{
				logger.error("Computer name cannot be left empty, Insertion did not go through.");
				//throw new ServletException("Wrong Input(s)");
				
			}
		
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		processRequest(request, response);
	}

}
