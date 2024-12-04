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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unipv.sfw.model.staff.Session;

public class MeccanicoView extends AbsView {

    private JFrame frame;
    private JPanel mainContainer, popUpPanel, graphicPanel;
    private JLayeredPane overlayPanel;
    private JLabel imgLabel,mex, id_p;
    private JButton addComponentButton, addPilotButton, insertVehicleButton, insertRequestButton, removeComponentButton,
            removePilotButton, visualTimePsButton, visualStatusComponentButton;
    private ImageIcon imgVec, imgWllp1;

    public MeccanicoView() {

		frame = new JFrame("MECHANIC");
		frame.setSize(718, 800);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());

		/*
		 * CREAZIONE 1 SEZIONE: IMMAGINE VETTURA + OVERLAY
		 */

		try {
			
			imgWllp1 = new ImageIcon(this.getClass().getResource("/wllp1.jpg"));
			imgWllp1 = new ImageIcon(imgWllp1.getImage().getScaledInstance(718, 400, java.awt.Image.SCALE_SMOOTH));
			
			imgVec = new ImageIcon(this.getClass().getResource("/vehicleF1.png"));
			imgVec = new ImageIcon(imgVec.getImage().getScaledInstance(718, 400, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagini caricate");

		} catch (Exception e) {
			System.out.println(e);
		}

		//imposto l'immagine di sfondo
		mainContainer = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Disegna l'immagine di sfondo
				g.drawImage(imgWllp1.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		
		mainContainer.setLayout(new BorderLayout());
		
		// CREAZIONE OVERLAY
		overlayPanel = new JLayeredPane();
		overlayPanel.setPreferredSize(new Dimension(800, 300));
		
		imgLabel = new JLabel(imgVec);
		imgLabel.setBounds(0, 15, 700, 300);

		overlayPanel.add(imgLabel, Integer.valueOf(1));

		mex = new JLabel("ID PILOT :");
		mex.setForeground(Color.WHITE);
		mex.setBounds(20, 20, 100, 30);

		// testo centrato orizzontalmente
		mex.setHorizontalAlignment(SwingConstants.CENTER);

		id_p = new JLabel("NO PILOT");
		id_p.setForeground(Color.BLACK);
		id_p.setHorizontalAlignment(SwingConstants.CENTER);
		id_p.setBounds(120, 20, 100, 30);

		overlayPanel.add(mex, Integer.valueOf(2));
		overlayPanel.add(id_p, Integer.valueOf(2));

		/*
		 * CREAZIONE 2 SEZIONE: 6 BOTTONI E FINESTRE POP UP
		 */

		// Pannello con i bottoni in una griglia 3x2
		popUpPanel = new JPanel();
		popUpPanel.setPreferredSize(new Dimension(800, 100));
		popUpPanel.setLayout(new GridBagLayout());
		popUpPanel.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();

		// Spaziatura interna
		gbc.insets = new Insets(25, 20, 50, 20);
		gbc.anchor = GridBagConstraints.CENTER;

		Dimension dim = new Dimension(800, 200);

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
		graphicPanel.setPreferredSize(new Dimension(800, 100));
		graphicPanel.setOpaque(false);
		graphicPanel.setLayout(new GridBagLayout());

		Dimension dimBtn = new Dimension(800, 300);

		// Spaziatura interna
		gbc.insets = new Insets(25, 50, 25, 50);
		gbc.anchor = GridBagConstraints.CENTER;

		// Prima riga - Prima colonna (Insert Veichle)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		visualTimePsButton = new JButton("VISUAL TIME PIT STOP");
		visualTimePsButton.setPreferredSize(dimBtn);
		graphicPanel.add(visualTimePsButton, gbc);	

		gbc.gridx = 1;
		gbc.gridy = 0;

		visualStatusComponentButton = new JButton("VISUAL STATUS COMPONENT");
		visualStatusComponentButton.setPreferredSize(dimBtn);
		graphicPanel.add(visualStatusComponentButton, gbc);

		// AGGIUNGO I PANNELLI AL MAIN PANEL
		mainContainer.add(overlayPanel, BorderLayout.NORTH);
		mainContainer.add(popUpPanel, BorderLayout.CENTER);
		mainContainer.add(graphicPanel, BorderLayout.SOUTH);

		frame.add(mainContainer);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
	}

    // Getter e setter per i bottoni
    public JButton getAddComponentButton() {
        return addComponentButton;
    }

    public JButton getAddPilotButton() {
        return addPilotButton;
    }

    public JButton getRemoveComponentButton() {
        return removeComponentButton;
    }

    public JButton getRemovePilotButton() {
        return removePilotButton;
    }

    public JButton getVisualTimePsButton() {
        return visualTimePsButton;
    }

    public JButton getVisualStatusComponentButton() {
        return visualStatusComponentButton;
    }

    public JButton getInsertRequestButton() {
        return insertRequestButton;
    }

    public JButton getInsertVehicleButton() {
        return insertVehicleButton;
    }

    public JLabel getId_p() {
        return id_p;
    }

    public void setId_p() {
        this.id_p.setText(Session.getIstance().getId_pilot());
        id_p.setForeground(Color.BLACK);
        frame.validate();
    }
}
