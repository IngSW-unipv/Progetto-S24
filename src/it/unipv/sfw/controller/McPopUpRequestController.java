package it.unipv.sfw.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unipv.sfw.dao.mysql.MeccanicoDAO;
import it.unipv.sfw.view.McPopUpRequestView;

public class McPopUpRequestController {
	
	private McPopUpRequestView pr; 
	private MeccanicoDAO md;
	
	public McPopUpRequestController() {
		
		pr = new McPopUpRequestView();
		md = new MeccanicoDAO();
		
		pr.getSendButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String idc = pr.getId_c().getText();
				
				int id_c = Integer.parseInt(idc);
				
				md.insertRequest(pr.getDesc().getText(), pr.getId_s().getText(), id_c, pr.getId_v().getText());
 				
			}
		});
	
	}
	
	// Metodo per mostrare la finestra
    public void showWindow() {
        pr.show();
    }
    
    public void clear() {
    	pr.clearComponents(pr.getDataPanel());
    }
    
}
