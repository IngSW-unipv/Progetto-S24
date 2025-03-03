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
 * Vista per l'aggiunta di un pilota.
 * Permette all'utente di inserire i dati di un nuovo pilota.
 */
public class McPopUpPilotView {

    private JFrame frame;

    // contenitori delle 3 sezioni +1 main
    private JPanel dataPanel, sendPanel;

    private JLabel titleLabel, mexLabel;

    // inserimento dati
    private JTextField id, name, surname, number;

    // bottoni per l'interazione: 1
    private JButton sendButton;

    /**
     * Costruttore della vista.
     */
    public McPopUpPilotView() {

        frame = new JFrame("ADD PILOT");

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
         * CREAZIONE 1 SEZIONE
         */

        titleLabel = new JLabel("DATA PILOT", SwingConstants.CENTER);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(titleLabel);

        /*
         * CREAZIONE 2 SEZIONE
         */

        dataPanel = new JPanel();
        dataPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        Dimension dim = new Dimension(110, 30);

        // Prima riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 0;
        id = new JTextField("ID");
        id.setPreferredSize(dim);
        dataPanel.add(id, gbc);

        // Prima riga, seconda colonna
        gbc.gridx = 1;
        gbc.gridy = 0;
        name = new JTextField("NAME");
        name.setPreferredSize(dim);
        dataPanel.add(name, gbc);

        // Prima riga, terza colonna
        gbc.gridx = 2;
        gbc.gridy = 0;
        surname = new JTextField("SURNAME");
        surname.setPreferredSize(dim);
        dataPanel.add(surname, gbc);

        // Prima riga, quarta colonna
        gbc.gridx = 3;
        gbc.gridy = 0;
        number = new JTextField("NUMBER");
        number.setPreferredSize(dim);
        dataPanel.add(number, gbc);

        frame.add(dataPanel);

        /*
         * CREAZIONE 3 SEZIONE
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

        frame.add(sendPanel, BorderLayout.CENTER);

        id.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (id.getText().equals("ID")) {
					id.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (id.getText().isEmpty()) {
					id.setText("ID"); // Ripristina il testo predefinito se vuoto
				}
			}
		});
		
		name.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (name.getText().equals("NAME")) {
					name.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (name.getText().isEmpty()) {
					name.setText("NAME"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		surname.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (surname.getText().equals("SURNAME")) {
					surname.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (surname.getText().isEmpty()) {
					surname.setText("SURNAME"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		number.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (number.getText().equals("NUMBER")) {
					number.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (number.getText().isEmpty()) {
					number.setText("NUMBER"); // Ripristina il testo predefinito se vuoto
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
     * Restituisce il campo nome.
     * @return Il campo nome.
     */
    public JTextField getName() {
        return name;
    }

    /**
     * Imposta il messaggio di errore.
     */
    public void mex() {
        mexLabel.setText("ERROR");
        mexLabel.setForeground(Color.RED);
    }

    /**
     * Chiude la finestra.
     */
    public void close() {
        frame.dispose();
    }

    /**
     * Pulisce i campi di testo e le etichette in un JPanel.
     * @param panel Il pannello da pulire.
     */
    public void clearComponents(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText(""); // Pulisce il JTextField
            } else if (comp instanceof JLabel) {
                ((JLabel) comp).setText("");
            }
        }
        panel.revalidate(); // Rende il pannello nuovamente valido
        panel.repaint(); // Ridisegna il pannello
    }

    /**
     * Restituisce il campo ID.
     * @return Il campo ID.
     */
    public JTextField getId() {
        return id;
    }

    /**
     * Imposta il campo ID.
     * @param id Il campo ID da impostare.
     */
    public void setId(JTextField id) {
        this.id = id;
    }

    /**
     * Imposta il campo nome.
     * @param name Il campo nome da impostare.
     */
    public void setName(JTextField name) {
        this.name = name;
    }

    /**
     * Restituisce il campo cognome.
     * @return Il campo cognome.
     */
    public JTextField getSurname() {
        return surname;
    }

    /**
     * Imposta il campo cognome.
     * @param surname Il campo cognome da impostare.
     */
    public void setSurname(JTextField surname) {
        this.surname = surname;
    }

    /**
     * Restituisce il campo numero.
     * @return Il campo numero.
     */
    public JTextField getNumber() {
        return number;
    }

    /**
     * Imposta il campo numero.
     * @param number Il campo numero da impostare.
     */
    public void setNumber(JTextField number) {
        this.number = number;
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
     * @return Il pannello di invio.
     */
	public void setSendPanel(JPanel sendPanel) {
		this.sendPanel = sendPanel;
	}

}
