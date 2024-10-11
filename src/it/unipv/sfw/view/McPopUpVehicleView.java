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

public class McPopUpVehicleView {
	
	private JFrame frame;
	
	//contenitori delle 3 sezioni +1 main
	private JPanel mainContainer,
							  titlePanel,
							  dataPanel,
							  sendPanel;
	
	private JLabel textLabel,
							  mexLabel;
	
	//inserimento dati
	private JTextField msn,
									id_p;
	
	// pannelli per non far espandere i bottoni nelle celle
	private JPanel cellPanel1,
							  cellPanel2,
							  cellPanel3;
	
	//bottoni  per l'interazione: 1
	private JButton sendButton;
	

	public McPopUpVehicleView() {
		
		frame = new JFrame("ADD VEHICLE");
		
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
		
		textLabel = new JLabel("INSERT VEHICLE", SwingConstants.CENTER);
		
		textLabel.setForeground(Color.WHITE);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(textLabel, BorderLayout.CENTER);
        
        /*
         * CREAZIONE 2 SEZIONE
         */
        
		dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
		cellPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		dataPanel.setBackground(Color.BLACK);
		dataPanel.setLayout(new GridLayout(1,2,10,10));
		
		msn = new JTextField("MSN");
		id_p = new JTextField("ID PILOT");
		
		Dimension dim = new Dimension(100,100);
		
		msn.setPreferredSize(dim);
		id_p.setPreferredSize(dim);
		
		cellPanel1.add(msn);
		cellPanel2.add(id_p);
		
		dataPanel.add(cellPanel1);
		dataPanel.add(cellPanel2);
		
		/*
         * CREAZIONE 3 SEZIONE
         */
		
		sendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
		cellPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		
		sendPanel.setBackground(Color.BLACK);
		sendPanel.setLayout(new GridLayout(2,1,10,10));
		
		sendButton = new JButton("INSERT");
		
		sendButton.setPreferredSize(dim);
		
		cellPanel3.add(sendButton);
		
		mexLabel = new JLabel();
		
		mexLabel.setForeground(Color.WHITE);
		mexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mexLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        sendPanel.add(cellPanel3);
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
	
	public void mex1() {
		mexLabel.setText("WRONG INSERTION");
		mexLabel.setForeground(Color.RED);
	}
	
	public void mex2() {
		mexLabel.setText("CORRECT INSERTION");
		mexLabel.setForeground(Color.GREEN);
	}
	
	
	public JTextField getMsn() {
		return msn;
	}

	public void setMsn(JTextField msn) {
		this.msn = msn;
	}

	public JTextField getId_p() {
		return id_p;
	}

	public void setId_p(JTextField id_p) {
		this.id_p = id_p;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}
	
}
