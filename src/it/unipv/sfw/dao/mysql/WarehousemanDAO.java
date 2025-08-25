package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.dao.interfacedao.IWarehousemanDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.model.request.Request;

/**
 * Data Access Object (DAO) per la gestione delle richieste e dei componenti
 * nel database MySQL.  Si occupa di recuperare, rimuovere e aggiornare
 * informazioni relative a richieste e componenti, oltre che di registrare
 * eventi nel log.
 */
public class WarehousemanDAO implements IWarehousemanDAO{

    private String SCHEMA = "";

    /**
     * Recupera tutte le richieste dal database.
     *
     * @return Un insieme (Set) di oggetti {@link Request} che rappresenta
     *         tutte le richieste presenti nel database.
     */
    @Override
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

                String id = rs1.getString(1), id_component = rs1.getString(3);

                int n1, n2;

                n1 = Integer.parseInt(id);
                n2 = Integer.parseInt(id_component);

                Request r = new Request(n1, rs1.getString(5), rs1.getString(2), n2, rs1.getString(4));
                result.add(r);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    /**
     * Rimuove una richiesta dal database, dato l'ID del componente.
     *
     * @param idc L'ID del componente la cui richiesta deve essere rimossa.
     */
    @Override
    public void removeRequest(String idc) {

        SCHEMA = "request";

        PreparedStatement st1;
        int rs1;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "DELETE FROM " + SCHEMA + " WHERE ID_COMPONENT = ?";

            st1 = conn.prepareStatement(query);

            st1.setString(1, idc);

            rs1 = st1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    /**
     * Aggiorna le informazioni di un componente nel database.
     *
     * @param id     L'ID del componente da aggiornare.
     * @param wear   Il nuovo valore di usura del componente.
     * @param status Il nuovo stato del componente.
     * @return `true` se l'aggiornamento ha avuto successo, `false` altrimenti.
     */
    @Override
    public boolean updateComponent(String id, String wear, String status) {

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

        } catch (SQLException e) {
            esito = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return esito;
    }

    /**
     * Conta il numero di elementi (componenti) presenti nel database.
     *
     * @return Il numero di componenti presenti nel database.
     */
    @Override
    public int countElement() {

        SCHEMA = "component";

        PreparedStatement st1;
        ResultSet rs1;

        int result = 0;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM " + SCHEMA + " WHERE WAREHOUSE = 1";
            st1 = conn.prepareStatement(query);

            rs1 = st1.executeQuery();

            if (rs1.next()) {
                result = rs1.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Conta il numero di elementi (componenti) nel database con un dato nome.
     *
     * @param select Il nome del componente da cercare.
     * @return Il numero di componenti con il nome specificato.
     */
    @Override
    public int countElementBySelect(String select) {

        SCHEMA = "component";

        PreparedStatement st1;
        ResultSet rs1;

        int result = 0;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();
            String query = "SELECT COUNT(*) FROM " + SCHEMA + " WHERE NAME = ? AND WAREHOUSE = 1 GROUP BY NAME";
            st1 = conn.prepareStatement(query);

            st1.setString(1, select);

            rs1 = st1.executeQuery();

            if (rs1.next()) {
                result = rs1.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Verifica se esiste una richiesta con i dati specificati.
     *
     * @param id_s L'ID dello staff che ha effettuato la richiesta.
     * @param id_c L'ID del componente richiesto.
     * @param id_v L'ID del veicolo a cui si riferisce la richiesta.
     * @throws RequestNotFoundException Se non viene trovata alcuna richiesta
     *                                   corrispondente ai dati forniti.
     */
    @Override
    public void checkRequest(String id_s, String id_c, String id_v) throws RequestNotFoundException {

        SCHEMA = "request";

        PreparedStatement st1;
        ResultSet rs1;

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "SELECT * FROM " + SCHEMA
                    + " WHERE ID_MECHANIC = ? AND ID_COMPONENT = ? AND ID_VEHICLE = ?";
            st1 = conn.prepareStatement(query);

            st1.setString(1, id_s);
            st1.setString(2, id_c);
            st1.setString(3, id_v);

            rs1 = st1.executeQuery();

            if (!rs1.next()) {
                throw new RequestNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se esiste un componente con l'ID specificato.
     *
     * @param id_c L'ID del componente da verificare.
     * @throws ComponentNotFoundException Se non viene trovato alcun componente
     *                                     con l'ID specificato.
     */
    @Override
    public void checkCompo(String id_c) throws ComponentNotFoundException {

        SCHEMA = "component";

        PreparedStatement st1;
        ResultSet rs1;

        String idc = String.valueOf(id_c);

        try (DBConnection db = new DBConnection(SCHEMA)) {
            Connection conn = db.getConnection();

            String query = "SELECT * FROM " + SCHEMA + " WHERE ID = ? ";
            st1 = conn.prepareStatement(query);

            st1.setString(1, idc);

            rs1 = st1.executeQuery();

            if (!rs1.next()) {
                throw new ComponentNotFoundException(idc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce un nuovo evento nel log.
     *
     * @param id_staff L'ID dello staff che ha causato l'evento.
     * @param desc     La descrizione dell'evento.
     */
    @Override
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