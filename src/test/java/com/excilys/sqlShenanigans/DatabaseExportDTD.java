package com.excilys.sqlShenanigans;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;

public class DatabaseExportDTD
{
    public static void main(String[] args) throws Exception
    {
        // database connection
        //Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test-db?serverTimezone=UTC",
		        "admintest", "qwerty1234");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("test.dtd"));
    }
}
