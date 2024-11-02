package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class McPopUpRequestView {

	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel dataPanel, sendPanel;

	private JLabel titleLabel, mexLabel;

	// inserimento dati
	private JTextField id_s, id_c, id_v;

	// area per descrivere la richiesta
	private JTextArea desc;

	// bottoni per l'interazione: 1
	private JButton sendButton;

	public McPopUpRequestView() {

		frame = new JFrame("INSERT REQUEST");

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

		titleLabel = new JLabel("REQUEST", SwingConstants.CENTER);

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
		dataPanel.setLayout(new GridLayout(2, 3, 10, 10));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 20, 10, 20);

		Dimension dim = new Dimension(100, 30);

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
		
		// Prima  riga, terza colonna
		gbc.gridx = 2;
		gbc.gridy = 0;
		id_v = new JTextField("ID VEHICLE");
		id_v.setPreferredSize(dim);
		dataPanel.add(id_v, gbc);
		
		// Seconda riga, prima/seconda/terza colonna
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;   // Numero di colonne da occupare
        gbc.gridheight = 1;  // Numero di righe da occupare
		desc = new JTextArea("DESCRIPTION...");
		desc.setPreferredSize(new Dimension(400, 100));
		dataPanel.add(desc, gbc);
		
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
		sendButton.setPreferredSize(new Dimension(150,30));
		sendPanel.add(sendButton, gbcSend);
		
		mexLabel = new JLabel();

		gbcSend.gridy = 1; // Sposta il messaggio sotto il bottone
		mexLabel = new JLabel("", SwingConstants.CENTER);
		mexLabel.setForeground(Color.WHITE);
		sendPanel.add(mexLabel, gbcSend);

		frame.add(sendPanel, BorderLayout.CENTER);
		
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

		desc.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (desc.getText().equals("DESCRIPTION...")) {
					desc.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (desc.getText().isEmpty()) {
					desc.setText("DESCRIPTION..."); // Ripristina il testo predefinito se vuoto
				}
			}
		});
	}

	public void show() {
		frame.setVisible(true);
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

	public JTextField getId_s() {
		return id_s;
	}

	public void setId_s(JTextField id_s) {
		this.id_s = id_s;
	}

	public JTextField getId_c() {
		return id_c;
	}

	public void setId_c(JTextField id_c) {
		this.id_c = id_c;
	}

	public JTextField getId_v() {
		return id_v;
	}

	public void setId_v(JTextField id_v) {
		this.id_v = id_v;
	}

	public JTextArea getDesc() {
		return desc;
	}

	public void setDesc(JTextArea desc) {
		this.desc = desc;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

}
