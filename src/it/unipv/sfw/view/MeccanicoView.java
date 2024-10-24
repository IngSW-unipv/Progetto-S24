package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unipv.sfw.model.staff.Session;

public class MeccanicoView extends AbsView {

	// frame generale
	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel mainContainer, topPanel, overlayPanel, popUpPanel, graphicPanel;

	private JLabel imgLabel, mex, id_p;

	// bottoni per l'interazione: 6
	private JButton addComponentButton, addPilotButton, insertVehicleButton, insertRequestButton, removeComponentButton,
			removePilotButton, visualTimePsButton, visualStatusComponentButton;
	
	private ImageIcon imgVec;

	public MeccanicoView() {

		frame = new JFrame("MECHANIC");
		frame.setSize(900, 800);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());
		mainContainer = new JPanel();

		/*
		 * CREAZIONE 1 SEZIONE: IMMAGINE VETTURA + OVERLAY
		 */
		
		try {

			imgVec = new ImageIcon(this.getClass().getResource("/Foto-MacchinaF1.jpg"));
			imgVec = new ImageIcon(imgVec.getImage().getScaledInstance(900, 350, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagine macchina caricata");
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		imgLabel = new JLabel(imgVec);
		
		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBackground(Color.BLACK);
		
		// CREAZIONE OVERLAY
		overlayPanel = new JPanel();	
		overlayPanel.setPreferredSize(new Dimension(900, 350));
//		overlayPanel.setOpaque(true);
		overlayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		overlayPanel.add(imgLabel);
		mex = new JLabel("ID PILOT :");
		mex.setForeground(Color.WHITE);

		// testo centrato orizzontalmente
		mex.setHorizontalAlignment(SwingConstants.CENTER);

		id_p = new JLabel();

		id_p.setForeground(Color.WHITE);
		id_p.setHorizontalAlignment(SwingConstants.CENTER);
		
		overlayPanel.add(mex);
		overlayPanel.add(id_p);
		
		overlayPanel.setBounds(20, frame.getHeight() - 120, 200, 50);

		
		/*
		 * CREAZIONE 2 SEZIONE: 6 BOTTONI E FINESTRE POP UP
		 */

		// Pannello con i bottoni in una griglia 3x2
		popUpPanel = new JPanel();
		popUpPanel.setPreferredSize(new Dimension(900,300));
		popUpPanel.setBackground(Color.BLACK);
		popUpPanel.setLayout(new GridBagLayout());
		popUpPanel.setOpaque(true);
		GridBagConstraints gbc = new GridBagConstraints();

		// Spaziatura interna
		gbc.insets = new Insets(50, 50, 50, 50);
		
		Dimension dim = new Dimension(300, 200);
		
		// Prima riga - Prima colonna (Insert Veichle)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1; // Occupa solo una colonna
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		insertVehicleButton = new JButton("INSERT VEHICLE");
		insertVehicleButton.setPreferredSize(dim);
		popUpPanel.add(insertVehicleButton, gbc);
		
		// Prima riga - Seconda colonna (insert Request)
		gbc.gridx = 1;
		gbc.gridy = 0;

		// Utilizza la variabile globale username invece di creare una locale
		insertRequestButton = new JButton("INSERT REQUEST");
		insertRequestButton.setPreferredSize(dim);
		popUpPanel.add(insertRequestButton, gbc);
		
		// Seconda riga - Prima colonna (add Component)
		gbc.gridx = 0;
		gbc.gridy = 1;

		addComponentButton = new JButton("ADD COMPONENT");
		addComponentButton.setPreferredSize(dim);
		popUpPanel.add(addComponentButton, gbc);
		
		// Seconda riga - Seconda colonna (remove Component)
		gbc.gridx = 1;
		gbc.gridy = 1;

		removeComponentButton = new JButton("REMOVE COMPONENT");
		removeComponentButton.setPreferredSize(dim);
		popUpPanel.add(removeComponentButton, gbc);
		
		// Terza riga - Prima colonna (add Pilot)
		gbc.gridx = 0;
		gbc.gridy = 2;

		addPilotButton = new JButton("ADD PILOT");
		addPilotButton.setPreferredSize(dim);
		popUpPanel.add(addPilotButton, gbc);
		
		// Terza riga - Seconda colonna (remove Pilot)
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		removePilotButton = new JButton("REMOVE PILOT");
		removePilotButton.setPreferredSize(dim);
		popUpPanel.add(removePilotButton, gbc);

		/*
		 * CREAZIONE 3 SEZIONE: 2 BOTTONI E ""GRAFICI""
		 */

		graphicPanel = new JPanel();

		graphicPanel.setBackground(Color.BLACK);
		graphicPanel.setLayout(new GridLayout(2, 1, 10, 10));
		
		Dimension dimBtn = new Dimension(150, 100);
		
		visualTimePsButton = new JButton("VISUAL TIME PIT STOP");
		visualStatusComponentButton = new JButton("VISUAL STATUS COMPONENT");

		visualTimePsButton.setPreferredSize( dimBtn);
		visualStatusComponentButton.setPreferredSize( dimBtn);

		graphicPanel.add(visualTimePsButton);
		graphicPanel.add(visualStatusComponentButton);

		// AGGIUNGO I PANNELLI AL MAIN PANEL
		
		mainContainer.add(overlayPanel, BorderLayout.NORTH);

		mainContainer.add(popUpPanel, BorderLayout.CENTER);

		mainContainer.add(graphicPanel, BorderLayout.SOUTH);

		frame.add(mainContainer);
		frame.setVisible(true);
		frame.validate();
	    frame.repaint();
	}

	public JButton getAddComponentButton() {
		return addComponentButton;
	}

	public void setAddComponentButton(JButton addComponentButton) {
		this.addComponentButton = addComponentButton;
	}

	public JButton getAddPilotButton() {
		return addPilotButton;
	}

	public void setAddPilotButton(JButton addPilotButton) {
		this.addPilotButton = addPilotButton;
	}

	public JButton getRemoveComponentButton() {
		return removeComponentButton;
	}

	public void setRemoveComponentButton(JButton removeComponentButton) {
		this.removeComponentButton = removeComponentButton;
	}

	public JButton getRemovePilotButton() {
		return removePilotButton;
	}

	public void setRemovePilotButton(JButton removePilotButton) {
		this.removePilotButton = removePilotButton;
	}

	public JButton getVisualTimePsButton() {
		return visualTimePsButton;
	}

	public void setVisualTimePsButton(JButton visualTimePsButton) {
		this.visualTimePsButton = visualTimePsButton;
	}

	public JButton getVisualStatusComponentButton() {
		return visualStatusComponentButton;
	}

	public void setVisualStatusComponentButton(JButton visualStatusComponentButton) {
		this.visualStatusComponentButton = visualStatusComponentButton;
	}

	public JButton getInsertRequestButton() {
		return insertRequestButton;
	}

	public void setInsertRequestButton(JButton insertRequest) {
		this.insertRequestButton = insertRequest;
	}

	public JButton getInsertVehicleButton() {
		return insertVehicleButton;
	}

	public void setInsertVehicleButton(JButton insertVehicleButton) {
		this.insertVehicleButton = insertVehicleButton;
	}

	public JLabel getId_p() {
		return id_p;
	}

	public void setId_p() {
		this.id_p.setText(Session.getIstance().getId_pilot());
	}

}
