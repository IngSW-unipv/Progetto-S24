package it.unipv.sfw.frame;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import it.unipv.sfw.view.AbsView;

/**
 * Classe che rappresenta la finestra principale dell'applicazione.
 * Gestisce il caricamento e la visualizzazione delle diverse viste.
 */
public class Frame extends JFrame {

    private AbsView currentView;

    /**
     * Costruttore del frame.
     * @param w Larghezza della finestra.
     * @param h Altezza della finestra.
     */
    public Frame(int w, int h) {

        this.currentView = null;
        this.setSize(w, h);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Management Scuderia");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        this.setIconImage(icona.getImage());

        // Aggiunge il tasto "ENTER" come tasto di navigazione in avanti
        Set<AWTKeyStroke> forwardKeys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newForwardKeys = new HashSet<>(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);

    }

    /**
     * Restituisce le dimensioni correnti della finestra.
     * @return Le dimensioni della finestra.
     */
    public Dimension getCurrentSize() {
        return this.getSize();
    }

    /**
     * Carica e visualizza una nuova vista.
     * Se è presente una vista corrente, la rimuove prima di caricare la nuova.
     * @param v La vista da caricare (oggetto di tipo {@link AbsView}).
     */
    public void loadView(AbsView v) {

        System.out.println("loadview" + v);

        if (currentView != null) {
            System.out.println("rimossa view attuale - @FRAME");
            this.remove(currentView);
        }
        System.out.println("aggiunta nuova view - @FRAME");
        this.add(v);
        this.revalidate();
        this.repaint();
        currentView = v;
        System.out.println("il contenuto di currentView: " + v);
    }

}