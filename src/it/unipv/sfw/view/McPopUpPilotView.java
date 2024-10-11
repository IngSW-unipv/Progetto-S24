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

public class McPopUpPilotView {

	private JFrame frame;
	
	//contenitori delle 3 sezioni +1 main
	private JPanel mainContainer,
							  titlePanel,
							  dataPanel,
							  sendPanel;
	
	private JLabel textLabel,
							  mexLabel;
	
	//inserimento dati
	private JTextField name,
									surname,
									number;
	
	//bottoni  per l'interazione: 1
	private JButton sendButton;
	

	public McPopUpPilotView() {
		
		frame = new JFrame("ADD PILOT");
		
		frame.setSize(300, 200);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainContainer = new JPanel();

		mainContainer.setLayout(new BorderLayout());	
		
        /*
         * CREAZIONE 1 SEZIONE
         */
        
		titlePanel = new JPanel();
		
		titlePanel.setBackground(Color.BLACK);
		titlePanel.setLayout(new BorderLayout());
		
		textLabel = new JLabel("ADD DATA PILOT", SwingConstants.CENTER);
		
		textLabel.setForeground(Color.WHITE);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(textLabel, BorderLayout.CENTER);
        
        /*
         * CREAZIONE 2 SEZIONE
         */
        
		dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
		
		dataPanel.setBackground(Color.BLACK);
		dataPanel.setLayout(new GridLayout(1,3,10,10));
		
		name = new JTextField("NAME");
		surname = new JTextField("SURNAME");
		number = new JTextField("NUMBER");
		
		Dimension dim = new Dimension(100,100);
		
		name.setPreferredSize(dim);
		surname.setPreferredSize(dim);
		number.setPreferredSize(dim);
		
		dataPanel.add(name);
		dataPanel.add(surname);
		dataPanel.add(number);
		
		/*
         * CREAZIONE 3 SEZIONE
         */
		
		sendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
		
		sendPanel.setBackground(Color.BLACK);
		sendPanel.setLayout(new GridLayout(2,1,10,10));
		
		sendButton = new JButton("INSERT");
		
		sendButton.setPreferredSize(dim);
		
		mexLabel = new JLabel();
		
		mexLabel.setForeground(Color.WHITE);
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
		surname.setEnabled(false);
		surname.setVisible(false);
	}
	
	public JTextField getName() {
		return name;
	}


	public void setName() {
		this.name.setText("Insert msn");
	}


	public JTextField getSurname() {
		return surname;
	}


	public void setSurname(JTextField surname) {
		this.surname = surname;
	}


	public JTextField getNumber() {
		return number;
	}


	public void setNumber() {
		this.number.setText("Insert id pilot");
	}


	public JButton getSendButton() {
		return sendButton;
	}


	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}
	
	
}
