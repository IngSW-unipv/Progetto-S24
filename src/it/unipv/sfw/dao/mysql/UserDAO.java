package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipv.sfw.dao.interfacedao.IUserDAO;

/**
 * Implementazione MySQL dell'interfaccia {@link IUserDAO}.
 * <p>
 * Fornisce metodi per accedere alla tabella <b>staff</b> del database,
 * permettendo di recuperare i campi di un utente a partire dal suo ID.
 * </p>
 */
public class UserDAO implements IUserDAO {

    private static final String TABLE = "staff";

    /**
     * Recupera i campi dell'utente con l'ID specificato dalla tabella {@code staff}.
     * <p>
     * L'array restituito contiene i valori nelle seguenti posizioni:
     * <ul>
     *   <li>[0] = ID</li>
     *   <li>[1] = PASSWORD</li>
     *   <li>[2] = ROLE</li>
     *   <li>[3] = NAME</li>
     *   <li>[4] = SURNAME</li>
     * </ul>
     * Se l'utente non esiste, viene restituito {@code null}.
     * </p>
     *
     * @param id identificativo dell'utente
     * @return array con i dati dell'utente, oppure {@code null} se non trovato
     */
    @Override
    public String[] selectRowFieldsById(String id) {
        final String sql = "SELECT ID, PASSWORD, ROLE, NAME, SURNAME FROM " + TABLE + " WHERE ID = ?";

        try (DBConnection db = new DBConnection(TABLE);
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String[] row = new String[5];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("PASSWORD");
                row[2] = rs.getString("ROLE");
                row[3] = rs.getString("NAME");
                row[4] = rs.getString("SURNAME");
                return row;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }
}
