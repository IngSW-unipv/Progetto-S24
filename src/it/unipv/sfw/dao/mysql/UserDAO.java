package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipv.sfw.dao.interfacedao.IUserDAO;

public class UserDAO implements IUserDAO {
    private static final String TABLE = "staff";

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
