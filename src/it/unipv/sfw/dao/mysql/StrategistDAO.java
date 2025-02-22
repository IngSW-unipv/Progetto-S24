package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;

/**
 * Data Access Object (DAO) per le operazioni relative allo stratega,
 * in particolare per l'inserimento di eventi di log.
 */
public class StrategistDAO {

    private static String SCHEMA = "";

    /**
     * Inserisce un nuovo evento nel log.
     *
     * @param id_staff L'ID dello staff che ha causato l'evento.
     * @param desc     La descrizione dell'evento.
     */
    public void insertLogEvent(String id_staff, String desc) {
    	
    	SCHEMA = "log_event";
    			
        PreparedStatement st1;
        int rs1 = 0;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "INSERT INTO " + SCHEMA + " (ID_STAFF, DESCRIPTION) VALUES(?,?)";
            st1 = conn.prepareStatement(query);

            st1.setString(1, id_staff);

            st1.setString(2, desc);

            rs1 = st1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    public void insertStrategistOnVehicle(String msn, String id) throws VehicleNotFoundException{
    	
    	SCHEMA = "VEHICLE";

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE" + SCHEMA + " SET ID_STRATEGIST = ? WHERE MSN = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id);
			st1.setString(2, msn);

			rs1 = st1.executeQuery();

			if (!rs1.next()) { // Spostati alla prima riga del risultato
				throw new WrongIDException();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

}