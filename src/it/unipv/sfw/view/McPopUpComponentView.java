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

public class McPopUpComponentView extends JFrame {

	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel dataPanel, sendPanel;

	private JLabel titleLabel, mexLabel;

	// inserimento dati
	private JTextField insertID_C, insertID_V, nameC, statusC;

	// bottoni per l'interazione: 1
	private JButton sendButton;

	public McPopUpComponentView() {

		frame = new JFrame("ADD COMPONENT");

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
		insertID_C = new JTextField("ID COMPONENT");
		insertID_C.setPreferredSize(dim);
		dataPanel.add(insertID_C, gbc);

		// Prima riga, seconda colonna
		gbc.gridx = 1;
		gbc.gridy = 0;
		insertID_V = new JTextField("ID VEHICLE");
		insertID_V.setPreferredSize(dim);
		dataPanel.add(insertID_V, gbc);

		// Seconda riga, prima colonna
		gbc.gridx = 0;
		gbc.gridy = 1;
		nameC = new JTextField("NAME COMPONENT");
		nameC.setPreferredSize(dim);
		dataPanel.add(nameC, gbc);

		// Seconda riga, seconda colonna
		gbc.gridx = 1;
		gbc.gridy = 1;
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

		insertID_C.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (insertID_C.getText().equals("ID COMPONENT")) {
					insertID_C.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (insertID_C.getText().isEmpty()) {
					insertID_C.setText("ID COMPONENT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		insertID_V.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (insertID_V.getText().equals("ID VEHICLE")) {
					insertID_V.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (insertID_V.getText().isEmpty()) {
					insertID_V.setText("ID VEHICLE"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		nameC.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (nameC.getText().equals("NAME COMPONENT")) {
					nameC.setText(""); // Rimuove il testo predefinito
				}
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
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (statusC.getText().isEmpty()) {
					statusC.setText("STATUS COMPONENT"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

	}

	public JPanel getDataPanel() {
		return dataPanel;
	}

	public void setDataPanel(JPanel dataPanel) {
		this.dataPanel = dataPanel;
	}

	public JPanel getSendPanel() {
		return sendPanel;
	}

	public void setSendPanel(JPanel sendPanel) {
		this.sendPanel = sendPanel;
	}

	// Metodo per ripulire i JTextField e JLabel in un JPanel
	public void clearComponents(JPanel panel) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JTextField) {
				((JTextField) comp).setText(""); // Pulisce il JTextField
			}
		}
		panel.revalidate(); // Rende il pannello nuovamente valido
		panel.repaint(); // Ridisegna il pannello
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}

	public void hideField() {
		statusC.setEnabled(false);
		statusC.setVisible(false);

	}

	public void mex() {
		mexLabel.setText("INVALID ERROR");
		mexLabel.setForeground(Color.RED);
	}

	public void mex1() {
		mexLabel.setText("COMPONENT ALREADY INSERTED");
		mexLabel.setForeground(Color.RED);
	}

	public void mex2() {
		mexLabel.setText("COMPONENT SUCCESSFULLY  INSERTED");
		mexLabel.setForeground(Color.GREEN);
	}

	public JTextField getInsertID_C() {
		return insertID_C;
	}

	public void setInsertID_C(JTextField insertID_C) {
		this.insertID_C = insertID_C;
	}

	public JTextField getInsertID_V() {
		return insertID_V;
	}

	public void setInsertID_V(JTextField insertID_V) {
		this.insertID_V = insertID_V;
	}

	public JTextField getNameC() {
		return nameC;
	}

	public void setNameC(JTextField nameC) {
		this.nameC = nameC;
	}

	public JTextField getStatusC() {
		return statusC;
	}

	public void setStatusC(JTextField statusC) {
		this.statusC = statusC;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

}
