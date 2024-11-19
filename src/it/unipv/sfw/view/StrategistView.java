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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class StrategistView extends AbsView {

	private JFrame frame;

	private JPanel mainContainer, titlePanel, statusPanel, timePanel, buttonPanel;

	private JButton componentStatusButton, getTimeButton, createStrategyButton;

	private JLabel imgWeatherLabel, imgCircuitLabel, dataLabel;

	private ImageIcon imgWeather, imgCircuit;
	
	private DefaultTableModel  tabTime;

	public StrategistView() {

		frame = new JFrame("STRATEGIST");
		frame.setSize(900, 800);
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

		statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(250, 250));
		statusPanel.setLayout(new GridBagLayout());
		statusPanel.setBackground(Color.BLACK);
		statusPanel.setOpaque(true);
		GridBagConstraints gbcs = new GridBagConstraints();

		// Spaziatura interna
		gbcs.insets = new Insets(20, 20, 60, 20);
		gbcs.anchor = GridBagConstraints.CENTER;

		Dimension dims = new Dimension(300, 200);

		// Prima riga - Prima colonna (Insert Veichle)
		gbcs.gridx = 0;
		gbcs.gridy = 0;

		dataLabel = new JLabel("SONO QUI");
		dataLabel.setForeground(Color.WHITE);
		dataLabel.setBackground(Color.BLACK);
		statusPanel.add(dataLabel, gbcs);

		// Prima riga - Seconda colonna (insert Request)
		gbcs.gridx = 0;
		gbcs.gridy = 1;

		componentStatusButton = new JButton("MORE DETAILS");
		componentStatusButton.setPreferredSize(dims);
		statusPanel.add(componentStatusButton, gbcs);

		titlePanel.add(statusPanel, BorderLayout.EAST);

		/*
		 * CREAZIONE 2 SEZIONE: 4 BOTTONI E FINESTRE POP UP
		 */
		timePanel = new JPanel();
		timePanel.setPreferredSize(new Dimension(700, 200));
		timePanel.setBackground(Color.BLACK);
		
		String[] column = {"TIME SECTOR 1","TIME SECTOR 2","TIME SECTOR 3","TIME LAP"};
		
		// Object[] columnNames, int rowCount
		tabTime = new DefaultTableModel(column,0);
		
		JTable tab = new JTable(tabTime);
		
		timePanel.add(tab);

		/*
		 * CREAZIONE 3 SEZIONE: LABEL PER MEX
		 */

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);

		buttonPanel.setPreferredSize(new Dimension(700, 100));
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

	public void addRow(String t1, String t2, String t3, String t4) {
		Object[] newRow = {t1,t2,t3,t4};
		tabTime.addRow(newRow);
	}
	
	public JPanel getTitlePanel() {
		return titlePanel;
	}

	public void setTitlePanel(JPanel titlePanel) {
		this.titlePanel = titlePanel;
	}

	public JPanel getStatusPanel() {
		return statusPanel;
	}

	public void setStatusPanel(JPanel statusPanel) {
		this.statusPanel = statusPanel;
	}

	public JPanel getTimePanel() {
		return timePanel;
	}

	public void setTimePanel(JPanel timePanel) {
		this.timePanel = timePanel;
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JButton getComponentStatusButton() {
		return componentStatusButton;
	}

	public void setComponentStatusButton(JButton componentStatusButton) {
		this.componentStatusButton = componentStatusButton;
	}

	public JButton getGetTimeButton() {
		return getTimeButton;
	}

	public void setGetTimeButton(JButton getTimeButton) {
		this.getTimeButton = getTimeButton;
	}

	public JButton getCreateStrategyButton() {
		return createStrategyButton;
	}

	public void setCreateStrategyButton(JButton createStrategyButton) {
		this.createStrategyButton = createStrategyButton;
	}

	public JLabel getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(JLabel dataLabel) {
		this.dataLabel = dataLabel;
	}
	
}
