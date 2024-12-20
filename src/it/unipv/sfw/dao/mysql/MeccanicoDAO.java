package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;

//INSERT PILOTI, SELECT PILOTA, INSERT REQUEST, SELECT STATO COMPONENTI, REMOVE PILOTA
public class MeccanicoDAO {

	private String SCHEMA = "";

	// CHECK STAFF

	public void checkStaff(String id) throws WrongIDException {

		SCHEMA = "staff";

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT COUNT(*) FROM " + SCHEMA + " WHERE ID = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id);

			rs1 = st1.executeQuery();

			if (!rs1.next()) { // Spostati alla prima riga del risultato
				throw new WrongIDException();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// CHECK VEHICLE

	public void checkVehicle(String msn) throws VehicleNotFoundException {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE MSN = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, msn);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (!rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new VehicleNotFoundException(msn);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Errore di connessione al database", e);
		}
	}

	// INSERT MECCANICO

	public boolean insertMeccOnVehicle(String msn, String id) {

		SCHEMA = "staff";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_VEHICLE = ? WHERE ID = ? ";
			st1 = conn.prepareStatement(query);

			st1.setString(1, msn);
			st1.setString(2, id);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;
	}

	// CHECK PILOT

	public void checkPilot(String id_p) throws PilotNotFoundException {

		SCHEMA = "pilot";

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID  = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id_p);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (!rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new PilotNotFoundException(id_p);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// INSERT PILOTONVEHICLE

	public boolean insertPilotOnVehicle(String id_p, String msn) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_PILOT = ? WHERE MSN = ? ";

			st1 = conn.prepareStatement(query);

			st1.setString(1, id_p);
			st1.setString(2, msn);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERRORE DI ESECUZIONE");
			e.printStackTrace();
		}

		return esito;
	}

	// REMOVE PILOT

	public boolean removePilot(String idp) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_PILOT = NULL WHERE ID_PILOT = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, idp);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	// SELECT ID PILOT

	public void selectP(String id, String name, String surname, String number) throws PilotNotFoundException {

		SCHEMA = "pilot";

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID = ? AND NAME = ? AND SURNAME = ? AND NUMBER = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id);
			st1.setString(2, name);
			st1.setString(3, surname);
			st1.setString(4, number);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (!rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new PilotNotFoundException(id);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// CHECK PILOTONVEHICLE

	public boolean checkPilotOnVehicle(String idp) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		ResultSet rs1;
		boolean result = false;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID_PILOT  = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, idp);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				return result = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// CHECK COMPO

	public void checkCompo(String id_c, String name, String status) throws ComponentNotFoundException {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		String idc = String.valueOf(id_c);

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID  = ? AND NAME = ? AND STATUS = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, idc);
			st1.setString(2, name);
			st1.setString(3, status);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (!rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new ComponentNotFoundException(idc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// CHECK ID COMPO

	public void checkIdCompo(String id_c) throws ComponentNotFoundException {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		String idc = String.valueOf(id_c);

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID  = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, idc);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (!rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new ComponentNotFoundException(idc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// INSERT COMPO

	public boolean insertComponent(String id, String msn) {

		SCHEMA = "component";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET WAREHOUSE = 0, STATUS = 'USED',  ID_VEHICLE = ? WHERE ID = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, msn);
			st1.setString(2, id);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	// UPDATE WEAR

	public boolean updateWear(int wear, String id) {

		SCHEMA = "component";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET WEAR = ? WHERE ID = ?";
			st1 = conn.prepareStatement(query);

			String c1 = String.valueOf(wear);
			st1.setString(1, c1);

			String c2 = String.valueOf(id);
			st1.setString(2, c2);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	// REMOVE COMPO

	public void removeComponent(String id_c, String id_v) {

		SCHEMA = "component";

		PreparedStatement st1;
		int rs1 = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA
					+ " SET WAREHOUSE = 1, STATUS = 'USED', ID_VEHICLE = NULL WHERE ID = ? AND ID_VEHICLE = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id_c);
			st1.setString(2, id_v);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// INSERT REQUEST

	public boolean insertRequest(String desc, String id_s, String id_c, String id_v) {

		SCHEMA = "request";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "INSERT INTO " + SCHEMA
					+ " (DESCRIPTION, ID_STAFF, ID_COMPONENT, ID_VEHICLE) VALUES(?,?,?,?)";
			st1 = conn.prepareStatement(query);

			st1.setString(1, desc);

			st1.setString(2, id_s);

			st1.setString(3, id_c);

			st1.setString(4, id_v);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	// CHECK REQUEST

	public void checkRequest(String id_c) throws WrongRequestException {

		SCHEMA = "request";

		PreparedStatement st1;
		ResultSet rs1;

		String idc = String.valueOf(id_c);

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE ID_COMPONENT  = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, idc);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				throw new WrongRequestException(idc);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  insertLogEvent(String id_staff, String desc) {

		SCHEMA = "log_event";
		
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
