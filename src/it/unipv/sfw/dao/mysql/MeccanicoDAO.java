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

/**
 * Data Access Object (DAO) per la gestione di meccanici, piloti, veicoli,
 * componenti e richieste nel database MySQL.  Fornisce metodi per
 * inserire, controllare, aggiornare e rimuovere informazioni relative a
 * queste entità.
 */
public class MeccanicoDAO {

	private String SCHEMA = "";

    /**
     * Verifica se esiste un membro dello staff con l'ID specificato.
     * @param id L'ID del membro dello staff da verificare.
     * @throws WrongIDException Se non viene trovato alcun membro dello staff con l'ID specificato.
     */
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

    /**
     * Verifica se esiste un veicolo con il numero di telaio (MSN) specificato.
     * @param msn Il numero di telaio (MSN) del veicolo da verificare.
     * @throws VehicleNotFoundException Se non viene trovato alcun veicolo con il numero di telaio specificato.
     */
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

    /**
     * Associa un meccanico a un veicolo.
     * @param msn Il numero di telaio (MSN) del veicolo.
     * @param id L'ID del meccanico da associare.
     * @return `true` se l'associazione ha avuto successo, `false` altrimenti.
     */
	public boolean insertMeccOnVehicle(String msn, String id) {

		SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_MECHANIC = ? WHERE MSN = ? ";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id);
			st1.setString(2, msn);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;
	}

    /**
     * Verifica se esiste un pilota con l'ID specificato.
     * @param id_p L'ID del pilota da verificare.
     * @throws PilotNotFoundException Se non viene trovato alcun pilota con l'ID specificato.
     */
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

	   /**
     * Associa un pilota a un veicolo.
     * @param id_p L'ID del pilota da associare.
     * @param msn Il numero di telaio (MSN) del veicolo.
     * @return `true` se l'associazione ha avuto successo, `false` altrimenti.
     */
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

	   /**
     * Rimuove l'associazione tra un pilota e un veicolo.
     * @param idp L'ID del pilota da rimuovere dall'associazione.
     * @return `true` se la rimozione ha avuto successo, `false` altrimenti.
     */
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

    /**
     * Seleziona un pilota dal database.
     * @param id L'ID del pilota.
     * @param name Il nome del pilota.
     * @param surname Il cognome del pilota.
     * @param number Il numero del pilota.
     * @throws PilotNotFoundException Se non viene trovato alcun pilota con i dati specificati.
     */

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

    /**
     * Verifica se un pilota è associato a un veicolo.
     * @param idp L'ID del pilota.
     * @return `true` se il pilota è associato a un veicolo, `false` altrimenti.
     */
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

    /**
     * Verifica se esiste un componente con le informazioni specificate.
     * @param id_c L'ID del componente.
     * @param name Il nome del componente.
     * @param status Lo stato del componente.
     * @throws ComponentNotFoundException Se non viene trovato alcun componente con i dati specificati.
     */
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

    /**
     * Verifica se esiste un componente con l'ID specificato.
     * @param id_c L'ID del componente.
     * @throws ComponentNotFoundException Se non viene trovato alcun componente con l'ID specificato.
     */
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

    /**
     * Associa un componente a un veicolo.
     * @param id L'ID del componente.
     * @param msn Il numero di telaio (MSN) del veicolo.
     * @return `true` se l'associazione ha avuto successo, `false` altrimenti.
     */
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

    /**
     * Aggiorna il valore di usura di un componente.
     * @param wear Il nuovo valore di usura.
     * @param id L'ID del componente.
     * @return `true` se l'aggiornamento ha avuto successo, `false` altrimenti.
     */
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

    /**
     * Rimuove l'associazione tra un componente e un veicolo.
     * @param id_c L'ID del componente.
     * @param id_v L'ID del veicolo.
     */
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

    /**
     * Inserisce una nuova richiesta.
     * @param desc La descrizione della richiesta.
     * @param id_s L'ID dello staff che ha effettuato la richiesta.
     * @param id_c L'ID del componente richiesto.
     * @param id_v L'ID del veicolo a cui si riferisce la richiesta.
     * @return `true` se l'inserimento ha avuto successo, `false` altrimenti.
     */
	public boolean insertRequest(String desc, String id_s, String id_c, String id_v) {

		SCHEMA = "request";

		PreparedStatement st1;
		int rs1 = 0;

		boolean esito = true;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "INSERT INTO " + SCHEMA
					+ " (ID_MECHANIC, ID_COMPONENT, ID_VEHICLE, DESCRIPTION) VALUES(?,?,?,?)";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id_s);

			st1.setString(2, id_c);

			st1.setString(3, id_v);

			st1.setString(4, desc);

			rs1 = st1.executeUpdate();

		} catch (SQLException e) {
			esito = false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return esito;

	}

    /**
     * Verifica se esiste una richiesta con l'ID del componente specificato.
     * @param id_c L'ID del componente.
     * @throws WrongRequestException Se non viene trovata alcuna richiesta con l'ID del componente specificato.
     */
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
	
    /**
     * Inserisce un evento nel log.
     * @param id_staff L'ID dello staff che ha effettuato l'azione.
     * @param desc La descrizione dell'evento.
     */
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