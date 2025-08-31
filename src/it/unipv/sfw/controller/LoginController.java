package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unipv.sfw.dao.mysql.UserDAO;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.facade.AuthResult;
import it.unipv.sfw.facade.LoginFacade;
import it.unipv.sfw.facade.impl.DefaultLoginFacade;
import it.unipv.sfw.model.staff.Staff;
import it.unipv.sfw.view.LoginView;

/**
 * Controller che gestisce il processo di autenticazione nella {@link LoginView}.
 * <p>
 * Si occupa di:
 * <ul>
 *   <li>Leggere username e password dalla view</li>
 *   <li>Invocare la {@link LoginFacade} per l’autenticazione</li>
 *   <li>Determinare il ruolo dell’utente autenticato</li>
 *   <li>Caricare il controller congruente tramite {@link ControllerManager}</li>
 *   <li>Gestire eventuali errori di login, mostrando i messaggi nella view</li>
 * </ul>
 * </p>
 *
 * @see LoginFacade
 * @see ControllerRole
 * @see ControllerManager
 */
public class LoginController extends AbsController {
    private LoginView logv;
    private LoginFacade loginFacade;

    /**
     * Esegue il flusso completo di autenticazione e routing:
     * <ol>
     *   <li>Recupera username e password dalla view</li>
     *   <li>Invoca {@link LoginFacade#authenticate(String, char[])}, che aggiorna anche la sessione</li>
     *   <li>Ottiene il ruolo utente come {@link Staff.TypeRole}</li>
     *   <li>Tramite {@link ControllerRole}, determina il {@link AbsController.TypeController} associato</li>
     *   <li>Carica il controller corrispondente usando {@link ControllerManager}</li>
     *   <li>Gestisce eccezioni di credenziali mostrando l’errore nella view</li>
     * </ol>
     */
    private void accedi() {
        logv = (LoginView) view;

        try {
            // 1) Login tramite Facade
            AuthResult result = loginFacade.authenticate(getUsername(), getPassword());

            // 2) Ruolo corrente
            Staff.TypeRole role = result.getRole();

            // 3) Mapping ruolo -> tipo controller
            AbsController.TypeController typeController = ControllerRole.toControllerType(role);

            // 4) Caricamento controller e chiusura login
            ControllerManager cm = ControllerManager.getInstance();
            cm.loadController(typeController);
            cm.closeWindow();

        } catch (WrongPasswordException | AccountNotFoundException err) {
            System.out.println(err);
            logv.upError(); // UI: mostra messaggio di errore
        }
    }

    /**
     * Restituisce il tipo di controller.
     *
     * @return {@link AbsController.TypeController#LOGIN}
     */
    @Override
    public TypeController getType() {
        return TypeController.LOGIN;
    }

    /**
     * Inizializza il controller e la view associata.
     * <p>
     * Crea la {@link LoginView}, collega gli event listener a tasti e bottoni
     * e imposta la {@link LoginFacade} di default.
     * </p>
     */
    @Override
    public void initialize() {
        System.out.println("Inizializzazione di LoginController - @LOGINCONTROLLER");
        LoginView v = new LoginView();

        // Facade di login predefinita
        this.loginFacade = new DefaultLoginFacade(new UserDAO());

        // Listener: tasto ENTER su campo password
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

        // Listener: click su bottone Accedi
        v.getAccediButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                accedi();
            }
        });

        view = v;
        logv = v;
    }

    /**
     * Recupera lo username inserito dall’utente.
     *
     * @return username testuale dalla {@link LoginView}
     */
    private String getUsername() { 
        return logv.getUsernameField().getText(); 
    }

    /**
     * Recupera la password inserita dall’utente.
     *
     * @return password come array di {@code char[]} dalla {@link LoginView}
     */
    private char[] getPassword() { 
        return logv.getPasswordField().getPassword(); 
    }
}
