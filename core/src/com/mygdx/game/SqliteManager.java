package com.mygdx.game;

import java.sql.*;

public class SqliteManager {
	
	private static final String URL = "jdbc:sqlite:Progress.db";
	private static Connection conn = null;

	
	/**
	 * Creates databases if they do not exist
	 */
	public SqliteManager() {
		createTable();
	}
	
	
	/**
	 * Connects to database
	 * @return - Connection
	 */
	public static Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(URL);
		} catch (SQLException e) {
//			System.out.println(e.getMessage());		
		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
		} 
		return conn;
	}
	
	/**
	 * Creates database if they don't exist
	 */
	public static void createTable() {
		try {
			String playerSQL = "CREATE TABLE IF NOT EXISTS Progress (\n"
					+ "id INTEGER PRIMARY KEY, \n"
					+ "Stage INTEGER, \n"
					+ "Currency INTEGER, \n"
					+ "Health INTEGER, \n"
					+ "Volume INTEGER"
					+ ");";
			
			String inventorySQL = "CREATE TABLE IF NOT EXISTS Inventory (\n"
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
					+ "Item INTEGER"
					+ ");";
			
//			System.out.println("Conection to SQLite has been established.");
			//Creates statment to execute
			Statement statement = connect().createStatement();
			//Executes specific statments
			statement.execute(playerSQL);
			statement.execute(inventorySQL);
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
//				System.out.println(ex.getMessage());
			}
		}
//		System.out.println("Table created");
	}

	
	/**
	 * Inserts default values in progress
	 * Used when starting a new game
	 */
	public static void defaultInfo() {
		String sql = "INSERT INTO Progress(id,Stage,Currency,Health,Volume) Values(1,0,0,100,50)";
		try {
			//Connects to progress table
			conn = DriverManager.getConnection(URL);
			PreparedStatement input = connect().prepareStatement(sql);
			//executes statment and closes connection
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Updates volume in the progress table
	 * @param volume
	 */
	public void updateVolume(int volume) {
		String sql = "UPDATE Progress SET Volume = " + volume + " WHERE id = 1";
		try {
			// creates statment to execute
			PreparedStatement input = connect().prepareStatement(sql);
			// executes statment
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gets volume value from progress table
	 * @return - Integer value of volume
	 */
	public int getVolume() {
		String sql = "SELECT Volume FROM Progress WHERE id = 1";
		int volumeData = 50;
		try {
			//Connects to progress table
			conn = DriverManager.getConnection(URL);
			Statement stmt = connect().createStatement();
			//Executes statment and gets volume
			ResultSet rs = stmt.executeQuery(sql);
			volumeData = rs.getInt("volume");
			conn.close();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
		return volumeData;
	}
	
	/**
	 * Updates health in progress table
	 * @param hp - Integer value of health
	 */
	public void updateHealth(int hp) {
		String sql = "UPDATE Progress SET Health = " + hp + " WHERE id = 1";
		try {
			//Connects to progress table
			PreparedStatement input = connect().prepareStatement(sql);
			//executes update
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gets hp stored in progress table
	 * @return - Integer value of health
	 */
	public int getHealth() {
		String sql = "SELECT Health FROM Progress WHERE id = 1";
		int health = 100;
		try {
			//connect to progress table
			conn = DriverManager.getConnection(URL);
			Statement stmt = connect().createStatement();
			//execute select statment
			ResultSet rs = stmt.executeQuery(sql);
			health = rs.getInt("health");
			conn.close();
		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
		return health;
	}
	
	/**
	 * Updates currency in progress table
	 * @param money - Integer value of money
	 */
	public void updateMoney(int money) {
		String sql = "UPDATE Progress SET Currency = " + money + " WHERE id = 1";
		try {
			//Connects to progress table
			PreparedStatement input = connect().prepareStatement(sql);
			//executes update
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gets hp stored in progress table
	 * @return - Integer value of health
	 */
	public int getMoney() {
		String sql = "SELECT Currency FROM Progress WHERE id = 1";
		int money =  0;
		try {
			//connect to progress table
			conn = DriverManager.getConnection(URL);
			Statement stmt = connect().createStatement();
			//execute select statment
			ResultSet rs = stmt.executeQuery(sql);
			money = rs.getInt("currency");
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return money;
	}
	
	/**
	 * Updates stage in progress table
	 * @param stage - Stage number
	 */
	public void updateStage(int stage) {
		String sql = "UPDATE Progress SET Stage = " + stage + " WHERE id = 1";
		try {
			//Connects to progress table
			PreparedStatement input = connect().prepareStatement(sql);
			//executes update
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gets stage number stored in progress table
	 * @return - Stage number
	 */
	public int getStage() {
		String sql = "SELECT Stage FROM Progress WHERE id = 1";
		int stage =  0;
		try {
			//connect to progress table
			conn = DriverManager.getConnection(URL);
			Statement stmt = connect().createStatement();
			//execute select statment
			ResultSet rs = stmt.executeQuery(sql);
			stage = rs.getInt("currency");
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return stage;
	}
	
	/**
	 * Updates all values in progress table excluding volume
	 * @param stage - Stage number
	 * @param health - Health value
	 * @param currency - Amount of coins
	 */
	public void updateAll(int stage, int health, int currency) {
		String sql = "UPDATE Progress SET"
				+ "Stage = " + stage + ","
				+ "Health = " + health + ","
				+ "Currency = " + currency
				+ "WHERE id = 1";
		try {
			//Connects to progress table
			PreparedStatement input = connect().prepareStatement(sql);
			//executes update
			input.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Clears out both tables in database
	 */
	public boolean clearTable(boolean volumeChanged) {
		 String sql = "DELETE FROM Progress";
		 String sql2 = "DELETE FROM Inventory";
		 String volChangedSQL = "UPDATE Progress SET Stage = 0, Currency = 0, Health = 100 WHERE id = 1";
	        try {
	        	//Creates sql statments to execute
	        	PreparedStatement pstmt;
	        	if (volumeChanged) {
	        		pstmt = connect().prepareStatement(volChangedSQL);
		            pstmt.executeUpdate();
	        	} else {
	        		pstmt = connect().prepareStatement(sql);
		            pstmt.executeUpdate();
		            defaultInfo();
	        	}
	        	
	            PreparedStatement pstmt2 = connect().prepareStatement(sql2);
	            // execute the delete statement
	            pstmt2.executeUpdate();
	            conn.close();
	            return true;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	}
	
}
