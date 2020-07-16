package com.excilys.mysql;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class SampleTest extends DBTestCase {
	private IDatabaseTester databaseTester;

	public SampleTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				"jdbc:mysql://localhost:3306/test-db?serverTimezone=UTC");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "admintest");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "test-db");

	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("partial.xml"));
	}

	protected void setUp() throws Exception {

		databaseTester = new PropertiesBasedJdbcDatabaseTester();

		// initialize your dataset here
		// IDataSet dataSet = null;
		// ...

		// databaseTester.setDataSet( dataSet );
		// will call default setUpOperation
		// databaseTester.onSetup();
	}

	protected void tearDown() throws Exception {
		// will call default tearDownOperation
		databaseTester.onTearDown();
	}

	public void testMe() throws Exception {
		IDataSet expds = getDataSet();
		ITable expectedTable = expds.getTable("computer");
		IDatabaseConnection connection = getConnection();
		IDataSet databaseDataSet = connection.createDataSet();
		ITable actualTable = databaseDataSet.getTable("computer");
		ITable filteredTable=DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(filteredTable, actualTable);
		
	}
	
	public void t()
	{}

}
