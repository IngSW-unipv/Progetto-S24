package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import it.unipv.sfw.frame.Frame;

/**
 * Classe che crea la view della pagina di login usata dagli utenti per accedere
 * al sistema.
 */
public class LoginView extends AbsView {

	private JPanel mainContainer, logoPanel, loginPanel, btnPanel, errorPanel;
	private JTextField username;
	private JPasswordField pwd;
	private JLabel imgBackgroundLabel, imgLogoLabel, userLabel, pwdLabel, errLabel;
	private JButton loginBtn;
	private ImageIcon imgBackground, imgLogo;

	public LoginView() {

		Font mediumFont = new Font("Arial", Font.BOLD, 16);
		System.out.println("ora sono qui - @LOGINVIEW");
		
		try {

			imgBackground = new ImageIcon(this.getClass().getResource("/wallpaperLogin2.jpg"));
			imgBackground = new ImageIcon(imgBackground.getImage().getScaledInstance(300, 100, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagine wallpaper caricata");

		} catch (Exception e) {
			System.out.println(e);
		}
		
		imgBackgroundLabel = new JLabel(imgBackground);
		
		// Inizializzazione JPanel
		mainContainer = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Disegna l'immagine di sfondo
				g.drawImage(imgBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		mainContainer.setLayout(new BorderLayout());
		mainContainer.add(imgBackgroundLabel);
	
		// PRIMA SEZIONE - LOGO PANEL
		logoPanel = new JPanel();
		logoPanel.setOpaque(false);

		// TRY CATCH PER DEBUG
		try {

			imgLogo = new ImageIcon(this.getClass().getResource("/F1-Logo.png"));
			imgLogo = new ImageIcon(imgLogo.getImage().getScaledInstance(300, 100, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagine logo caricata");

		} catch (Exception e) {
			System.out.println(e);
		}

		imgLogoLabel = new JLabel(imgLogo);
		imgLogoLabel.setOpaque(false);
		imgLogoLabel.setPreferredSize(new Dimension(300, 200));

		imgLogoLabel.setHorizontalAlignment(JLabel.CENTER);
		imgLogoLabel.setIcon(imgLogo);

		// SECONDA SEZIONE
		loginPanel = new JPanel(new GridBagLayout());
		loginPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();

		// Spaziatura interna
		gbc.insets = new Insets(10, 10, 10, 10);

		Dimension dimField = new Dimension(250, 50);

		// Prima riga - Prima colonna (userLabel)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1; // Occupa solo una colonna
		gbc.fill = GridBagConstraints.HORIZONTAL;

		userLabel = new JLabel("ID");
		userLabel.setForeground(Color.YELLOW);
		loginPanel.add(userLabel, gbc);

		// Prima riga - Seconda colonna (username)
		gbc.gridx = 1;
		gbc.gridy = 0;

		// Utilizza la variabile globale username invece di creare una locale
		username = new JTextField("Insert ID");
		username.setPreferredSize(dimField);
		loginPanel.add(username, gbc);

		// Seconda riga - Prima colonna (pwdLabel)
		gbc.gridx = 0;
		gbc.gridy = 1;

		pwdLabel = new JLabel("Password");
		pwdLabel.setForeground(Color.YELLOW);
		loginPanel.add(pwdLabel, gbc);

		// Seconda riga - Seconda colonna (pwd)
		gbc.gridx = 1;
		gbc.gridy = 1;

		pwd = new JPasswordField("Password");
		pwd.setPreferredSize(dimField);
		loginPanel.add(pwd, gbc);

		// Terza riga - Pannello del pulsante (unione delle due colonne)
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2; // Occupa entrambe le colonne
		gbc.fill = GridBagConstraints.HORIZONTAL;

		btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setOpaque(false);
		
		Dimension dimBtn = new Dimension(100, 40);

		loginBtn = new JButton("LOGIN");
		loginBtn.setPreferredSize(dimBtn);
		btnPanel.add(loginBtn);
		loginPanel.add(btnPanel, gbc);

		// TERZA SEZIONE
		errorPanel = new JPanel();
		errLabel = new JLabel("Username o Password errati!");
		errLabel.setForeground(Color.RED);

		errorPanel.add(errLabel);
		errorPanel.setVisible(false);
		errorPanel.setBackground(Color.BLACK);

		userLabel.setFont(mediumFont);
		pwdLabel.setFont(mediumFont);

		// Aggiunta dei FocusListener per i campi di testo
		// FocusListener per il campo username (JTextField)
		username.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (username.getText().equals("Insert ID")) {
					username.setText(""); // Rimuove il testo predefinito
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (username.getText().isEmpty()) {
					username.setText("Insert ID"); // Ripristina il testo predefinito se vuoto
				}
			}
		});

		// FocusListener per il campo pwd (JPasswordField)
		pwd.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String pwdText = new String(pwd.getPassword());
				if (pwdText.equals("Password")) {
					pwd.setText(""); // Rimuove il testo predefinito
					pwd.setEchoChar('●'); // Imposta il carattere di mascheramento della password
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				String pwdText = new String(pwd.getPassword());
				if (pwdText.isEmpty()) {
					pwd.setText("Password"); // Ripristina il testo predefinito se vuoto
					pwd.setEchoChar((char) 0); // Rimuove il mascheramento della password
				}
			}
		});

		logoPanel.add(imgLogoLabel);
		mainContainer.add(logoPanel, BorderLayout.NORTH);
		mainContainer.add(loginPanel, BorderLayout.CENTER);
		mainContainer.add(errorPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout()); // Imposta il layout di LoginView come BorderLayout
		this.add(mainContainer, BorderLayout.CENTER);
	}

	public JButton getAccediButton() {
		return loginBtn;
	}

	public JPasswordField getPasswordField() {
		return pwd;
	}

	public JTextField getUsernameField() {
		return username;
	}

	@Override
	public void onLoad() {
		errorPanel.setVisible(false);
	}

	public void upError() {
		errorPanel.setVisible(true);
		mainContainer.repaint();
	}
}
