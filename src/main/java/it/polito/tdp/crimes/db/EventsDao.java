package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
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

	public List<String> getOffensesId() {
		String sql = "SELECT DISTINCT offense_category_id " + 
				"FROM events";
		List<String> offenses = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				offenses.add(res.getString("offense_category_id"));
			}
			conn.close();
			return offenses;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel collegamento al database!");
		}
		return null;
	}

	public List<Integer> getMonths() {
		String sql = "SELECT DISTINCT MONTH(reported_date) AS month " + 
				"FROM EVENTS " + 
				"GROUP BY Month(reported_date) ASC ";
		List<Integer> months = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				months.add(res.getInt("month"));
			}
			conn.close();
			return months;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel collegamento al database!");
		}
		return null;
	}

	public List<String> getVertexes(String offense, Integer month) {
		String sql = "SELECT distinct offense_type_id AS o " + 
				"FROM EVENTS " + 
				"WHERE offense_category_id=? AND MONTH(reported_date)=?";
		List<String> vertexes = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, offense);
			st.setInt(2, month);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				vertexes.add(res.getString("o"));
			}
			conn.close();
			return vertexes;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel collegamento al database!");
		}
		return null;
		
	}

	public List<Adiacenza> getAdiacenza(String offense, Integer month) {
		String sql ="SELECT e1.offense_type_id AS o1,e2.offense_type_id AS o2, COUNT(DISTINCT(e1.neighborhood_id)) AS peso " + 
				"FROM events AS e1, events AS e2 " + 
				"WHERE e1.offense_category_id=? " + 
				"AND e2.offense_category_id=? " + 
				"AND MONTH(e1.reported_date)=? " + 
				"AND MONTH(e2.reported_date)=? " + 
				"AND e1.offense_type_id<>e2.offense_type_id " + 
				"AND e1.neighborhood_id=e2.neighborhood_id " + 
				"GROUP BY e1.offense_type_id,e2.offense_type_id";
		List<Adiacenza> adiacenze = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, offense);
			st.setString(2,offense);
			st.setInt(3, month);
			st.setInt(4, month);
			ResultSet res  = st.executeQuery();
			while(res.next()) {
				Adiacenza a = new Adiacenza(res.getString("o1"),res.getString("o2"), res.getInt("peso"));
				if(a.getWeight()>0)
					adiacenze.add(a);
			}
			conn.close();
			return adiacenze;
	      }catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel collegamento al database!");
		}
		return null;
	}		
}


