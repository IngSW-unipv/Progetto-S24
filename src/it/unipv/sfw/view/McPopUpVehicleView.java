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
 * Vista per l'aggiunta di un veicolo.
 * Permette all'utente di inserire i dati di un nuovo veicolo.
 */
public class McPopUpVehicleView {

    private JFrame frame;

    // contenitori delle 3 sezioni +1 main
    private JPanel dataPanel, sendPanel;

    private JLabel titleLabel, mexLabel;

    // inserimento dati
    private JTextField msn, id_p;

    // bottoni per l'interazione: 1
    private JButton sendButton;

    /**
     * Costruttore della vista.
     */
    public McPopUpVehicleView() {

        frame = new JFrame("ADD VEHICLE");

        frame.setSize(500, 500);
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

        titleLabel = new JLabel("INSERT VEHICLE", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(titleLabel);

        /*
         * CREAZIONE 2 SEZIONE - INSERT DATA
         */

        dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;

        msn = new JTextField("MSN");
        msn.setPreferredSize(new Dimension(150, 30));
        dataPanel.add(msn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        id_p = new JTextField("ID PILOT");
        id_p.setPreferredSize(new Dimension(150, 30));
        dataPanel.add(id_p, gbc);

        frame.add(dataPanel);

        /*
         * CREAZIONE 3 SEZIONE - SEND DATA
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

        gbcSend.gridy = 1; // Sposta il messaggio sotto il bottone
        mexLabel = new JLabel("", SwingConstants.CENTER);
        mexLabel.setForeground(Color.WHITE);
        sendPanel.add(mexLabel, gbcSend);

        frame.add(sendPanel);

        msn.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (msn.getText().equals("MSN")) {
					msn.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (msn.getText().isEmpty()) {
					msn.setText("MSN"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		id_p.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (id_p.getText().equals("ID PILOT")) {
					id_p.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (id_p.getText().isEmpty()) {
					id_p.setText("ID PILOT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});


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
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText(""); // Pulisce il JTextField
            }
        }
        panel.revalidate(); // Rende il pannello nuovamente valido
        panel.repaint(); // Ridisegna il pannello
    }

    /**
     * Mostra la finestra.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Imposta il messaggio.
     * @param message Il messaggio da mostrare.
     */
    public void mex() {
        mexLabel.setText("WRONG INSERTION");
        mexLabel.setForeground(Color.RED);
    }

    /**
     * Chiude la finestra.
     */
    public void close() {
        frame.dispose();
    }

    /**
     * Restituisce il campo MSN.
     * @return Il campo MSN.
     */
    public JTextField getMsn() {
        return msn;
    }

    /**
     * Imposta il campo MSN.
     * @param msn Il campo MSN da impostare.
     */
    public void setMsn(JTextField msn) {
        this.msn = msn;
    }

    /**
     * Restituisce il campo ID pilota.
     * @return Il campo ID pilota.
     */
    public JTextField getId_p() {
        return id_p;
    }

    /**
     * Imposta il campo ID pilota.
     * @param id_p Il campo ID pilota da impostare.
     */
    public void setId_p(JTextField id_p) {
        this.id_p = id_p;
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

}