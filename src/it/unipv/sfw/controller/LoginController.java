package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.LoginView;

/**
 * Controller che gestisce il processo di login nella {@link LoginView}.
 * Verifica le credenziali e, in base al ruolo dell'utente,
 * carica il controller congruente usando {@link ControllerFactory}.
 */
public class LoginController extends AbsController {
    private LoginView logv;

    /**
     * Esegue il flusso di autenticazione e routing:
     *
     *   Legge username/password dalla view.
     *   Invoca {@link Session#login(String, char[])}.
     *   Ricava il ruolo corrente e lo mappa in un {@link AbsController.TypeController}.
     *   Carica il controller corrispondente nel {@link ControllerManager}.
     * 
     * Gestisce gli errori di credenziali mostrando il messaggio nella view.
     */
    private void accedi() {
        logv = (LoginView) view;

        try {
            // 1) login applicativo
            Session.getIstance().login(getUsername(), getPassword());

            // 2) ruolo corrente come enum
            Staff.TypeRole role = Session.getIstance().getCurrentRole();

            // 3) mapping ruolo → tipo controller
            AbsController.TypeController typeController = ControllerRole.toControllerType(role);

            // 4) carica il controller tramite factory
            ControllerManager cm = ControllerManager.getInstance();
            cm.loadController(typeController);
            cm.closeWindow();

        } catch (WrongPasswordException | AccountNotFoundException err) {
            System.out.println(err);
            logv.upError(); //UI per mostrare l'errore
        }
    }

    @Override
    public TypeController getType() {
        return TypeController.LOGIN;
    }

    @Override
    public void initialize() {
        System.out.println("Inizializzazione di LoginController - @LOGINCONTROLLER");
        LoginView v = new LoginView();

        v.getPasswordField().setFocusTraversalKeysEnabled(false);
        v.getPasswordField().addKeyListener(new KeyListener() {
            @Override 
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) accedi();
            }
            @Override 
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });

        v.getAccediButton().addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) { accedi(); }
        });

        view = v;
        logv = v;
    }

    /** @return username dalla view */
    private String getUsername() {
        return logv.getUsernameField().getText();
    }

    /** @return password dalla view */
    private char[] getPassword() {
        return logv.getPasswordField().getPassword();
    }
}
