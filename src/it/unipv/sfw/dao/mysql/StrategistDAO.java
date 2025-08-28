package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.sfw.dao.interfacedao.IStrategistDAO;
import it.unipv.sfw.exceptions.VehicleNotFoundException;

/**
 * Data Access Object (DAO) per la gestione delle operazioni relative allo stratega.
 * Questa classe fornisce metodi per l'inserimento di eventi di log,
 * la verifica dell'esistenza di un veicolo tramite MSN e
 * l'assegnazione di uno stratega a un veicolo.
 */
public class StrategistDAO implements IStrategistDAO{

    private static String SCHEMA = "";

    /**
     * Inserisce un nuovo evento nel log.
     *
     * @param id_staff L'ID dello staff che ha causato l'evento.
     * @param desc     La descrizione dell'evento.
     */
    @Override
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
    
    /**
     * Verifica se esiste un veicolo con il numero di telaio (MSN) specificato.
     *
     * @param msn Il numero di telaio (MSN) del veicolo da verificare.
     * @throws VehicleNotFoundException Se non viene trovato alcun veicolo con il numero di telaio specificato.
     */
    @Override
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
				// Accedo ai dati solo dopo rs.next()
				throw new VehicleNotFoundException(msn);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Errore di connessione al database", e);
		}
	}
    
    /**
     * Assegna uno stratega a un veicolo specificato tramite MSN.
     *
     * @param msn Il numero di telaio (MSN) del veicolo.
     * @param id  L'ID dello stratega da assegnare.
     */
    @Override
    public void insertStrategistOnVehicle(String msn, String id){
    	
    	SCHEMA = "vehicle";

		PreparedStatement st1;
		int rs1;

		try (DBConnection db = new DBConnection(SCHEMA)) {
			Connection conn = db.getConnection();

			String query = "UPDATE " + SCHEMA + " SET ID_STRATEGIST = ? WHERE MSN = ?";
			st1 = conn.prepareStatement(query);

			st1.setString(1, id);
			st1.setString(2, msn);

			rs1 = st1.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
