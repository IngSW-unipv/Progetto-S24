package it.unipv.sfw.view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.unipv.sfw.model.request.Request;

public class WhPopUpShowRequestView {
	
	private JFrame frame;
	
	private JPanel mex;
	
	private JLabel text,
							  dataLabel;
	
	public WhPopUpShowRequestView(Set<Request> request){
		
		frame = new JFrame();
		frame.setTitle("ALL REQUEST");
		frame.setSize(600,400);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mex = new JPanel();
		mex.setLayout(new BoxLayout(mex, BoxLayout.Y_AXIS));
		
		text = new JLabel("TOTAL REQUEST: ");
		dataLabel = new JLabel();
		dataLabel.setText(""+request.size());
		
		mex.add(text);
		mex.add(dataLabel);
		
		
		// CREAZIONE DELLA TABELLA 
		String[] columns = { "ID", "DESCRIPTION", "ID STAFF", "ID COMPONENT", "ID VEHICLE"};
		
		Object[][] data =  new Object[request.size()][4];
		
		int row = 0;
		
		for(Request r : request) {
			data[row][0] = r.getId_r();
			data[row][1] = r.getDescription();
			data[row][2] = r.getId_s();
			data[row][3] = r.getId_c();
			data[row][4] = r.getId_v();
			row++;
		}
		
		JTable table = new JTable(new DefaultTableModel(data, columns));
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		frame.add(mex, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	
	
}

