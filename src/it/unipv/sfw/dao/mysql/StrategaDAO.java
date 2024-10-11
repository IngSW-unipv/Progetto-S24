package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class StrategaDAO {
	
private  String SCHEMA ="";
	
	public ArrayList<Long>  selectAllVehicle(String msn){
		
		SCHEMA ="vehicle";
		
		ArrayList<Long> result = new ArrayList<>();
	
		PreparedStatement st1;
		ResultSet rs1;
		
		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA +"WHERE MSN = ?" ;
			
			st1 = conn.prepareStatement(query);
			
			st1.setString(1, msn);
			
			rs1 = st1.executeQuery();
			
			while(rs1.next()) {
				
				long ts1 = Long.parseLong( rs1.getString(2)),
					     ts2 = Long.parseLong( rs1.getString(3)),
						 ts3 = Long.parseLong(rs1.getString(4));
				
				result.add(ts1);
				result.add(ts2);
				result.add(ts3);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
    public ArrayList<String>  selectAllComponent(){
    	
		SCHEMA = "component";
		
		ArrayList<String> result = new ArrayList<>();
	
		PreparedStatement st1;
		ResultSet rs1;
		
		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + "WHERE WAREHOUSE = 0" ;
			
			st1 = conn.prepareStatement(query);
			
			rs1 = st1.executeQuery();
			
			while(rs1.next()) {
				
			   String id = rs1.getString(1),
					   	  name =  rs1.getString(2),
					   	  wear = rs1.getString(3);
					
				result.add(id);
				result.add(name);
				result.add(wear);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
}
