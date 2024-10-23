package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

public class MeccanicoView extends AbsView {

	// frame generale
	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel mainContainer, overlayPanel, popUpPanel, graphicPanel;

	private JLabel imgvLabel, mex, id_p;

	// bottoni per l'interazione: 6
	private JButton addComponentButton, addPilotButton, insertVehicleButton, insertRequestButton, removeComponentButton,
			removePilotButton, visualTimePsButton, visualStatusComponentButton;
	
	private ImageIcon imgVec;
	private Image img;

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

		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBackground(Color.BLACK);

		/*
		 * CREAZIONE 1 SEZIONE: IMMAGINE VETTURA + OVERLAY
		 */
		
		try {

			imgVec = new ImageIcon(this.getClass().getResource("/Foto-MacchinaF1.jpg"));
			Image img = imgVec.getImage();
			System.out.println("immagine macchina caricata");

		} catch (Exception e) {
			System.out.println(e);
		}
		
		imgvLabel = new JLabel();
		 // Verifica se l'immagine è stata caricata correttamente e imposta l'immagine iniziale
        if ( img != null) {
            Image scaledInitialImage = img.getScaledInstance(frame.getWidth(), frame.getHeight() / 3, Image.SCALE_SMOOTH);
            imgvLabel.setIcon(new ImageIcon(scaledInitialImage));
        }

        // Aggiungi un listener al frame per monitorare i cambiamenti di dimensione
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Verifica se l'immagine è stata caricata correttamente
                if (img != null) {
                    // Ottieni le dimensioni correnti del frame
                    int frameWidth = frame.getWidth();
                    int frameHeight = frame.getHeight();

                    // Ridimensiona l'immagine mantenendo le proporzioni in base alle dimensioni del frame
                    Image scaledImage = img.getScaledInstance(frameWidth, frameHeight / 3, Image.SCALE_SMOOTH); // Altezza della parte superiore
                    imgvLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        });
		
		// CREAZIONE OVERLAY
		overlayPanel = new JPanel();
		
		overlayPanel.setOpaque(false);

		// posizione in basso a sinistra
		overlayPanel.setBounds(20, frame.getHeight() - 120, 200, 100);

		// divisione in due parti: mex + id pilot
		overlayPanel.setLayout(new GridLayout(2, 1));

		mex = new JLabel("ID PILOT :");
		mex.setForeground(Color.WHITE);

		// testo centrato orizzontalmente
		mex.setHorizontalAlignment(SwingConstants.CENTER);

		id_p = new JLabel();

		id_p.setForeground(Color.WHITE);
		id_p.setHorizontalAlignment(SwingConstants.CENTER);
		
		overlayPanel.add(imgvLabel);
		overlayPanel.add(mex);
		overlayPanel.add(id_p);

		/*
		 * CREAZIONE 2 SEZIONE: 6 BOTTONI E FINESTRE POP UP
		 */

		// Pannello con i bottoni in una griglia 3x2
		popUpPanel = new JPanel();

		// hgap = 10, vgap = 10, horizontal e vertical
		popUpPanel.setBackground(Color.BLACK);
		popUpPanel.setLayout(new GridLayout(3, 2, 10, 10));

		insertRequestButton = new JButton("INSERT REQUEST");
		insertVehicleButton = new JButton("INSERT VEHICLE");
		addComponentButton = new JButton("ADD COMPONENT");
		addPilotButton = new JButton("ADD PILOT");
		removeComponentButton = new JButton("REMOVE COMPONENT");
		removePilotButton = new JButton("REMOVE PILOT");

		Dimension dim = new Dimension(200, 50);

		removePilotButton.setPreferredSize(dim);
		addPilotButton.setPreferredSize(dim);
		removeComponentButton.setPreferredSize(dim);
		addComponentButton.setPreferredSize(dim);
		insertRequestButton.setPreferredSize(dim);
		insertVehicleButton.setPreferredSize(dim);

		popUpPanel.add(insertVehicleButton);
		popUpPanel.add(insertRequestButton);
		popUpPanel.add(addComponentButton);
		popUpPanel.add(removeComponentButton);
		popUpPanel.add(addPilotButton);
		popUpPanel.add(removePilotButton);

		/*
		 * CREAZIONE 3 SEZIONE: 2 BOTTONI E ""GRAFICI""
		 */

		graphicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

		graphicPanel.setBackground(Color.BLACK);
		graphicPanel.setLayout(new GridLayout(2, 1, 10, 10));

		visualTimePsButton = new JButton("VISUAL TIME PIT STOP");
		visualStatusComponentButton = new JButton("VISUAL STATUS COMPONENT");

		visualTimePsButton.setPreferredSize(dim);
		visualStatusComponentButton.setPreferredSize(dim);

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
