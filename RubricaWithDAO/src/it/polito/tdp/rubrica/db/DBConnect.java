package it.polito.tdp.rubrica.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database
 * @author alex10key
 *
 */
public class DBConnect {

	private static String url = "jdbc:mysql://localhost/rubrica?user=root";
	
	/**
	 * Restituisce una nuova connessione, con i parametri a lui noti
	 * @return la nuova java.sql.Connection, oppure null se si verificano 
	 * errori di connessione
	 */
	static public Connection getConnection(){
		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
