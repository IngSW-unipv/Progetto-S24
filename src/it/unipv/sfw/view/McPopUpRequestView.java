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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class McPopUpRequestView{

		private JFrame frame;
		
		//contenitori delle 3 sezioni +1 main
		private JPanel mainContainer,
								  titlePanel,
								  dataPanel,
								  sendPanel;
		
		private JLabel textLabel,
								  mexLabel;
		
		// inserimento dati
		private JTextField id_s,
										id_c,
										id_v;
		
		// pannelli per non far espandere i bottoni nelle celle
		private JPanel cellPanel1,
								  cellPanel2,
								  cellPanel3,
								  cellPanel4;
		
		//  area per descrivere la richiesta
		private JTextArea desc;
		
		// bottoni  per l'interazione: 1
		private JButton sendButton;
		
		

		public McPopUpRequestView() {
			
			frame = new JFrame("INSERT REQUEST");
			
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
			
			textLabel = new JLabel("REQUEST", SwingConstants.CENTER);
			
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
			cellPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			dataPanel.setBackground(Color.BLACK);
			dataPanel.setLayout(new GridLayout(2,3,10,10));
			
			id_s = new JTextField("ID STAFF");
			id_c = new JTextField("ID COMPONENT");
			id_v = new JTextField("ID VEHICLE");
			
			desc = new JTextArea("DESCRIPTION...");
			
			Dimension dim = new Dimension(100,100);
			
			id_s.setPreferredSize(dim);
			id_c.setPreferredSize(dim);
			id_v.setPreferredSize(dim);
			
			cellPanel1.add(id_s);
			cellPanel2.add(id_c);
			cellPanel3.add(id_v);
			
			dataPanel.add(cellPanel1);
			dataPanel.add(cellPanel2);
			dataPanel.add(cellPanel3);
			
			/*
	         * CREAZIONE 3 SEZIONE
	         */
			
			sendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
			cellPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			sendPanel.setBackground(Color.BLACK);
			sendPanel.setLayout(new GridLayout(2,1,10,10));
			
			sendButton = new JButton("INSERT");
			
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

		public JTextField getId_s() {
			return id_s;
		}


		public void setId_s(JTextField id_s) {
			this.id_s = id_s;
		}


		public JTextField getId_c() {
			return id_c;
		}


		public void setId_c(JTextField id_c) {
			this.id_c = id_c;
		}


		public JTextField getId_v() {
			return id_v;
		}


		public void setId_v(JTextField id_v) {
			this.id_v = id_v;
		}


		public JTextArea getDesc() {
			return desc;
		}


		public void setDesc(JTextArea desc) {
			this.desc = desc;
		}


		public JButton getSendButton() {
			return sendButton;
		}


		public void setSendButton(JButton sendButton) {
			this.sendButton = sendButton;
		}

}
