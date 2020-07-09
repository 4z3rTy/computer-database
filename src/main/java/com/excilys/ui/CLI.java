package com.excilys.ui;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;


/**
 * The Class CLI.
 */
@Configuration
@ComponentScan({"com.excilys.persistence","com.excilys.service"})
public class CLI {

	/**
	 * Instantiates a new cli.
	 *
	 * @param computerService the computer service
	 * @param companyService the company service
	 */
	public CLI(ComputerService computerService, CompanyService companyService) {
		this.companyService = companyService;
		this.computerService = computerService;
	}

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CLI.class);

	/** The company service. */
	private CompanyService companyService;
	
	/** The computer service. */
	private ComputerService computerService;

	/**
	 * Prints the computers.
	 */
	public void printComputers() {
		System.out.println("'List all computers' selected ->");
		System.out.println("");
		ArrayList<ComputerDTO> c = (ArrayList<ComputerDTO>) computerService.getAllComputers();
		for (int i = 0; i < c.size(); i++) {
			System.out.println(c.get(i).toString());

		}
	}

	/**
	 * Prints the companies.
	 */
	public void printCompanies() {
		System.out.println("'List all companies' selected ->");
		System.out.println("");
		ArrayList<CompanyDTO> co;
		try {
			co = (ArrayList<CompanyDTO>) companyService.getAllCompanies();
			for (int i = 0; i < co.size(); i++) {
				System.out.println(co.get(i).toString());

			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Show computer details.
	 */
	public void show() {
		System.out.println("'Show computer details' selected:");
		System.out.println("Please indicate which computer you are interested in using its id ->");
		Scanner scan = new Scanner(System.in);
		try {
			int id = scan.nextInt();
			// scan.close();
			System.out.println("Attempting to fetch computer details for computer ID=" + id);
			computerService.getCompDetails(id);
		}

		catch (InputMismatchException | ClassNotFoundException | SQLException | IOException e) {
			scan.next();
			System.out.println("That’s not an integer => ");
			System.out.println();
		} finally {
			//scan.close();
		}
	}

	/**
	 * Creates a computer.
	 */
	public void createComputer() {
		String name = null;
		String intr = null;
		String disc = null;
		int c_id = -1;

		System.out.println("'Create a computer' selected:");
		System.out.println(
				"Please indicate the desired attributes of the computer you wish to create (name, date introduced (yyyy-MM-dd), date discontinued (yyyy-MM-dd) & company id) ->");
		Scanner scan = new Scanner(System.in);
		try {
			name = scan.next();
			intr = scan.next();
			disc = scan.next();
			c_id = scan.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Sorry,there was an issue with the number of inputs. Creation aborted.");
		}
		System.out.println("Attempting to create computer with following attributes name=" + name);
		System.out.println("date introduced=" + intr);
		System.out.println("date discontinued=" + disc);
		System.out.println("company ID=" + c_id);
		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setId(String.valueOf(c_id)).build();
		ComputerDTO compDto = new ComputerDTO.ComputerDTOBuilder().setName(name).setIntro(intr).setDisco(disc)
				.setAny(anyDto).build();
		try {
			computerService.insertComputer(ComputerMapper.toComputer(compDto));
		} catch (DateTimeParseException | ClassNotFoundException | SQLException | IOException e) {
			System.out.println("Sorry,there was an issue with the format of either or both of the dates input.");
			System.out.println();
		}
		//scan.close();
	}

	/**
	 * Edits a computer .
	 */
	public void edit() {

		int id = -1;
		String name = null;
		String intr = null;
		String disc = null;

		System.out.println("'Update a computer' selected:");
		System.out.println("Please indicate which computer you wish to update using it's id ->");
		Scanner scan = new Scanner(System.in);

		try {
			id = scan.nextInt();
		} catch (InputMismatchException e) {
			scan.next();
			System.out.println("That’s not a valid ID (integer required) => Update Failed");
			System.out.println();
		}

		System.out.println("Please input '1' if you wish to update " + id
				+ "'s Computer name or '2' if you wish to update " + id + "'s discontinued date ->");
		int subc;
		subc = scan.nextInt();
		switch (subc) {
		case 1:
			System.out.println("Please input a new name for Computer id=" + id + ":");
			Scanner scan1 = new Scanner(System.in);

			try {
				name = scan1.next();
				computerService.updateComputerName(name, id);
				System.out.println(
						"Your modification has been carried out (hopefully, maybe, probably, definitely...unless " + id
								+ " didn't even exist to begin with)");
				System.out.println("");
			} catch (NoSuchElementException | ClassNotFoundException | SQLException | IOException e) {
				scan.next();
				scan.close();
				System.out.println("This computer is not present in the database");
			}
			break;
		case 2:
			System.out.println("Please input a new introduced & discontinued date for Computer " + id + ":");
			Scanner scan2 = new Scanner(System.in);
			intr = scan2.next();
			disc = scan2.next();
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			try {
				LocalDate date1 = LocalDate.parse(disc, formatter1);
				Date sqlDate1 = Date.valueOf(date1);
				LocalDate date2 = LocalDate.parse(intr, formatter1);
				Date sqlDate2 = Date.valueOf(date2);

				// five2.close();
				if (computerService.updateComputerDisc(sqlDate2, sqlDate1, id)) {
					System.out
							.println("Your modification has been carried out (hopefully, maybe, probably, definitely)");
					System.out.println("");
				}
			}

			catch (DateTimeParseException | ClassNotFoundException | SQLException | IOException e) {
				System.out.println(
						"Sorry,there was an issue with the format of your discontinued date input. Update failed");
				System.out.println();
			}

			break;
		}
	}

	/**
	 * Delete a computer.
	 */
	public void deleteComputer() {
		int id = -1;

		System.out.println("'Delete a computer' selected:");
		System.out.println("Please indicate which computer you wish to delete using it's id ->");
		Scanner scan = new Scanner(System.in);
		try {
			id = scan.nextInt();
			// six.close();
			computerService.deleteComputer(id);
			System.out.println("Computer " + id + " has been deleted (hopefully, maybe, probably, definitely...unless "
					+ id + " didn't even exist to begin with)");

		} catch (InputMismatchException | ClassNotFoundException | SQLException | IOException e) {
			scan.next();
			System.out.println("That’s not a valid ID (integer required) => Deletion Failed");
			System.out.println();
		}
		scan.close();
	}

	/**
	 * Show some amounts of computers (per Page).
	 */
	public void showSome() {
		int id = -1;

		boolean notdone = true;
		System.out.println("List some or all of the computers in the db ->");
		Page p = new Page(computerService.count("computer"));
		p.calcPages();
		Scanner scan = new Scanner(System.in);
		while (notdone) {
			System.out.println("Please indicate which page you wish to see:");
			try {
				id = scan.nextInt();
				// three.close();
				System.out.println("Attempting to display page " + id);
				p.setPage(id);
				ArrayList<ComputerDTO> com = (ArrayList<ComputerDTO>) computerService.viewSomeComputers(p);
				for (int i = 0; i < com.size(); i++) {
					System.out.println(com.get(i).toString());

				}
				System.out.println();
				System.out.println("If you wish to exit please input 'exit'");
			}

			catch (InputMismatchException | ClassNotFoundException | SQLException | IOException e) {
				if (scan.next().equals("exit")) {
					notdone = false;
					System.out.println("Option exited succesfully.");
				} else {
					System.out.println("That’s not an integer => ");
					System.out.println();
				}
			}
		}
		//scan.close();
	}

	/**
	 * Delete a company.
	 */
	public void deleteCompany() {
		int id = -1;

		System.out.println("'Delete a computer' selected:");
		System.out.println("Please indicate which company you wish to delete using it's id ->");
		Scanner scan = new Scanner(System.in);
		try {
			id = scan.nextInt();
			// six.close();
			companyService.deleteCompany(id);
			System.out.println("Company " + id + " has been deleted (hopefully, maybe, probably, definitely...unless "
					+ id + " didn't even exist to begin with)");

		} catch (InputMismatchException | SQLException e) {
			scan.next();
			System.out.println("That’s not a valid ID (integer required) => Deletion Failed");
			System.out.println();
		}
		//scan.close();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws SQLException           the SQL exception
	 * @throws ParseException         the parse exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void main(String[] args) throws IOException, SQLException, ParseException, ClassNotFoundException {

		Scanner sc = new Scanner(System.in);
		boolean running = true;
		int option = 0;
		
		logger.info("Log4j Enabled");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CLI.class);


		ComputerService computerService = ctx.getBean(ComputerService.class);
		CompanyService companyService = ctx.getBean(CompanyService.class);
		CLI myne = new CLI(computerService, companyService);

		while (running) {

			System.out.println("===============================================");
			System.out.println("|   CDB WONDERFUL CLI (Much options such wow) |");
			System.out.println("===============================================");
			System.out.println("| Available Options:                          |");
			System.out.println("|        1. List all computers                |");
			System.out.println("|        2. List all companies                |");
			System.out.println("|        3. Show computer details             |");
			System.out.println("|        4. Create a computer                 |");
			System.out.println("|        5. Update a computer                 |");
			System.out.println("|        6. Delete a computer                 |");
			System.out.println("|        7. Delete a company                  |");
			System.out.println("|        8. List some or all of the computers |");
			System.out.println("|        9. Exit                              |");
			System.out.println("===============================================");
			System.out.println("");
			System.out.println(" Please select the (next) option you are interested in:  ");
			
			try {
				option = sc.nextInt();
			}

			catch (InputMismatchException e) {
				sc.next();
				System.out.print("That’s not an integer => ");
				System.out.print("");
			}

			switch (option) {

			case 1:
				myne.printComputers();
				break;

			case 2:
				myne.printCompanies();
				break;

			case 3:
				myne.show();
				break;

			case 4:
				myne.createComputer();
				break;

			case 5:
				myne.edit();
				break;

			case 6:
				myne.deleteComputer();
				break;

			case 7:
				myne.deleteCompany();
				break;

			case 8:
				myne.showSome();
				break;

			case 9:
				running = false;
				System.out.println("'Exit' selected...bye bye!");
				sc.close();
				ctx.close();
				break;
			default:
				System.out.println("The option you selected is not currently available, please try again :) ");
				break;
			}

		}
	}
}
