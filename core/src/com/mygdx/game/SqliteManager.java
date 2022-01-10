package com.mygdx.game;

import java.sql.*;

public class SqliteManager {
	
	private static String playerURL = "jdbc:sqlite:Progress.db";
//	private static String inventoryURL = "jdbc:sqlite:Inventory.db";
	private static Connection conn = null;
	
	/**
	 * Creates and connects to databases
	 */
	public SqliteManager() {
		connect();
		createTable();
		defaultInfo();
	}
	
	
	/**
	 * Connects to database
	 * @return - Connection
	 */
	public static Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
//			if (db.equals("Player")) {
				conn = DriverManager.getConnection(playerURL);
//			} else if (db.equals("Inventory")) {
//				conn = DriverManager.getConnection(inventoryURL);
//			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());		
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} 
		return conn;
	}
	
	/**
	 * Creates database if they don't exist
	 */
	public static void createTable() {
		try {
			String playerSQL = "CREATE TABLE IF NOT EXISTS Progress (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
					+ "Stage INTEGER, \n"
					+ "Currency INTEGER, \n"
					+ "Health INTEGER, \n"
					+ "Volume INTEGER"
					+ ");";
			
			String inventorySQL = "CREATE TABLE IF NOT EXISTS Inventory (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
					+ "Row INTEGER, \n"
					+ "Column INTEGER, \n"
					+ "Item INTEGER, \n"
					+ "Amount INTEGER, \n"
					+ "Tier INTEGER"
					+ ");";
			
			System.out.println("Conection to SQLite has been established.");
			Statement playerStatement = connect().createStatement();
//			Statement inventoryStatement = connect().createStatement();
			playerStatement.execute(playerSQL);
			playerStatement.execute(inventorySQL);
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

	
	public static void defaultInfo() {
		String sql = "INSERT INTO Progress(Stage,Currency,Health,Volume) Values(0,0,100,50)";
		try {
			conn = DriverManager.getConnection(playerURL);
			PreparedStatement input = connect().prepareStatement(sql);
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateVolume(int volume) {
		String sql = "UPDATE Progress SET Volume = " + volume;
		try {
			PreparedStatement input = connect().prepareStatement(sql);
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getVolume() {
		String sql = "SELECT Volume FROM Progress";
		int volumeData = 50;
		try {
			conn = DriverManager.getConnection(playerURL);
			Statement stmt = connect().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			volumeData = rs.getInt("volume");
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return volumeData;
	}
}
