package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.Evento;
import it.polito.tdp.crimes.model.Evento.EventType;



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
	
	public List<Integer> anni(){
		
		String sql= "select distinct year(reported_date) as anno " + 
				"from events " + 
				"order by anno";
		
		List<Integer> anni = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				anni.add(res.getInt("anno"));
			}
			
			conn.close();
			return anni;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public List<Integer> vertici(){
		String sql = "select distinct district_id " + 
				"from events " + 
				"order by district_id";
		List<Integer> vertici = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				vertici.add(res.getInt("district_id"));
			}
			
			conn.close();
			return vertici;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void listaDistretti(List<Distretto> distretti, Integer anno) {
		String sql = "select   district_id, avg(distinct geo_lat) as lat, avg(distinct geo_lon) as lon " + 
				"from events " + 
				"where year(reported_date)=? " + 
				"group by district_id " + 
				"order by district_id";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Distretto d = new Distretto(res.getInt("district_id"),new LatLng(res.getDouble("lat"), res.getDouble("lon")));
				distretti.add(d);

			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public Integer centrale(Integer anno) {
		
		String sql ="select district_id, count(incident_id) as c " + 
				"from events " + 
				"where year(reported_date)=? " + 
				"group by events.`district_id` " + 
				"order by c asc";
		Integer centrale=null;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) { //basta la prima riga
				centrale = res.getInt("district_id");
			}
			
			conn.close();
			return centrale;
			

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		
	}
	
	public PriorityQueue<Evento> coda(int giorno, int mese, int anno){
		String sql="select offense_category_id, district_id, reported_date " + 
				"from events " + 
				"where year(reported_date)=? " + 
				"and month(reported_date)=? " + 
				"and day(reported_date) = ? " + 
				"order by reported_date";
		PriorityQueue<Evento> coda = new PriorityQueue<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) { 
				coda.add(new Evento(res.getString("offense_category_id"),
									res.getTimestamp("reported_date").toLocalDateTime(),
									res.getInt("district_id"), EventType.CRIMINE));
		 
			}
			
			conn.close();
			return coda; 
			

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
