package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipv.sfw.dao.interfacedao.IUserDAO;
import it.unipv.sfw.model.staff.Mechanic;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Strategist;
import it.unipv.sfw.model.staff.Warehouseman;
import it.unipv.sfw.model.staff.Staff.TypeController;

public class UserDAO implements IUserDAO {
    private static final String SCHEMA = "staff";

    @Override
    public Staff selectById(String id) {
        final String sql = "SELECT ID, PASSWORD, ROLE, NAME, SURNAME FROM " + SCHEMA + " WHERE ID = ?";

        try (DBConnection db = new DBConnection(SCHEMA);
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String userId = rs.getString("ID");
                String pwd    = rs.getString("PASSWORD");
                String role   = rs.getString("ROLE"); 

                return switch (role) {
                    case "Mechanic"     -> new Mechanic(userId, pwd);
                    case "Strategist"   -> new Strategist(userId, pwd);
                    case "Warehouseman" -> new Warehouseman(userId, pwd);
                    default -> null; 
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TypeController selectByIDandPwd(String id, String pwd) {
        final String sql = "SELECT ROLE, PASSWORD FROM " + SCHEMA + " WHERE ID = ?";

        try (DBConnection db = new DBConnection(SCHEMA);
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String roleDb   = rs.getString("ROLE");
                String storedPw = rs.getString("PASSWORD");

                if (storedPw == null || !storedPw.equals(pwd)) return null;

                return switch (roleDb) {
                    case "Mechanic"     -> TypeController.MECHANIC;
                    case "Strategist"   -> TypeController.STRATEGIST;
                    case "Warehouseman" -> TypeController.WAREHOUSEMAN;
                    default -> null;
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
