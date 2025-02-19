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
 * Vista per l'aggiunta di un componente.
 * Permette all'utente di inserire i dati di un nuovo componente.
 */
public class McPopUpComponentView {

    private JFrame frame;

    // contenitori delle 3 sezioni +1 main
    private JPanel dataPanel, sendPanel;

    private JLabel titleLabel, mexLabel;

    // inserimento dati
    private JTextField idC, nameC, statusC;

    // bottoni per l'interazione: 1
    private JButton sendButton;

    /**
     * Costruttore della vista.
     */
    public McPopUpComponentView() {

        frame = new JFrame("ADD COMPONENT");

        frame.setSize(600, 600);
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

        titleLabel = new JLabel("INSERT COMPONENT", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        frame.add(titleLabel);

        /*
         * CREAZIONE 2 SEZIONE
         */

        dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        dataPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        Dimension dim = new Dimension(150, 30);

        // Prima riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 0;
        idC = new JTextField("ID COMPONENT");
        idC.setPreferredSize(dim);
        dataPanel.add(idC, gbc);

        // Seconda riga, prima colonna
        gbc.gridx = 1;
        gbc.gridy = 0;
        nameC = new JTextField("NAME COMPONENT");
        nameC.setPreferredSize(dim);
        dataPanel.add(nameC, gbc);

        // Seconda riga, seconda colonna
        gbc.gridx = 2;
        gbc.gridy = 0;
        statusC = new JTextField("STATUS COMPONENT");
        statusC.setPreferredSize(dim);
        dataPanel.add(statusC, gbc);

        // Aggiungi il pannello dati al frame
        frame.add(dataPanel);
        frame.repaint();
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

        gbcSend.gridy = 1; // Sposta il messaggio sotto il bottone
        mexLabel = new JLabel("", SwingConstants.CENTER);
        mexLabel.setForeground(Color.WHITE);
        sendPanel.add(mexLabel, gbcSend);

        frame.add(sendPanel, BorderLayout.CENTER);

        idC.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (idC.getText().equals("ID COMPONENT")) {
					idC.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (idC.getText().isEmpty()) {
					idC.setText("ID COMPONENT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		nameC.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (nameC.getText().equals("NAME COMPONENT")) {
					nameC.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (nameC.getText().isEmpty()) {
					nameC.setText("NAME COMPONENT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		statusC.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (statusC.getText().equals("STATUS COMPONENT")) {
					statusC.setText(""); // Rimuove il testo predefinito
				}
				clearComponents(sendPanel);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (statusC.getText().isEmpty()) {
					statusC.setText("STATUS COMPONENT"); // Ripristina il testo predefinito se vuoto
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
     * Mostra la finestra.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Nasconde la finestra.
     */
    public void hide() {
        frame.setVisible(false);
        frame.setEnabled(false);
    }

    /**
     * Nasconde il campo status.
     */
    public void hideField() {
        statusC.setEnabled(false);
        statusC.setVisible(false);

    }

    /**
     * Imposta il messaggio di errore.
     */
    public void mex() {
        mexLabel.setText("INVALID ERROR");
        mexLabel.setForeground(Color.RED);
    }

    /**
     * Imposta il messaggio di componente già inserito.
     */
    public void mex1() {
        mexLabel.setText("COMPONENT ALREADY INSERTED");
        mexLabel.setForeground(Color.RED);
    }

    /**
     * Imposta il messaggio di componente inserito con successo.
     */
    public void mex2() {
        mexLabel.setText("COMPONENT SUCCESSFULLY  INSERTED");
        mexLabel.setForeground(Color.GREEN);
    }

    /**
     * Imposta il messaggio di componente rimosso con successo.
     */
    public void mex3() {
        mexLabel.setText("COMPONENT SUCCESSFULLY  REMOVED");
        mexLabel.setForeground(Color.GREEN);
    }

    /**
     * Restituisce il campo ID componente.
     * @return Il campo ID componente.
     */
    public JTextField getIdC() {
        return idC;
    }

    /**
     * Imposta il campo ID componente.
     * @param insertID_C Il campo ID componente da impostare.
     */
    public void setIdC(JTextField insertID_C) {
        this.idC = insertID_C;
    }

    /**
     * Restituisce il campo nome componente.
     * @return Il campo nome componente.
     */
    public JTextField getNameC() {
        return nameC;
    }

    /**
     * Imposta il campo nome componente.
     * @param nameC Il campo nome componente da impostare.
     */
    public void setNameC(JTextField nameC) {
        this.nameC = nameC;
    }

    /**
     * Restituisce il campo status componente.
     * @return Il campo status componente.
     */
    public JTextField getStatusC() {
		return statusC;
	}
    
    /**
     * Imposta il campo status componente.
     * @return Il campo status componente.
     */
	public void setStatusC(JTextField statusC) {
		this.statusC = statusC;
	}
	
    /**
     * Restituisce il bottone SendButton.
     * @return Il bottone SendButton.
     */
	public JButton getSendButton() {
		return sendButton;
	}
	
    /**
     * Imposta il bottone SendButton.
     * @return Il bottone SendButton.
     */
	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}
	
}