package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StrategistDAO {
	
	private static final String SCHEMA = "log_event";
	
	public void  insertLogEvent(String id_staff, String desc) {

		PreparedStatement st1;
		int rs1 = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "INSERT INTO " + SCHEMA +" (ID_STAFF, DESCRIPTION)"+ "VALUES(?,?)";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id_staff);
			
			st1.setString(2,  desc);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
