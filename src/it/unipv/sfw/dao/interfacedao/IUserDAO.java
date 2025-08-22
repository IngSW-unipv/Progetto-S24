package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.model.staff.Staff;

public interface IUserDAO {
    /** Ritorna lo Staff concreto (Mechanic/Strategist/Warehouseman) o null se non trovato. */
    Staff selectById(String id);

    /** Ritorna il ruolo se le credenziali sono corrette, altrimenti null. */
    it.unipv.sfw.model.staff.Staff.TypeController selectByIDandPwd(String id, String pwd);
}
