package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;

//STAMPARE LA LISTA DELLE RICHIESTE, RIMUOVERE RICHIESTE, AGGIORNARE VALORE WEAR
public class MagazziniereDAO {

	private String SCHEMA = "";

	public Set<Request> selectAllRequest() {

		SCHEMA = "request";

		Set<Request> result = new HashSet<>();

		PreparedStatement st1;
		ResultSet rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT * FROM " + SCHEMA;

			st1 = conn.prepareStatement(query);
			rs1 = st1.executeQuery();

			while (rs1.next()) {

				String id = rs1.getString(1), id_component = rs1.getString(4);

				int n1, n2;

				n1 = Integer.parseInt(id);
				n2 = Integer.parseInt(id_component);

				Request r = new Request(n1, rs1.getString(2), rs1.getString(3), n2, rs1.getString(5));
				result.add(r);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public boolean removeRequest(int id) {

		SCHEMA = "request";

		PreparedStatement st1;
		int rs1;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "DELETE  FROM " + SCHEMA + " WHERE ID_COMPONENT = ?";

			String convert = String.valueOf(id);

			st1 = conn.prepareStatement(query);

			st1.setString(1, convert);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return esito;
	}

	public boolean updateComponent(int id, int wear, String status) {
		
		SCHEMA = "component";

		PreparedStatement st1;
		int rs1;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET WEAR = ?, STATUS = ? WHERE ID = ?";
			st1 = conn.prepareStatement(query);
			
			String c1 = String.valueOf(wear);
			st1.setString(1, c1);
			
			st1.setString(2, status);
			
			String c2 = String.valueOf(id);
			st1.setString(3, c2);

			rs1 = st1.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;
	}

	public int countElement() {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		int result = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();
			// ATTENZIONE ALL'ACCENTO
			String query = "SELECT NAME, COUNT(*) AS QUANTITA' FROM " + SCHEMA + " WHERE WAREHOUSE = 1 GROUP BY NAME";
			st1 = conn.prepareStatement(query);

			rs1 = st1.executeQuery();

			result = rs1.getInt(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int countElementBySelect(String select) {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		int result = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();
			// ATTENZIONE ALL'ACCENTO
			String query = "SELECT NAME, COUNT(*) AS QUANTITA' FROM " + SCHEMA
					+ " WHERE  NAME = ?  AND WAREHOUSE = 1  GROUP BY NAME";
			st1 = conn.prepareStatement(query);

			st1.setString(1, select);

			rs1 = st1.executeQuery();

			result = rs1.getInt(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int checkRequest(String id_s, int id_c, String id_v) {

		SCHEMA = "request";

		PreparedStatement st1;
		ResultSet rs1;

		int result = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT  COUNT(*) FROM " + SCHEMA + " WHERE ID_STAFF = ? AND ID_COMPONENT = ? AND ID_VEHICLE = ?";
			st1 = conn.prepareStatement(query);

			String idc = String.valueOf(id_c);

			st1.setString(1, id_s);
			st1.setString(2, idc);
			st1.setString(3, id_v);

			rs1 = st1.executeQuery();

			if (rs1.next()) { // mi sposto alla prima riga del risultato
				result = rs1.getInt(1); // Ottengo il valore di COUNT(*)
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int checkCompo(int id) {

		SCHEMA = "component";

		PreparedStatement st1;
		ResultSet rs1;

		int result = 0;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "SELECT  COUNT(*) FROM " + SCHEMA + " WHERE ID = ?";
			st1 = conn.prepareStatement(query);

			String c1 = String.valueOf(id);
			st1.setString(1, c1);

			rs1 = st1.executeQuery();

			if (rs1.next()) { // mi sposto alla prima riga del risultato
				result = rs1.getInt(1); // Ottengo il valore di COUNT(*)
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

}
