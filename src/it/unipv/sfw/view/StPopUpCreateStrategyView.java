package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StPopUpCreateStrategyView {
	   private JFrame frame;
	   private JPanel mainContainer, componentPanel, strategyPanel, mexPanel, buttonDetailsPanel;
	   private JButton detailsButton;
	   private JLabel componentLabel1,componentLabel2, strategyLabel1, strategyLabel2, mexLabel;
	    
	   public StPopUpCreateStrategyView() {
		   // Setup base components
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
	        
	        componentPanel = new JPanel();
	        componentPanel.setPreferredSize(new Dimension(700, 200));
	        componentPanel.setBackground(Color.BLACK);
	        
	        
	        
	   }
}
