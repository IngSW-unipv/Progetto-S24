package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.pilot.Pilota;

//INSERT PILOTI, SELECT PILOTA, INSERT REQUEST, SELECT STATO COMPONENTI, REMOVE PILOTA
public class MeccanicoDAO {

	private String SCHEMA = "";

	public boolean insertRequest(String desc, String id_s, int id_c, String id_v) {

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

			String idc = String.valueOf(id_c);
			st1.setString(3, idc);

			st1.setString(4, id_v);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	public boolean insertPilot(String name, String surname, int n) {

		SCHEMA = "pilot";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "INSERT INTO " + SCHEMA + " (NAME, SURNAME, NUMBER) VALUES(?,?,?)";
			st1 = conn.prepareStatement(query);

			st1.setString(1, name);
			st1.setString(2, surname);

			String convert = String.valueOf(n);

			st1.setString(3, convert);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	public boolean insertMeccOnVehicle(String msn) {

		SCHEMA = "staff";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_ VEHICLE = ? ";
			st1 = conn.prepareStatement(query);

			st1.setString(1, msn);
			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;
	}

	public boolean insertPilotOnVehicle(int id_p) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_ PILOT = ? ";
			st1 = conn.prepareStatement(query);

			String convert = String.valueOf(id_p);

			st1.setString(1, convert);
			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;
	}

	public boolean insertComponent(int id, String msn) {

		SCHEMA = "component";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET WAREHOUSE = 0 AND ID_VEHICLE = ? WHERE ID = ?";
			st1 = conn.prepareStatement(query);

			String convert = String.valueOf(id);

			st1.setString(2, msn);
			st1.setString(1, convert);
			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	public boolean updateWear(int wear, int id) {

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

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	public ArrayList<Pilota> selectAllPilot() {

		SCHEMA = "pilot";

		ArrayList<Pilota> result = new ArrayList<>();

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA;

			st1 = conn.prepareStatement(query);
			rs1 = st1.executeQuery();

			while (rs1.next()) {

				String number = rs1.getString(4);

				int n1 = Integer.parseInt(number);

				Pilota p = new Pilota(rs1.getString(2), rs1.getString(3), n1);
				result.add(p);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public boolean removePilot(String msn, int idp) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_PILOT = NULL WHERE MSN = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, msn);

			String id = String.valueOf(idp);
			st1.setString(2, id);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

	public boolean removeComponent(int id_c, int id_v) {

		SCHEMA = "component";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA
					+ " SET WAREHOUSE = 1 AND STATUS = 'USED'  WHERE ID = ? AND ID_VEHICLE = ?";
			st1 = conn.prepareStatement(query);

			String n1 = String.valueOf(id_c), n2 = String.valueOf(id_v);

			st1.setString(1, n1);
			st1.setString(2, n2);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}
	/*
	 * public ArrayList<Components> selectComponent(String msn) {
	 * 
	 * SCHEMA = "component";
	 * 
	 * ArrayList<Components> result = new ArrayList<>();
	 * 
	 * PreparedStatement st1; ResultSet rs1;
	 * 
	 * try (DBConnection db = new DBConnection(SCHEMA)) { Connection conn =
	 * db.getConnection();
	 * 
	 * String query = "SELECT * FROM" + SCHEMA + "WHERE ID_VEHICLE = ?"; st1 =
	 * conn.prepareStatement(query);
	 * 
	 * st1.setString(1, msn);
	 * 
	 * rs1 = st1.executeQuery();
	 * 
	 * String convert = rs1.getString(1);
	 * 
	 * int id = Integer.parseInt(convert);
	 * 
	 * Components c = new Components(id, rs1.getString(2), rs1.getString(3));
	 * result.add(c);
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return result;
	 * 
	 * }
	 * 
	 */

	public String selectIdP() {

		SCHEMA = "pilot";

		PreparedStatement st1;
		ResultSet rs1;

		String idp = "";

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT ID FROM " + SCHEMA;
			st1 = conn.prepareStatement(query);

			rs1 = st1.executeQuery();

			idp = rs1.getString(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idp;
	}

	public boolean checkPilot(int id_p) {

		SCHEMA = "pilot";

		PreparedStatement st1;
		ResultSet rs1;

		boolean esito = false;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA + " WHERE MSN = ?";
			st1 = conn.prepareStatement(query);

			String id = String.valueOf(id_p);
			st1.setString(1, id);

			rs1 = st1.executeQuery();

			// Verifica se ci sono risultati
			if (rs1.next()) {
				// Accedi ai dati solo dopo rs.next()
				String pilotName = rs1.getString("PILOT_NAME");
				return esito = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito; // Nessun risultato trovato
	}

	public int checkCompo(int id_c, String name) {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		int result = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT COUNT (*) FROM " + SCHEMA + " WHERE ID = ? AND NAME = ?";
			st1 = conn.prepareStatement(query);

			String idc = String.valueOf(id_c);

			st1.setString(1, idc);
			st1.setString(2, name);

			rs1 = st1.executeQuery();

			result = rs1.getInt(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
