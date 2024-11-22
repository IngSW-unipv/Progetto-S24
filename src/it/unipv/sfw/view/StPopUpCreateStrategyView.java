package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import it.unipv.sfw.model.staff.Session;

public class StPopUpCreateStrategyView {
	private JFrame frame;
	private JPanel mainContainer, componentPanel, strategyPanel, mexPanel;
	private JButton detailsButton;
	private JComboBox<String> box;
	private JLabel componentLabel1, componentLabel2, strategyLabel1, strategyLabel2, strategyLabel3, mexLabel;
	private JTextField placeholder;

	public StPopUpCreateStrategyView() {
		// Setup base components
		frame = new JFrame("CREATE STRATEGY");
		frame.setSize(800, 700);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon icona = new ImageIcon(getClass().getResource("/F1-Logo.png"));
		frame.setIconImage(icona.getImage());

		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBackground(Color.BLACK);

		componentPanel = new JPanel();
		componentPanel.setPreferredSize(new Dimension(600, 300));
		componentPanel.setBackground(Color.BLACK);
		componentPanel.setOpaque(true);
		componentPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcs = new GridBagConstraints();
		gbcs.insets = new Insets(20, 20, 60, 20);
		gbcs.anchor = GridBagConstraints.WEST;
		
		// Primo elemento (0, 0)
		gbcs.gridx = 0;
		gbcs.gridy = 0;
		componentLabel1 = new JLabel("DEGRADATION OF COMPONENTS");
		componentLabel1.setForeground(Color.WHITE);
		componentLabel1.setBackground(Color.BLACK);
		componentPanel.add(componentLabel1, gbcs);

		// Secondo elemento (0, 1)
		gbcs.gridx = 0;
		gbcs.gridy = 1;
		componentLabel2 = new JLabel("VEHICLE STATUS");
		componentLabel2.setForeground(Color.WHITE);
		componentLabel2.setBackground(Color.BLACK);
		componentPanel.add(componentLabel2, gbcs);

		// Quarto elemento (1, 1)
		gbcs.gridx = 1;
		gbcs.gridy = 1;
		detailsButton = new JButton("MORE DETAILS");
		componentPanel.add(detailsButton, gbcs);

		strategyPanel = new JPanel();
		strategyPanel.setPreferredSize(new Dimension(600, 300));
		strategyPanel.setBackground(Color.BLACK);
		strategyPanel.setOpaque(true);

		// Impostiamo GridBagLayout
		strategyPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcs_2 = new GridBagConstraints();
		gbcs_2.insets = new Insets(20, 20, 60, 20); // Margini tra gli elementi
		gbcs_2.anchor = GridBagConstraints.WEST; // Allineamento al centro

		// Primo elemento: Label "CURRENT STRATEGY" (0, 0)
		gbcs_2.gridx = 0;
		gbcs_2.gridy = 0;
		strategyLabel1 = new JLabel("CURRENT STRATEGY");
		strategyLabel1.setForeground(Color.WHITE);
		strategyPanel.add(strategyLabel1, gbcs_2);

		// Secondo elemento: Label "STANDARD" (1, 0)
		gbcs_2.gridx = 1;
		gbcs_2.gridy = 0;
		strategyLabel2 = new JLabel(Session.getIstance().getStrategy());
		strategyLabel2.setForeground(Color.WHITE);
		strategyPanel.add(strategyLabel2, gbcs_2);

		// Terzo elemento: Label "NEW STRATEGY" (0, 1)
		gbcs_2.gridx = 0;
		gbcs_2.gridy = 1;
		strategyLabel3 = new JLabel("NEW STRATEGY");
		strategyLabel3.setForeground(Color.WHITE);
		strategyPanel.add(strategyLabel3, gbcs_2);

		// Quarto elemento: JComboBox per le opzioni (1, 1)
		gbcs_2.gridx = 1;
		gbcs_2.gridy = 1;
		String[] options = {"UNDERCUT", "STANDARD", "OVERCUT"};
		box = new JComboBox<>(options);
		box.setForeground(Color.WHITE);
		box.setBackground(Color.BLACK);
		box.setEditable(true);

		// Placeholder per il JComboBox
		placeholder = (JTextField) box.getEditor().getEditorComponent();
		placeholder.setText("SELECT A STRATEGY"); // Testo iniziale
		strategyPanel.add(box, gbcs_2);

		// Creazione di mexPanel
		mexPanel = new JPanel();
		mexPanel.setBackground(Color.BLACK);
		mexPanel.setPreferredSize(new Dimension(600, 100));
		mexPanel.setOpaque(true);


		mexPanel = new JPanel();
		mexPanel.setBackground(Color.BLACK);

		mexLabel = new JLabel();
		mexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mexLabel.setPreferredSize(new Dimension(600, 100));
		mexLabel.setBackground(Color.BLACK);

		mexPanel.add(mexLabel);

		mainContainer.add(componentPanel, BorderLayout.NORTH);
		mainContainer.add(strategyPanel, BorderLayout.CENTER);
		mainContainer.add(mexPanel, BorderLayout.SOUTH);

		frame.add(mainContainer);
		frame.validate();
		frame.repaint();
	}

	public void show() {
		frame.setVisible(true);
	}

	public JButton getDetailsButton() {
		return detailsButton;
	}

	public void setDetailsButton(JButton detailsButton) {
		this.detailsButton = detailsButton;
	}

	public JPanel getComponentPanel() {
		return componentPanel;
	}

	public void setComponentPanel(JPanel componentPanel) {
		this.componentPanel = componentPanel;
	}

	public JPanel getStrategyPanel() {
		return strategyPanel;
	}

	public void setStrategyPanel(JPanel strategyPanel) {
		this.strategyPanel = strategyPanel;
	}

	public JPanel getMexPanel() {
		return mexPanel;
	}

	public void setMexPanel(JPanel mexPanel) {
		this.mexPanel = mexPanel;
	}

	public JLabel getMexLabel() {
		return mexLabel;
	}

	public void setMexLabel(JLabel mexLabel) {
		this.mexLabel = mexLabel;
	}

	public JComboBox<String> getBox() {
		return box;
	}

	public void setBox(JComboBox<String> box) {
		this.box = box;
	}

	public JLabel getComponentLabel1() {
		return componentLabel1;
	}

	public void setComponentLabel1(JLabel componentLabel1) {
		this.componentLabel1 = componentLabel1;
	}

	public JLabel getComponentLabel2() {
		return componentLabel2;
	}

	public void setComponentLabel2(JLabel componentLabel2) {
		this.componentLabel2 = componentLabel2;
	}

	public JLabel getStrategyLabel2() {
		return strategyLabel2;
	}

	public void setStrategyLabel2(JLabel strategyLabel2) {
		this.strategyLabel2 = strategyLabel2;
	}

}
