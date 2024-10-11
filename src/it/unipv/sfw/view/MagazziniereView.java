package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unipv.sfw.model.staff.Session;

public class MagazziniereView extends AbsView{

	private JFrame frame;
	
	private JPanel mainContainer,
							  titlePanel, //decidere un titolo o una immagine
							  popUpPanel,
							  mexPanel;
	
	private JPanel cellPanel1,
							  cellPanel2,
							  cellPanel3,
							  cellPanel4;
	
	private JButton showRequestButton,
								deleteRequestButton,
								updateCompoButton;
	
	private JComboBox<String> comboBox; 
	
	private JLabel mex;
	
	public MagazziniereView() {
		
		frame  = new JFrame("WAREHOUSEMAN");
		frame.setSize(800, 600);
		frame.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainContainer = new JPanel();

		mainContainer.setLayout(new BorderLayout());
		
		/*
		 * CREAZIONE 1 SEZIONE : TITOLO
		 */
		titlePanel = new JPanel();
		
		/*
		 * CREAZIONE 2 SEZIONE: 4 BOTTONI E FINESTRE POP UP
		 */
		popUpPanel = new JPanel ();
		cellPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cellPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		String option[] = {"RUOTA ANTERIORE SX HARD", "RUOTA ANTERIORE DX HARD", 
									  "RUOTA POSTERIORE SX HARD", "RUOTA POSTERIORE DX HARD",
									  
									  "RUOTA ANTERIORE SX MEDIUM", "RUOTA ANTERIORE DX MEDIUM", 
									  "RUOTA POSTERIORE SX MEDIUM", "RUOTA POSTERIORE DX MEDIUM",
									  
									  "ALA ANTERIORE", "DRS","MOTORE TERMICO","ERS", "- ALL"};
		
		comboBox = new JComboBox<>(option);
		
		// hgap = 10, vgap = 10, horizontal e vertical
		popUpPanel.setBackground(Color.BLACK);
		popUpPanel.setLayout(new GridLayout(2, 2, 10, 10));
		
		showRequestButton = new JButton("SHOW REQUESTS");
		deleteRequestButton = new JButton("DELETE REQUEST");
		updateCompoButton = new JButton("UPDATE COMPONENT");
		
		Dimension dim = new Dimension(100, 100);

		showRequestButton.setPreferredSize(dim);
		deleteRequestButton.setPreferredSize(dim);
		updateCompoButton.setPreferredSize(dim);
		comboBox.setPreferredSize(dim);
	
		cellPanel1.add(showRequestButton);
		cellPanel2.add(deleteRequestButton);
		cellPanel3.add(updateCompoButton);
		cellPanel4.add(comboBox);
		
		popUpPanel.add(cellPanel1);
		popUpPanel.add(cellPanel2);
		popUpPanel.add(cellPanel3);
		popUpPanel.add(cellPanel4);
		
		/*
		 * CREAZIONE 3 SEZIONE: LABEL PER MEX
		 */
		
		mexPanel = new JPanel();
		mex = new JLabel();
		
		mexPanel.add(mex);
		
		mainContainer.add(titlePanel);
		mainContainer.add(popUpPanel);
		mainContainer.add(mexPanel);
		
		frame.add(mainContainer);
		
	}

	public JButton getShowRequestButton() {
		return showRequestButton;
	}

	public void setShowRequestButton(JButton showRequestButton) {
		this.showRequestButton = showRequestButton;
	}

	public JButton getDeleteRequestButton() {
		return deleteRequestButton;
	}

	public void setDeleteRequestButton(JButton deleteRequestButton) {
		this.deleteRequestButton = deleteRequestButton;
	}

	public JButton getUpdateCompoButton() {
		return updateCompoButton;
	}

	public void setUpdateCompoButton(JButton updateCompoButton) {
		this.updateCompoButton = updateCompoButton;
	}

	public JComboBox<String> getCombobox() {
		return comboBox;
	}

	public void setCombobox(JComboBox<String> combobox) {
		this.comboBox = combobox;
	}
	
	public JLabel getMex() {
		return mex;
	}

	public void setMex(JLabel mex) {
		this.mex.setText("REQUEST TOTAL: " + Session.getIstance().getId_pilot());
	}
	
	
	
}
