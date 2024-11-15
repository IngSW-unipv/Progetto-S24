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

public class WhPopUpUpdateComponentView {

	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel dataPanel, sendPanel;

	private JLabel titleLabel, mexLabel;

	// inserimento dati
	private JTextField id_c, wear, status;

	// bottoni per l'interazione: 1
	private JButton sendButton;

	public WhPopUpUpdateComponentView() {

		frame = new JFrame("UPDATE REQUEST");

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
		 * CREAZIONE 1 SEZIONE
		 */

		titleLabel = new JLabel("UPDATE REQUEST", SwingConstants.CENTER);

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

		Dimension dim = new Dimension(130, 30);
		// Prima riga, prima colonna
		gbc.gridx = 0;
		gbc.gridy = 0;

		id_c = new JTextField("ID COMPONENT");
		id_c.setPreferredSize(dim);
		dataPanel.add(id_c, gbc);

		// Prima riga, seconda colonna
		gbc.gridx = 1;
		gbc.gridy = 0;

		wear = new JTextField("WEAR");
		wear.setPreferredSize(dim);
		dataPanel.add(wear, gbc);

		// Prima riga, terza colonna
		gbc.gridx = 2;
		gbc.gridy = 0;

		status = new JTextField("STATUS");
		status.setPreferredSize(dim);
		dataPanel.add(status, gbc);

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

		frame.add(sendPanel);

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

		wear.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (wear.getText().equals("WEAR")) {
					wear.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (wear.getText().isEmpty()) {
					wear.setText("WEAR"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		status.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (status.getText().equals("STATUS")) {
					status.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (status.getText().isEmpty()) {
					status.setText("STATUS"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

	}

	public void show() {
		frame.setVisible(true);
	}

	public void mex1() {
		mexLabel.setText("ERROR");
		mexLabel.setForeground(Color.RED);
	}

	public void mex2() {
		mexLabel.setText("SUCCESS");
		mexLabel.setForeground(Color.GREEN);
	}


	public JTextField getWear() {
		return wear;
	}
	
	public JTextField getId_c() {
		return id_c;
	}

	public void setId_c(JTextField id_c) {
		this.id_c = id_c;
	}
	
	public void setWear(JTextField wear) {
		this.wear = wear;
	}

	public JTextField getStatus() {
		return status;
	}

	public void setStatus(JTextField status) {
		this.status = status;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
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
			} else if (comp instanceof JLabel) {
				((JLabel) comp).setText("");
			}
		}
		panel.revalidate(); // Rende il pannello nuovamente valido
		panel.repaint(); // Ridisegna il pannello
	}

}
