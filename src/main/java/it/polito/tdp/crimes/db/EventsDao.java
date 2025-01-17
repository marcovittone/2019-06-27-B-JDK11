package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.crimes.model.Connessione;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getTypeGivenMontAndCategory(String categoria, int month) {
		
		
		String sql = "SELECT DISTINCT offense_type_id "
				+ "FROM `events` "
				+ "WHERE offense_category_id = ? AND MONTH(reported_date) = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, categoria);
			st.setInt(2, month);
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_type_id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getAllCategories() {

		
		String sql = "SELECT DISTINCT offense_category_id\n"
				+ "FROM `events`" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<Integer> getAllMonths() {
		
		String sql = "SELECT DISTINCT MONTH(reported_date) AS mese "
				+ "FROM `events` "
				+ "ORDER BY mese" ;
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		List<Integer> list = new ArrayList<>() ;
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			try {
				list.add(res.getInt("mese"));
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		conn.close();
		return list ;

	} catch (SQLException e) {
		e.printStackTrace();
		return null ;
	}
	}
	
public List<Connessione> getEdges(String categoria, int month) {
		
		
		String sql = "SELECT e1.offense_type_id AS t1, e2.offense_type_id AS t2, COUNT(DISTINCT e1.neighborhood_id) AS conto "
				+ "FROM `events` AS e1, `events` AS e2 "
				+ "WHERE MONTH(e1.reported_date) = ? AND MONTH(e2.reported_date) = MONTH(e1.reported_date) "
				+ "AND  e1.offense_category_id = ? AND e2.offense_category_id = e1.offense_category_id "
				+ "AND  e1.neighborhood_id = e2.neighborhood_id "
				+ "AND e1.offense_type_id < e2.offense_type_id "
				+ "GROUP BY t1, t2" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(2, categoria);
			st.setInt(1, month);
			
			List<Connessione> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					
					Connessione c = new Connessione(res.getString("t1"),res.getString("t2"),res.getInt("conto"));
					list.add(c);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	

}
