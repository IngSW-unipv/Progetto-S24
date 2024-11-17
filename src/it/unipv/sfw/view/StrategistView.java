package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StrategistView extends AbsView {
	
	private JFrame frame;

	// decidere un titolo o una immagine
	private JPanel mainContainer, titlePanel, timePanel, buttonPanel;

	private JButton componentStatusButton, getTimeButton, createStrategyButton;

	private JLabel imgWeatherLabel, imgCircuitLabel, dataLabel;

	private ImageIcon imgWeather, imgCircuit;
	
	public StrategistView() {

		frame = new JFrame("WAREHOUSEMAN");
		frame.setSize(900, 700);
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
		 * CREAZIONE 1 SEZIONE : meteo+circuito+data/bottone
		 */
		titlePanel = new JPanel(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(700, 200));
		titlePanel.setBackground(Color.BLACK);

		try {

			imgWeather = new ImageIcon(this.getClass().getResource("/meteo.png"));
			imgWeather = new ImageIcon(imgWeather.getImage().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH));
			System.out.println("immagine user caricata");
			
			// immagine del circuito
			
		} catch (Exception e) {
			System.out.println(e);
		}

		imgWeatherLabel = new JLabel(imgWeather);
		titlePanel.add(imgWeatherLabel, BorderLayout.WEST);

		dataLabel = new JLabel();
		dataLabel.setForeground(Color.BLACK);
		dataLabel.setLayout(new BorderLayout());
		
		componentStatusButton = new JButton("MORE DETAILS");
		componentStatusButton.setPreferredSize(new Dimension(300,200));
		dataLabel.add(componentStatusButton, BorderLayout.SOUTH);
		
		titlePanel.add(dataLabel, BorderLayout.EAST);
		
		/*
		 * CREAZIONE 2 SEZIONE: 4 BOTTONI E FINESTRE POP UP
		 */
		timePanel = new JPanel();
		timePanel.setPreferredSize(new Dimension(700, 200));
		timePanel.setBackground(Color.BLACK);


		/*
		 * CREAZIONE 3 SEZIONE: LABEL PER MEX
		 */

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		
		buttonPanel.setPreferredSize(new Dimension(700, 200));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setOpaque(true);
		GridBagConstraints gbc = new GridBagConstraints();

		// Spaziatura interna
		gbc.insets = new Insets(20, 20, 50, 20);
		gbc.anchor = GridBagConstraints.CENTER;

		Dimension dim = new Dimension(700, 200);

		// Prima riga - Prima colonna (Insert Veichle)
		gbc.gridx = 0;
		gbc.gridy = 0;

		getTimeButton = new JButton("GET TIME");
		getTimeButton.setPreferredSize(dim);
		buttonPanel.add(getTimeButton, gbc);

		// Prima riga - Seconda colonna (insert Request)
		gbc.gridx = 1;
		gbc.gridy = 0;

		createStrategyButton = new JButton("CREATE STRATEGY");
		createStrategyButton.setPreferredSize(dim);
		buttonPanel.add(createStrategyButton, gbc);

		mainContainer.add(titlePanel, BorderLayout.NORTH);
		mainContainer.add(timePanel, BorderLayout.CENTER);
		mainContainer.add(buttonPanel, BorderLayout.SOUTH);

		frame.add(mainContainer);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
	}

}
