package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import it.unipv.sfw.model.vehicle.Vehicle;

public class VehicleDAO {
	
	private static final String SCHEMA = "vehicle";
	
	public boolean timeSector(Vehicle v) {
		
		PreparedStatement st1;
		int rs1;
		
		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET TIME_SECTOR1 = ?, TIME_SECTOR2 = ?, TIME_SECTOR3 = ? WHERE MSN = 'SF24-001' ";
			st1 = conn.prepareStatement(query);
			
			String c1 = String.valueOf(v.getTimeSect1());
			st1.setString(1, c1);
			
			String c2 = String.valueOf(v.getTimeSect2());
			st1.setString(2, c2);
			
			String c3 = String.valueOf(v.getTimeSect3());
			st1.setString(3, c3);
			
//			String c4 = String.valueOf(v.getMSN());
//			st1.setString(4, c4);
			
			rs1 = st1.executeUpdate();
			
		}catch(SQLIntegrityConstraintViolationException e) {
			esito = false;
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return esito;
	
	}
	
}
