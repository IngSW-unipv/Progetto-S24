package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Vista per l'eliminazione di una richiesta.
 * Permette all'utente di inserire gli ID di staff, componente e veicolo per eliminare una richiesta.
 */
public class WhPopUpDeleteRequestView {

    private JFrame frame;

    // contenitori delle 3 sezioni +1 main
    private JPanel dataPanel, sendPanel;

    private JLabel titleLabel, mexLabel;

    // inserimento dati
    private JTextField id_s, id_c, id_v;

    // bottoni per l'interazione: 1
    private JButton sendButton;

    /**
     * Costruttore della vista.
     */
    public WhPopUpDeleteRequestView() {

        frame = new JFrame("DELETE REQUEST");

        frame.setSize(450, 405);
        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
        frame.setIconImage(icona.getImage());
        frame.setLayout(new GridLayout(3, 1));

        /*
         * CREAZIONE 1 SEZIONE - TITOLO
         */

        titleLabel = new JLabel("DELETE REQUEST", SwingConstants.CENTER);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(titleLabel);

        /*
         * CREAZIONE 2 SEZIONE - INSERIMENTO DATI
         */

        dataPanel = new JPanel();
        dataPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        Dimension dim = new Dimension(130, 30);
        // Prima riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 0;

        id_s = new JTextField("ID STAFF");
        id_s.setPreferredSize(dim);
        dataPanel.add(id_s, gbc);

        // Prima riga, seconda colonna
        gbc.gridx = 1;
        gbc.gridy = 0;

        id_c = new JTextField("ID COMPONENT");
        id_c.setPreferredSize(dim);
        dataPanel.add(id_c, gbc);

        // Prima riga, terza colonna
        gbc.gridx = 2;
        gbc.gridy = 0;

        id_v = new JTextField("ID VEHICLE");
        id_v.setPreferredSize(dim);
        dataPanel.add(id_v, gbc);

        frame.add(dataPanel);

        /*
         * CREAZIONE 3 SEZIONE - BOTTONE E MESSAGGIO
         */

        sendPanel = new JPanel();
        sendPanel.setLayout(new GridBagLayout());
        sendPanel.setBackground(Color.BLACK);

        GridBagConstraints gbcSend = new GridBagConstraints();
        gbcSend.insets = new Insets(10, 0, 10, 0); // Margini per centrare il bottone e il messaggio

        gbcSend.gridx = 0;
        gbcSend.gridy = 0;

        sendButton = new JButton("SEND");
        sendButton.setPreferredSize(new Dimension(150, 30));
        sendPanel.add(sendButton, gbcSend);

        mexLabel = new JLabel();

        gbcSend.gridy = 1; // Sposta il messaggio sotto il bottone
        mexLabel = new JLabel("", SwingConstants.CENTER);
        mexLabel.setForeground(Color.WHITE);
        sendPanel.add(mexLabel, gbcSend);

        frame.add(sendPanel);

        id_s.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (id_s.getText().equals("ID STAFF")) {
					id_s.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (id_s.getText().isEmpty()) {
					id_s.setText("ID STAFF"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		id_c.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (id_c.getText().equals("ID COMPONENT")) {
					id_c.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (id_c.getText().isEmpty()) {
					id_c.setText("ID COMPONENT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		id_v.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (id_v.getText().equals("ID VEHICLE")) {
					id_v.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (id_v.getText().isEmpty()) {
					id_v.setText("ID VEHICLE"); // Ripristina il testo predefinito se vuoto
				}
			}
		});
    }

    /**
     * Mostra la finestra.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Imposta il messaggio di errore.
     */
    public void mex1() {
        mexLabel.setText("ERROR");
        mexLabel.setForeground(Color.RED);
    }

    /**
     * Imposta il messaggio di successo.
     */
    public void mex2() {
        mexLabel.setText("SUCCESS");
        mexLabel.setForeground(Color.GREEN);
    }

    /**
     * Restituisce il campo ID staff.
     * @return Il campo ID staff.
     */
    public JTextField getId_s() {
        return id_s;
    }

    /**
     * Imposta il campo ID staff.
     * @param id_s Il campo ID staff da impostare.
     */
    public void setId_s(JTextField id_s) {
        this.id_s = id_s;
    }

    /**
     * Restituisce il campo ID componente.
     * @return Il campo ID componente.
     */
    public JTextField getId_c() {
        return id_c;
    }

    /**
     * Imposta il campo ID componente.
     * @param id_c Il campo ID componente da impostare.
     */
    public void setId_c(JTextField id_c) {
        this.id_c = id_c;
    }

    /**
     * Restituisce il campo ID veicolo.
     * @return Il campo ID veicolo.
     */
    public JTextField getId_v() {
        return id_v;
    }

    /**
     * Imposta il campo ID veicolo.
     * @param id_v Il campo ID veicolo da impostare.
     */
    public void setId_v(JTextField id_v) {
        this.id_v = id_v;
    }

    /**
     * Restituisce il pulsante di invio.
     * @return Il pulsante di invio.
     */
    public JButton getSendButton() {
        return sendButton;
    }

    /**
     * Imposta il pulsante di invio.
     * @param sendButton Il pulsante di invio da impostare.
     */
    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    /**
     * Restituisce il pannello dati.
     * @return Il pannello dati.
     */
    public JPanel getDataPanel() {
        return dataPanel;
    }

    /**
     * Imposta il pannello dati.
     * @param dataPanel Il pannello dati da impostare.
     */
    public void setDataPanel(JPanel dataPanel) {
        this.dataPanel = dataPanel;
    }

    /**
     * Restituisce il pannello di invio.
     * @return Il pannello di invio.
     */
    public JPanel getSendPanel() {
        return sendPanel;
    }

    /**
     * Imposta il pannello di invio.
     * @param sendPanel Il pannello di invio da impostare.
     */
    public void setSendPanel(JPanel sendPanel) {
        this.sendPanel = sendPanel;
    }

    /**
     * Pulisce i campi di testo e le etichette in un JPanel.
     * @param panel Il pannello da pulire.
     */
	public void clearComponents(JPanel panel) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JTextField) {
				((JTextField) comp).setText(""); // Pulisce il JTextField
			}else if (comp instanceof JLabel) {
				((JLabel) comp).setText("");
			}
		}
		panel.revalidate(); // Rende il pannello nuovamente valido
		panel.repaint(); // Ridisegna il pannello
	}

}
