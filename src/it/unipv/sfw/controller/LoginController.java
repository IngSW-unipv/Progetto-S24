package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.LoginView;

/**
 * Controller che gestisce il processo di login nella {@link LoginView}.
 * Si occupa di verificare le credenziali dell'utente e di caricare il controller appropriato
 * in base al ruolo dell'utente autenticato.
 * 
 * @see AbsController
 * @see it.unipv.sfw.view.LoginView
 */
public class LoginController extends AbsController {
    private LoginView logv;
    
    /**
     * Gestisce il processo di login di un operatore nel sistema.
     * In base al ruolo dell'operatore, carica il controller corrispondente.
     */
    private void accedi() {
        logv = (LoginView) view;

        try {
            Session.getIstance().login(getUsername(), getPassword());
        } catch (WrongPasswordException | AccountNotFoundException err) {
            System.out.print(err);
            logv.upError();
            return;
        }

        // Caricamento del controller in base al tipo di utente
        switch ("" + Session.getIstance().getCurrentUser().getType()) {
            case "MECHANIC":
                ControllerManager.getInstance().loadController(TypeController.MECHANIC);
                ControllerManager.getInstance().closeWindow();
                break;
            case "STRATEGIST":
                ControllerManager.getInstance().loadController(TypeController.STRATEGIST);
                ControllerManager.getInstance().closeWindow();
                break;
            case "WAREHOUSEMAN":
                ControllerManager.getInstance().loadController(TypeController.WAREHOUSEMAN);
                ControllerManager.getInstance().closeWindow();
                break;
        }
    }

    /**
     * Restituisce il tipo di controller.
     * 
     * @return Il tipo di controller, in questo caso {@link TypeController#LOGIN}.
     */
    @Override
    public TypeController getType() {
        return TypeController.LOGIN;
    }

    /**
     * Inizializza il controller creando la vista di login e impostando i listener
     * per la gestione dell'accesso tramite tastiera e pulsante.
     */
    @Override
    public void initialize() {
        System.out.println("Inizializzazione di LoginController - @LOGINCONTROLLER");
        LoginView v = new LoginView();

        v.getPasswordField().setFocusTraversalKeysEnabled(false);
        v.getPasswordField().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    accedi();
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        v.getAccediButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accedi();
            }
        });

        view = v;
    }
    
    /**
     * Restituisce il nome utente inserito nella vista di login.
     * 
     * @return Il nome utente come stringa.
     */
    private String getUsername() {
        return logv.getUsernameField().getText();
    }
    
    /**
     * Restituisce la password inserita nella vista di login.
     * 
     * @return Un array di caratteri contenente la password.
     */
    private char[] getPassword() {
        return logv.getPasswordField().getPassword();
    }
}
