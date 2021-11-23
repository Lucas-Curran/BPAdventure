package com.mygdx.game.database;

import java.sql.*;

public class SqliteManager {
	
	private static String playerURL = "jdbc:sqlite:Progress.db";
	private static String inventoryURL = "jdbc:sqlite:Inventory.db";
	private static Connection conn = null;
	
	
	//Position/save point
	//Currency
	//Inventory (seperate database)
	
	public static void main(String[] args) {
		connect("Player");
		connect("Inventory");
		createTable();
	}
	
	public static Connection connect(String db) {
		try {
			Class.forName("org.sqlite.JDBC");
			if (db.equals("Player")) {
				conn = DriverManager.getConnection(playerURL);
			} else if (db.equals("Inventory")) {
				conn = DriverManager.getConnection(inventoryURL);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());		
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} 
		return conn;
	}
	
	public static void createTable() {
		try {
			String playerSQL = "CREATE TABLE IF NOT EXISTS Progress (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
					+ "SavePoint INTEGER, \n"
					+ "Currency INTEGER, \n"
					+ ");";
			
			String inventorySQL = "CREATE TABLE IF NOT EXISTS Inventory (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
					+ "Row INTEGER, \n"
					+ "Column INTEGER, \n"
					+ "Item INTEGER, \n"
					+ "Amount INTEGER, \n"
					+ ");";
			
			System.out.println("Conection to SQLite has been established.");
			Statement playerStatement = connect("Player").createStatement();
			Statement inventoryStatement = connect("Inventory").createStatement();
			playerStatement.execute(playerSQL);
			inventoryStatement.execute(inventorySQL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("Table created");
	}

}
