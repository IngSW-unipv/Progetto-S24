package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unipv.sfw.model.staff.Session;

public class MagazziniereView extends AbsView {

	private JFrame frame;

	// decidere un titolo o una immagine
	private JPanel mainContainer, titlePanel, popUpPanel, mexPanel;

	private JButton showRequestButton, deleteRequestButton, updateCompoButton;

	private JComboBox<String> comboBox;

	private JLabel mex;

	public MagazziniereView() {

		frame = new JFrame("WAREHOUSEMAN");
		frame.setSize(800, 700);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());

		mainContainer = new JPanel();

		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBackground(Color.BLACK);

		/*
		 * CREAZIONE 1 SEZIONE : TITOLO
		 */
		titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(700, 200));
		titlePanel.setBackground(Color.GREEN);
		/*
		 * CREAZIONE 2 SEZIONE: 4 BOTTONI E FINESTRE POP UP
		 */
		popUpPanel = new JPanel();
		popUpPanel = new JPanel();
		popUpPanel.setPreferredSize(new Dimension(700, 200));
		popUpPanel.setBackground(Color.BLACK);
		popUpPanel.setLayout(new GridBagLayout());
		popUpPanel.setOpaque(true);
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

		String option[] = { "QUANTITA' COMPONENTI", "RUOTA ANTERIORE SX HARD", "RUOTA ANTERIORE DX HARD",
				"RUOTA POSTERIORE SX HARD", "RUOTA POSTERIORE DX HARD",

				"RUOTA ANTERIORE SX MEDIUM", "RUOTA ANTERIORE DX MEDIUM", "RUOTA POSTERIORE SX MEDIUM",
				"RUOTA POSTERIORE DX MEDIUM",

				"ALA ANTERIORE", "DRS", "MOTORE TERMICO", "ERS", "- ALL" };

		comboBox = new JComboBox<>(option);
		comboBox.setPreferredSize(dim);
		popUpPanel.add(comboBox, gbc);

		/*
		 * CREAZIONE 3 SEZIONE: LABEL PER MEX
		 */

		mexPanel = new JPanel();
		mexPanel.setBackground(Color.WHITE);

		mex = new JLabel();
		mex.setText("sono qui");
		mex.setHorizontalAlignment(SwingConstants.CENTER);
		mex.setPreferredSize(new Dimension(700,200));

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

	public JLabel getMex() {
		return mex;
	}

	public void setMex(JLabel mex) {
		// aggiungere il valore da sessione.magazziniere
		this.mex.setText("REQUEST TOTAL: ");
	}

}
