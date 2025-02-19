package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Data Access Object (DAO) per la gestione dei veicoli nel database MySQL.
 * Contiene metodi per interagire con la tabella "vehicle", in particolare
 * per aggiornare i tempi di settore di un veicolo.
 */
public class VehicleDAO {

    private static final String SCHEMA = "vehicle";

    /**
     * Aggiorna i tempi di settore di un veicolo nel database.
     *
     * @param v L'oggetto {@link Vehicle} contenente i nuovi tempi di settore.
     * @return `true` se l'aggiornamento ha avuto successo, `false` in caso di errore.
     *         In particolare, restituisce `false` se viene violato un vincolo di integrità
     *         (ad esempio, se si tenta di inserire un valore non valido).
     */
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

            rs1 = st1.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            esito = false; // Gestione specifica della violazione di vincoli
        } catch (Exception e) {
            e.printStackTrace();
        }

        return esito;

    }

}