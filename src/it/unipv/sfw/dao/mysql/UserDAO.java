package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Strategist;
import it.unipv.sfw.model.staff.Staff.TypeController;

/**
 * Data Access Object (DAO) per la gestione degli utenti (staff) nel database MySQL.
 * Fornisce metodi per recuperare informazioni sull'utente tramite ID e per
 * verificare le credenziali (ID e password) e ottenere il ruolo.
 */
public class UserDAO {
    private static final String SCHEMA = "staff";

    /**
     * Recupera un utente (membro dello staff) dal database tramite il suo ID.
     *
     * @param id L'ID dell'utente da recuperare.
     * @return Un oggetto {@link Staff} che rappresenta l'utente, o `null` se
     *         l'utente non viene trovato. Il tipo specifico dell'oggetto Staff
     *         dipende dal ruolo dell'utente (Meccanico, Stratega, Magazziniere).
     *         Vengono anche impostati nome e cognome dell'utente nella sessione.
     */
    public Staff selectById(String id) {

        Staff result = null;

        PreparedStatement st1;
        ResultSet rs1;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "SELECT * FROM " + SCHEMA + " WHERE ID = ?";
            st1 = conn.prepareStatement(query);
            st1.setString(1, id);
            rs1 = st1.executeQuery();

            if (rs1.next()) {
                String type = rs1.getString(3);

                switch (type) {
                    case "Mechanic":
                        result = new Mechanic(rs1.getString(1), rs1.getString(2));
                        Session.getIstance().setName(rs1.getString(4));
                        Session.getIstance().setSurname(rs1.getString(5));
                        break;

                    case "Strategist":
                        result = new Strategist(rs1.getString(1), rs1.getString(2));
                        Session.getIstance().setName(rs1.getString(4));
                        Session.getIstance().setSurname(rs1.getString(5));
                        break;

                    case "Warehouseman":
                        result = new Warehouseman(rs1.getString(1), rs1.getString(2));
                        Session.getIstance().setName(rs1.getString(4));
                        Session.getIstance().setSurname(rs1.getString(5));
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Verifica le credenziali di un utente (ID e password) e restituisce il suo ruolo.
     *
     * @param id  L'ID dell'utente.
     * @param pwd La password dell'utente.
     * @return Il ruolo dell'utente ({@link TypeController}), o `null` se le
     *         credenziali non sono corrette.
     */
    public static TypeController selectByIDandPwd(String id, String pwd) {

        TypeController typeSelected = null;

        PreparedStatement st1;
        ResultSet rs1;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "SELECT ROLE FROM " + SCHEMA + " WHERE ID = ? AND PASSWORD LIKE ?";
            st1 = conn.prepareStatement(query);
            st1.setString(1, id);
            st1.setString(2, pwd);
            rs1 = st1.executeQuery();
            
            if (rs1.next()) {
                String type = rs1.getString(3);

                switch (type) {
                    case "Meccanico":
                        typeSelected = TypeController.MECHANIC;
                        break;

                    case "Stratega":
                        typeSelected = TypeController.STRATEGIST;
                        break;

                    case "Magazziniere":
                        typeSelected = TypeController.WAREHOUSEMAN;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeSelected;
    }
}