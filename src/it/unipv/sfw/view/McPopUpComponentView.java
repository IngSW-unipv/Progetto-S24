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

public class McPopUpComponentView extends JFrame {

	private JFrame frame;

	// contenitori delle 3 sezioni +1 main
	private JPanel mainContainer, titlePanel, dataPanel, sendPanel;

	private JLabel textLabel, mexLabel;

	// inserimento dati
	private JTextField insertID_C, insertID_V, nameC, statusC;

	// bottoni per l'interazione: 1
	private JButton sendButton;

	public McPopUpComponentView() {

		frame = new JFrame("ADD COMPONENT");

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

		textLabel = new JLabel("ADD DATA COMPONENT", SwingConstants.CENTER);

		textLabel.setForeground(Color.WHITE);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setVerticalAlignment(SwingConstants.CENTER);

		titlePanel.add(textLabel, BorderLayout.CENTER);

		/*
		 * CREAZIONE 2 SEZIONE
		 */

		dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

		dataPanel.setBackground(Color.BLACK);
		dataPanel.setLayout(new GridLayout(2, 2, 10, 10));

		insertID_C = new JTextField("insert id component");
		insertID_V = new JTextField("insert id vehicle");
		nameC = new JTextField("insert name component");
		statusC = new JTextField("insert status of component");

		Dimension dim = new Dimension(100, 100);

		insertID_C.setPreferredSize(dim);
		insertID_V.setPreferredSize(dim);
		nameC.setPreferredSize(dim);
		statusC.setPreferredSize(dim);

		dataPanel.add(insertID_C);
		dataPanel.add(insertID_V);
		dataPanel.add(nameC);
		dataPanel.add(statusC);

		/*
		 * CREAZIONE 3 SEZIONE
		 */

		sendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

		sendPanel.setBackground(Color.BLACK);
		sendPanel.setLayout(new GridLayout(2, 1, 10, 10));

		sendButton = new JButton("INSERT");

		sendButton.setPreferredSize(dim);

		mexLabel = new JLabel();

		mexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mexLabel.setVerticalAlignment(SwingConstants.CENTER);

		sendPanel.add(sendButton);
		sendPanel.add(mexLabel, BorderLayout.WEST);

		mainContainer.add(titlePanel);
		mainContainer.add(dataPanel);
		mainContainer.add(sendPanel);

		frame.add(mainContainer);

	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}

	public void hideField() {
		statusC.setEnabled(false);
		statusC.setVisible(false);

	}

	public void mex() {
		mexLabel.setText("INVALID ERROR");
		mexLabel.setForeground(Color.RED);
	}

	public void mex1() {
		mexLabel.setText("COMPONENT ALREADY INSERTED");
		mexLabel.setForeground(Color.RED);
	}

	public void mex2() {
		mexLabel.setText("COMPONENT SUCCESSFULLY  INSERTED");
		mexLabel.setForeground(Color.GREEN);
	}

	public JTextField getInsertID_C() {
		return insertID_C;
	}

	public void setInsertID_C(JTextField insertID_C) {
		this.insertID_C = insertID_C;
	}

	public JTextField getInsertID_V() {
		return insertID_V;
	}

	public void setInsertID_V(JTextField insertID_V) {
		this.insertID_V = insertID_V;
	}

	public JTextField getNameC() {
		return nameC;
	}

	public void setNameC(JTextField nameC) {
		this.nameC = nameC;
	}

	public JTextField getStatusC() {
		return statusC;
	}

	public void setStatusC(JTextField statusC) {
		this.statusC = statusC;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

}
