package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Classe che crea la view della pagina di login usata dagli utenti per accedere
 * al sistema.
 *
 */

public class LoginView extends AbsView {

	private JFrame frame;

	private JPanel mainContainer, 
							  loginPanel,
							  btnPanel,
							  errorPanel;

	private JTextField username;

	private JPasswordField pwd;

	private JLabel imgLabel, 
							  userLabel, 
							  pwdLabel,
							  errLabel;

	private JButton loginBtn;

	private ImageIcon img;

	public LoginView() {

		frame = new JFrame("LOGIN");
		frame.setSize(500, 500);
		frame.setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Font mediumFont = new Font("Arial", Font.BOLD, 16);

		// Inizializzazione JPanel
		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());

		// Inizializzazione JLabel e inizializzazione ImageIcon
		imgLabel = new JLabel();
		
		
		
		
		
		
		// SECONDA SEZIONE 
		
		loginPanel = new JPanel();
		
		// hgap = 10, vgap = 10, horizontal e vertical
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setLayout(new GridLayout(3, 1, 10, 10));
		
		userLabel = new JLabel("ID");
		pwdLabel = new JLabel("Password");
		
		username = new JTextField("Insert ID");
		pwd = new JPasswordField("Password");
		
		loginPanel.add(userLabel);
		loginPanel.add(username);
		loginPanel.add(pwdLabel);
		loginPanel.add(pwd);
		
		btnPanel  =new JPanel(new FlowLayout(FlowLayout.CENTER));
		loginBtn = new JButton("LOGIN");
		
		Dimension dim = new Dimension(100, 100);
		
		loginBtn.setPreferredSize(dim);
		
		btnPanel.add(loginBtn);
		
		loginPanel.add(btnPanel);
		
		//nel caso aggiungere un componente vuoto per riempire la griglia
		
		
		// TERZA SESSIONE
		errorPanel = new JPanel();
		errLabel = new JLabel("Username o Password errati!");
		errLabel.setForeground(Color.RED);
		
		errorPanel.add(errLabel);
		errorPanel.setVisible(false);
		
		userLabel.setFont(mediumFont);
		pwdLabel.setFont(mediumFont);
		
		mainContainer.add(imgLabel,BorderLayout.NORTH);
		mainContainer.add(loginPanel, BorderLayout.CENTER);
		mainContainer.add(errorPanel, BorderLayout.SOUTH);
		
		frame.add(mainContainer);
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
