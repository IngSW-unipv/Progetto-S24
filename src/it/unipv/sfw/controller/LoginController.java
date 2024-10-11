package it.unipv.sfw.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.User.Type;
import it.unipv.sfw.view.LoginView;

/**
 * Controller che si occupa della LoginView.
 *
 * @see AbsController
 * @see it.unipv.sfw.view.LoginView
 */

public class LoginController extends AbsController{
	
	private void accedi() {
		LoginView v = (LoginView) view;
		
		// Try to login into session
		//USERNAME = ID DELL'OPERATORE

		try {
			Session.getIstance().login(v.getUsernameField().getText().toLowerCase(),
					v.getPasswordField().getPassword());
			
		} catch (WrongPasswordException | AccountNotFoundException err) {
			v.upError();
			return;
		}
		
		//SWITCH LOAD CONTROLLER
		switch(""+Session.getIstance().getCurrentUser().getType()) {
		
		case "MECCANICO":
			ControllerManager.getInstance().loadController(TypeController.MECCANICO);
			
			break;
		
		case "STRATEGA":
			ControllerManager.getInstance().loadController(TypeController.STRATEGA);
			
			break;
		
		case "MAGAZZINIERE":
			ControllerManager.getInstance().loadController(TypeController.MAGAZZINIERE);
			
			break;
		
		}
		
	}

	@Override
	public TypeController getType() {
		
		return TypeController.LOGIN;
	}

	@Override
	public void initialize() {
		LoginView v = new LoginView();

		v.getPasswordField().setFocusTraversalKeysEnabled(false);
		v.getPasswordField().addKeyListener(new KeyListener() {
			
		@Override
		public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					accedi();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
	
		});

		v.getAccediButton().addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			accedi();
		}
		
		});
		
		view = v;
	}

}
