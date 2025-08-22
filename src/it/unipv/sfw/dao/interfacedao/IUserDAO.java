package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.model.staff.Staff.TypeController;

public interface IUserDAO {
	
	Staff selectById(String id);
	
  TypeController selectByIDandPwd(String id, String pwd);

}
