package it.unipv.sfw.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import it.unipv.sfw.model.staff.Magazziniere;
import it.unipv.sfw.model.staff.Meccanico;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Stratega;
import it.unipv.sfw.model.staff.User;
import it.unipv.sfw.model.staff.User.Type;

public class UserDAO {
	private static final String SCHEMA = "staff";

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
				String type = rs1.getString(5);

				switch (type) {
				case "Meccanico":

					result = new Meccanico(rs1.getString(2), rs1.getString(3), rs1.getString(1), rs1.getString(4));

					break;

				case "Stratega":

					result = new Stratega(rs1.getString(2), rs1.getString(3), rs1.getString(1), rs1.getString(4));

					break;

				case "Magazziniere":

					result = new Magazziniere(rs1.getString(2), rs1.getString(3), rs1.getString(1), rs1.getString(4));

					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Type selectByIDandPwd(String id, String pwd) {

		Type typeSelected = null;

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
				String type = rs1.getString(5);

				switch (type) {
				case "Meccanico":

					typeSelected = Type.Meccanico;

					break;

				case "Stratega":

					typeSelected = Type.Stratega;

					break;

				case "Magazziniere":

					typeSelected = Type.Magazziniere;

					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeSelected;
	}
}
