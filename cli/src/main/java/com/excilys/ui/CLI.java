package com.excilys.ui;

import java.io.IOException;
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

import com.excilys.config.CLIconfig;
import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerDtoMapper;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

/**
 * The Class CLI.
 */

public class CLI {
	/**
	 * Instantiates a new cli.
	 *
	 * @param computerService the computer service
	 * @param companyService  the company service
	 */
	public CLI(ComputerService computerService, CompanyService companyService) {
		this.companyService = companyService;
		this.computerService = computerService;
	}

	private CompanyService companyService;
	private ComputerService computerService;
	protected static Scanner myScan;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CLI.class);

	/**
	 * Prints the computers.
	 */
	public void printComputers() {
		System.out.println("'List all computers' selected ->");
		System.out.println("");
		ArrayList<ComputerDTO> computers = (ArrayList<ComputerDTO>) computerService.getAllComputers();
		computers.forEach(computer -> System.out.println(computer.toString()));
	}

	/**
	 * Prints the companies.
	 */
	public void printCompanies() {
		System.out.println("'List all companies' selected ->");
		System.out.println("");
		ArrayList<CompanyDTO> companies;
		companies = (ArrayList<CompanyDTO>) companyService.getAllCompanies();
		companies.forEach(company -> System.out.println(company.toString()));
	}

	/**
	 * Show computer details.
	 */
	public void show() {
		System.out.println("'Show computer details' selected:");
		System.out.println("Please indicate which computer you are interested in using its id ->");
		try {
			int computerId = myScan.nextInt();
			System.out.println("Attempting to fetch computer details for computer ID=" + computerId);
			System.out.println(computerService.getCompDetails(computerId));
		}

		catch (InputMismatchException e) {
			myScan.next();
			System.out.println("That’s not an integer => ");
			System.out.println();
		}
	}

	/**
	 * Creates a computer.
	 */
	public void createComputer() {
		String name = null;
		String intr = null;
		String disc = null;
		Integer companyId = null;

		System.out.println("'Create a computer' selected:");
		System.out.println(
				"Please indicate the desired attributes of the computer you wish to create (name, date introduced (yyyy-MM-dd), date discontinued (yyyy-MM-dd) & company id) ->");
		try {
			name = myScan.next();
			intr = myScan.next();
			disc = myScan.next();
			companyId = myScan.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Sorry,there was an issue with the number of inputs. Please try again.");
		}
		System.out.println("Attempting to create computer with following attributes name=" + name);
		System.out.println("date introduced=" + intr);
		System.out.println("date discontinued=" + disc);
		System.out.println("company ID=" + companyId);
		CompanyDTO anyDto = new CompanyDTO.CompanyDTOBuilder().setcId(String.valueOf(companyId)).build();
		ComputerDTO uterDto = new ComputerDTO.ComputerDTOBuilder().setComputerName(name).setIntroduced(intr)
				.setDiscontinued(disc).setCompany(anyDto).build();
		try {
			computerService.insertComputer(ComputerDtoMapper.toComputerAdd(uterDto));
		} catch (DateTimeParseException e) {
			System.out.println(
					"Sorry,there was an issue with the format of either or both of your date input. Please try again.");
			System.out.println();
		}
	}

	/**
	 * Edits a computer .
	 */
	public void edit() {

		Integer computerId = null;
		String name = null;
		String intr = null;
		String disc = null;

		System.out.println("'Update a computer' selected:");
		System.out.println("Please indicate which computer you wish to update using it's id ->");
		Scanner scan = new Scanner(System.in);

		try {
			computerId = scan.nextInt();
		} catch (NullPointerException | InputMismatchException e) {
			scan.next();
			System.out.println("That’s not a valid ID (integer required) => Update Failed");
			System.out.println();
		}

		if (computerId != null) {
			System.out.println("Please input '1' if you wish to update " + computerId
					+ "'s Computer name or '2' if you wish to update " + computerId + "'s discontinued date ->");
			Integer subOption = null;
			try {
				subOption = scan.nextInt();
			} catch (NullPointerException | InputMismatchException e) {
				scan.next();
				System.out.println("That’s not a valid ID (integer required) => Update Failed");
				System.out.println();
			}
			if (subOption != null) {
				switch (subOption) {
				case 1:
					System.out.println("Please input a new name for Computer id=" + computerId + ":");
					Scanner subScan1 = new Scanner(System.in);

					try {
						name = subScan1.next();
						computerService.updateComputerName(name, computerId);
						System.out.println(
								"Your modification has been carried out (hopefully, maybe, probably, definitely...unless "
										+ computerId + " didn't even exist to begin with)");
						System.out.println("");
					} catch (NullPointerException | NoSuchElementException e) {
						scan.next();
						scan.close();
						System.out.println("This computer is not present in the database");
					}
					break;
				case 2:
					System.out.println(
							"Please input a new introduced & discontinued date for Computer " + computerId + ":");
					Scanner subScan2 = new Scanner(System.in);
					intr = subScan2.next();
					disc = subScan2.next();
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
					try {
						LocalDate introduced = LocalDate.parse(intr, formatter1);
						LocalDate discontinued = LocalDate.parse(disc, formatter1);

						if (computerService.updateComputerDisc(introduced, discontinued, computerId)) {
							System.out.println(
									"Your modification has been carried out (hopefully, maybe, probably, definitely)");
							System.out.println("");
						}
					}

					catch (DateTimeParseException e) {
						System.out.println(
								"Sorry,there was an issue with the format of your discontinued date input. Update failed");
						System.out.println();
					}
					break;
				}
			}
		}
	}

	/**
	 * Delete a computer.
	 */
	public void deleteComputer() {
		int computerId = -1;

		System.out.println("'Delete a computer' selected:");
		System.out.println("Please indicate which computer you wish to delete using it's id ->");
		try {
			computerId = myScan.nextInt();
			computerService.deleteComputer(computerId);
			System.out.println(
					"Computer " + computerId + " has been deleted (hopefully, maybe, probably, definitely...unless "
							+ computerId + " didn't even exist to begin with)");

		} catch (InputMismatchException e) {
			myScan.next();
			System.out.println("That’s not a valid ID (integer required) => Deletion Failed");
			System.out.println();
		}
	}

	/**
	 * Show some amounts of computers (per Page).
	 */
	public void showSome() {
		int computerId = -1;

		boolean notDone = true;
		System.out.println("List some or all of the computers in the db ->");
		Page p = new Page(computerService.count("computer"));
		p.calcPages();
		while (notDone) {
			System.out.println("Please indicate which page you wish to see:");
			try {
				computerId = myScan.nextInt();
				System.out.println("Attempting to display page " + computerId);
				p.setPage(computerId);
				ArrayList<ComputerDTO> computers = (ArrayList<ComputerDTO>) computerService.viewSomeComputers(p);
				computers.forEach(computer -> System.out.println(computer.toString()));

				System.out.println();
				System.out.println("If you wish to exit please input 'exit'");
			}

			catch (InputMismatchException e) {
				if (myScan.next().equals("exit")) {
					notDone = false;
					System.out.println("Option exited succesfully.");
				} else {
					System.out.println("That’s not an integer => ");
					System.out.println();
				}
			}
		}
	}

	/**
	 * Delete a company.
	 */
	public void deleteCompany() {
		Integer companyId = null;

		System.out.println("'Delete a computer' selected:");
		System.out.println("Please indicate which company you wish to delete using it's id ->");
		try {
			companyId = myScan.nextInt();
			companyService.deleteCompany(companyId);
			System.out.println(
					"Company " + companyId + " has been deleted (hopefully, maybe, probably, definitely...unless "
							+ companyId + " didn't even exist to begin with)");

		} catch (InputMismatchException e) {
			myScan.next();
			System.out.println("That’s not a valid ID (integer required) => Deletion Failed");
			System.out.println();
		}
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
	public static void main(String[] args) {

		myScan = new Scanner(System.in);
		boolean running = true;
		int option = 0;

		logger.info("Log4j Enabled");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CLIconfig.class,
				ComputerService.class, CompanyService.class, ComputerDAO.class, CompanyDAO.class);

		ComputerService computerService = ctx.getBean(ComputerService.class);
		CompanyService companyService = ctx.getBean(CompanyService.class);
		CLI cli = new CLI(computerService, companyService);

		while (running) {

			System.out.println("===============================================");
			System.out.println("|   CDB WONDERFUL CLI (Much options such wow) |");
			System.out.println("===============================================");
			System.out.println("| Available Options:                          |");
			System.out.println("|        1. Display all computers             |");
			System.out.println("|        2. Display all companies             |");
			System.out.println("|        3. Show computer details             |");
			System.out.println("|        4. Create a computer                 |");
			System.out.println("|        5. Update a computer                 |");
			System.out.println("|        6. Delete a computer                 |");
			System.out.println("|        7. Delete a company                  |");
			System.out.println("|        8. Display pages of computers        |");
			System.out.println("|        9. Exit                              |");
			System.out.println("===============================================");
			System.out.println("");
			System.out.println(" Please select the (next) option you are interested in:  ");

			try {
				option = myScan.nextInt();
			}

			catch (InputMismatchException e) {
				myScan.next();
				System.out.print("That’s not an integer => ");
				System.out.print("");
			}

			switch (option) {

			case 1:
				cli.printComputers();
				break;

			case 2:
				cli.printCompanies();
				break;

			case 3:
				cli.show();
				break;

			case 4:
				cli.createComputer();
				break;

			case 5:
				cli.edit();
				break;

			case 6:
				cli.deleteComputer();
				break;

			case 7:
				cli.deleteCompany();
				break;

			case 8:
				cli.showSome();
				break;

			case 9:
				running = false;
				System.out.println("'Exit' selected...bye bye!");
				myScan.close();
				ctx.close();
				break;
			default:
				System.out.println("The option you selected is not currently available, please try again :) ");
				break;
			}
		}
	}
}
