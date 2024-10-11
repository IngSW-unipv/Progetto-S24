package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WhPopUpUpdateComponentView {

	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel mainContainer, titlePanel, dataPanel, sendPanel;

	private JLabel textLabel, mexLabel;

	// inserimento dati
	private JTextField  id_c, wear, status;

	// pannelli per non far espandere i bottoni nelle celle
	private JPanel cellPanel1, cellPanel2, cellPanel3, cellPanel4;

	// bottoni per l'interazione: 1
	private JButton sendButton;

	public WhPopUpUpdateComponentView() {

		frame = new JFrame("UPDATE COMPONENT");

		frame.setSize(300, 200);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainContainer = new JPanel();

		mainContainer.setLayout(new BorderLayout());

		/*
		 * CREAZIONE 1 SEZIONE
		 */

		titlePanel = new JPanel();

		titlePanel.setBackground(Color.BLACK);
		titlePanel.setLayout(new BorderLayout());

		textLabel = new JLabel("UPDATE WEAR", SwingConstants.CENTER);

		textLabel.setForeground(Color.WHITE);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setVerticalAlignment(SwingConstants.CENTER);

		titlePanel.add(textLabel, BorderLayout.CENTER);

		/*
		 * CREAZIONE 2 SEZIONE
		 */

		dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		cellPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		dataPanel.setBackground(Color.BLACK);
		dataPanel.setLayout(new GridLayout(1, 3, 10, 10));

		id_c = new JTextField("ID COMPONENT");
		wear = new JTextField("WEAR");
		status = new JTextField("STATUS");

		Dimension dim = new Dimension(100, 100);

		id_c.setPreferredSize(dim);
		wear.setPreferredSize(dim);
		status.setPreferredSize(dim);

		cellPanel1.add(id_c);
		cellPanel2.add(wear);
		cellPanel3.add(status);

		dataPanel.add(cellPanel1);
		dataPanel.add(cellPanel2);
		dataPanel.add(cellPanel3);
		
		/*
		 * CREAZIONE 3 SEZIONE
		 */

		sendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		cellPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		sendPanel.setBackground(Color.BLACK);
		sendPanel.setLayout(new GridLayout(2, 1, 10, 10));

		sendButton = new JButton("SEND");

		sendButton.setPreferredSize(dim);

		cellPanel4.add(sendButton);

		mexLabel = new JLabel();

		mexLabel.setForeground(Color.WHITE);
		mexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mexLabel.setVerticalAlignment(SwingConstants.CENTER);

		sendPanel.add(cellPanel4);
		sendPanel.add(mexLabel, BorderLayout.WEST);

		mainContainer.add(titlePanel);
		mainContainer.add(dataPanel);
		mainContainer.add(sendPanel);

		frame.add(mainContainer);
		
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	
	public void mex1() {
		mexLabel.setText("ERROR");
		mexLabel.setForeground(Color.RED);
	}	
	
	public void mex2() {
		mexLabel.setText("SUCCESS");
		mexLabel.setForeground(Color.GREEN);
	}

	public JPanel getSendPanel() {
		return sendPanel;
	}

	public void setSendPanel(JPanel sendPanel) {
		this.sendPanel = sendPanel;
	}

	public JLabel getMexLabel() {
		return mexLabel;
	}

	public void setMexLabel(JLabel mexLabel) {
		this.mexLabel = mexLabel;
	}

	public JTextField getId_c() {
		return id_c;
	}

	public void setId_c(JTextField id_c) {
		this.id_c = id_c;
	}
	
	public JTextField getWear() {
		return wear;
	}

	public void setWear(JTextField wear) {
		this.wear = wear;
	}
	
	public JTextField getStatus() {
		return status;
	}

	public void setStatus(JTextField status) {
		this.status = status;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}
	
	
}
