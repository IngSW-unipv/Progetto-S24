package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unipv.sfw.model.staff.Session;

public class MeccanicoView extends AbsView{

	// frame generale
	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel mainContainer,
							  imgPanel,
							  overlayPanel,
							  popUpPanel,
							  graphicPanel;

	// pannelli per non far espandere i bottoni nelle celle
	private JPanel cellPanel1,
							  cellPanel2,
							  cellPanel3,
							  cellPanel4,
							  cellPanel5,
							  cellPanel6,
							  cellPanel7,
							  cellPanel8;
	
	private JLabel mex,
							  id_p;

	// bottoni per l'interazione: 6
	private JButton addComponentButton, 
								addPilotButton,
								insertVehicleButton,
								insertRequestButton,
								removeComponentButton,
								removePilotButton,
								visualTimePsButton,
								visualStatusComponentButton;

	public MeccanicoView() {

		frame  = new JFrame("MECHANIC");
		frame.setSize(800, 600);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainContainer = new JPanel();

		mainContainer.setLayout(new BorderLayout());

		/*
		 * CREAZIONE 1 SEZIONE: IMMAGINE VETTURA + OVERLAY
		 */
		imgPanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				
				/*
				 * ImageIcon imageIcon = new ImageIcon("percorso/della/tua/immagine.jpg");
				 * Sostituisci con il percorso dell'immagine 
				 * Image img =
				 * image.Icon.getImage();
				 * g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				 * 
				 */

			}

		};

		// CREAZIONE OVERLAY
		overlayPanel = new JPanel();
		
		overlayPanel.setBackground(new Color(0,0,0,150));
		
		// posizione in basso a sinistra
		overlayPanel.setBounds(20, frame.getHeight() - 120, 200, 100);
		
		// divisione in due parti: mex + id pilot
		overlayPanel.setLayout(new GridLayout(2,1));
		
		mex = new JLabel("ID PILOT :");
		mex.setForeground(Color.WHITE);
		
		// testo centrato orizzontalmente
		mex.setHorizontalAlignment(SwingConstants.CENTER);
		
		id_p = new JLabel();
		
		id_p.setForeground(Color.WHITE);
		id_p.setHorizontalAlignment(SwingConstants.CENTER);
			
		overlayPanel.add(mex);
		overlayPanel.add(id_p);
		
		/*
		 * CREAZIONE 2 SEZIONE: 6 BOTTONI E FINESTRE POP UP
		 */

		// Pannello con i bottoni in una griglia 3x2
		popUpPanel = new JPanel();
		cellPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// hgap = 10, vgap = 10, horizontal e vertical
		popUpPanel.setBackground(Color.BLACK);
		popUpPanel.setLayout(new GridLayout(3, 2, 10, 10));
		
		insertRequestButton = new JButton("INSERT REQUEST");
		insertVehicleButton = new JButton("INSERT VEHICLE");
		addComponentButton = new JButton("ADD COMPONENT");
		addPilotButton = new JButton("ADD PILOT");
		removeComponentButton = new JButton("REMOVE COMPONENT");
		removePilotButton = new JButton("REMOVE PILOT");

		Dimension dim = new Dimension(100, 100);

		removePilotButton.setPreferredSize(dim);
		addPilotButton.setPreferredSize(dim);
		removeComponentButton.setPreferredSize(dim);
		addComponentButton.setPreferredSize(dim);
		insertRequestButton.setPreferredSize(dim);
		insertVehicleButton.setPreferredSize(dim);
		
		cellPanel1.add(insertVehicleButton);
		cellPanel2.add(insertRequestButton);
		cellPanel3.add(addComponentButton);
		cellPanel4.add(removeComponentButton);
		cellPanel5.add(addPilotButton);
		cellPanel6.add(removePilotButton);
		
		popUpPanel.add(cellPanel1);
		popUpPanel.add(cellPanel2);
		popUpPanel.add(cellPanel3);
		popUpPanel.add(cellPanel4);
		popUpPanel.add(cellPanel5);
		popUpPanel.add(cellPanel6);
		
		/*
		 * CREAZIONE 3 SEZIONE: 2 BOTTONI E ""GRAFICI""
		 */

		graphicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
		cellPanel7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel8 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		graphicPanel.setBackground(Color.BLACK);
		graphicPanel.setLayout(new GridLayout(2, 1, 10, 10));

		visualTimePsButton = new JButton("VISUAL TIME PIT STOP");
		visualStatusComponentButton = new JButton("VISUAL STATUS COMPONENT");

		visualTimePsButton.setPreferredSize(dim);
		visualStatusComponentButton.setPreferredSize(dim);
		
		cellPanel7.add(visualTimePsButton);
		cellPanel8.add(visualStatusComponentButton);
		
		graphicPanel.add(cellPanel7);
		graphicPanel.add(cellPanel8);

		// AGGIUNGO I PANNELLI AL MAIN PANEL
		mainContainer.add(imgPanel, BorderLayout.NORTH);
		mainContainer.add(overlayPanel, BorderLayout.NORTH);

		mainContainer.add(popUpPanel, BorderLayout.CENTER);

		mainContainer.add(graphicPanel, BorderLayout.SOUTH);
		
		frame.add(mainContainer);
		
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
