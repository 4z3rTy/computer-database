package com.excilys.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.service.CompanyS;
import com.excilys.service.ComputerS;

/**
 * Servlet implementation class secondServlet
 */

@WebServlet(name = "SecondServlet", urlPatterns = "/addComputer")
public class secondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public secondServlet() {
        super();
   
    }
    private CompanyS CS = new CompanyS();
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/static/views/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String name=request.getParameter("computerName");
		String intro=request.getParameter("introduced");
		String disco=request.getParameter("discontinued");
		String company_id=request.getParameter("companyId");
		
		int c=Integer.valueOf(company_id); //TODO NumberFormatException null ??????????????????
		
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate i=LocalDate.parse(intro,formatter);
		LocalDate d=LocalDate.parse(disco,formatter);
		Date in=Date.valueOf(i);
		Date di=Date.valueOf(d);
		try {
			ComputerS C= new ComputerS();
			C.insertComputer(name, c , in, di);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		processRequest(request, response);
	}

}
