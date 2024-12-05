package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MagazziniereView extends AbsView {

	private JFrame frame;

	// decidere un titolo o una immagine
	private JPanel mainContainer, titlePanel, popUpPanel, mexPanel;

	private JButton showRequestButton, deleteRequestButton, updateCompoButton;

	private JComboBox<String> comboBox;

	private JLabel imgLabel, dataLabel, mex;

	private ImageIcon imgUser, imgWllp2;

	private JTextField placeholder;

	public MagazziniereView() {

		frame = new JFrame("WAREHOUSEMAN");
		frame.setSize(718, 800);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());

		/*
		 * CREAZIONE 1 SEZIONE : TITOLO
		 */
		titlePanel = new JPanel(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(718, 200));

		try {

			imgWllp2 = new ImageIcon(this.getClass().getResource("/wllp2.jpg"));
			imgWllp2 = new ImageIcon(imgWllp2.getImage().getScaledInstance(718, 400, java.awt.Image.SCALE_SMOOTH));

			imgUser = new ImageIcon(this.getClass().getResource("/icon.png"));
			imgUser = new ImageIcon(imgUser.getImage().getScaledInstance(320, 370, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagine user caricata");

		} catch (Exception e) {
			System.out.println(e);
		}
		
		// imposto l'immagine di sfondo
		mainContainer = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Disegna l'immagine di sfondo
				g.drawImage(imgWllp2.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		mainContainer.setLayout(new BorderLayout());
		
		imgLabel = new JLabel(imgUser);
		titlePanel.add(imgLabel, BorderLayout.WEST);
		titlePanel.setOpaque(false);
		
		dataLabel = new JLabel();
		dataLabel.setForeground(Color.BLACK);
		titlePanel.add(dataLabel, BorderLayout.CENTER);

		/*
		 * CREAZIONE 2 SEZIONE: 4 BOTTONI E FINESTRE POP UP
		 */
		popUpPanel = new JPanel();
		popUpPanel.setPreferredSize(new Dimension(718, 200));
		popUpPanel.setLayout(new GridBagLayout());
		popUpPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();

		// Spaziatura interna
		gbc.insets = new Insets(20, 20, 50, 20);
		gbc.anchor = GridBagConstraints.CENTER;

		Dimension dim = new Dimension(700, 200);

		// Prima riga - Prima colonna (Insert Veichle)
		gbc.gridx = 0;
		gbc.gridy = 0;

		showRequestButton = new JButton("SHOW REQUESTS");
		showRequestButton.setPreferredSize(dim);
		popUpPanel.add(showRequestButton, gbc);

		// Prima riga - Seconda colonna (insert Request)
		gbc.gridx = 1;
		gbc.gridy = 0;

		deleteRequestButton = new JButton("DELETE REQUEST");
		deleteRequestButton.setPreferredSize(dim);
		popUpPanel.add(deleteRequestButton, gbc);

		// Seconda riga - Prima colonna (add Component)
		gbc.gridx = 0;
		gbc.gridy = 1;

		updateCompoButton = new JButton("UPDATE COMPONENT");
		updateCompoButton.setPreferredSize(dim);
		popUpPanel.add(updateCompoButton, gbc);

		// Seconda riga - Seconda colonna (remove Component)
		gbc.gridx = 1;
		gbc.gridy = 1;

		String option[] = { "RUOTA ANTERIORE SX HARD", "RUOTA ANTERIORE DX HARD", "RUOTA POSTERIORE SX HARD",
				"RUOTA POSTERIORE DX HARD", "RUOTA ANTERIORE SX MEDIUM", "RUOTA ANTERIORE DX MEDIUM",
				"RUOTA POSTERIORE SX MEDIUM", "RUOTA POSTERIORE DX MEDIUM", "ALA ANTERIORE", "DRS", "MOTORE TERMICO",
				"ERS", "- ALL" };

		comboBox = new JComboBox<>(option);
		comboBox.setPreferredSize(dim);
		comboBox.setEditable(true);
		placeholder = (JTextField) comboBox.getEditor().getEditorComponent();
		placeholder.setText("QUANTITA' COMPONENTI");

		popUpPanel.add(comboBox, gbc);

		/*
		 * CREAZIONE 3 SEZIONE: LABEL PER MEX
		 */

		mexPanel = new JPanel();
		mexPanel.setOpaque(false);
		
		mex = new JLabel("SELECT A COMPONENT FROM THE MENU");	
		mex.setHorizontalAlignment(SwingConstants.CENTER);
		mex.setPreferredSize(new Dimension(718, 200));
		mex.setBackground(Color.BLACK);

		mexPanel.add(mex);

		mainContainer.add(titlePanel, BorderLayout.NORTH);
		mainContainer.add(popUpPanel, BorderLayout.CENTER);
		mainContainer.add(mexPanel, BorderLayout.SOUTH);

		frame.add(mainContainer);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
	}

	public JButton getShowRequestButton() {
		return showRequestButton;
	}

	public void setShowRequestButton(JButton showRequestButton) {
		this.showRequestButton = showRequestButton;
	}

	public JButton getDeleteRequestButton() {
		return deleteRequestButton;
	}

	public void setDeleteRequestButton(JButton deleteRequestButton) {
		this.deleteRequestButton = deleteRequestButton;
	}

	public JButton getUpdateCompoButton() {
		return updateCompoButton;
	}

	public void setUpdateCompoButton(JButton updateCompoButton) {
		this.updateCompoButton = updateCompoButton;
	}

	public JComboBox<String> getCombobox() {
		return comboBox;
	}

	public void setCombobox(JComboBox<String> combobox) {
		this.comboBox = combobox;
	}

	public void setMex() {
		mex.setText("SELECT A COMPONENT FROM THE MENU");
		mex.setForeground(Color.GRAY);
	}

	public void data(String name, String surname, int total) {
		dataLabel.setText(
				"NAME: " + name + "           " + "SURNAME: " + surname + "           " + "TOTAL REQUEST: " + total);

	}

	public void mexCombo(int quantity) {
		String currentText = placeholder.getText();
		mex.setText("NAME COMPONENT:  " + currentText + "             QUANTITY:  " + quantity);
		mex.setForeground(Color.BLACK);
	}
}
