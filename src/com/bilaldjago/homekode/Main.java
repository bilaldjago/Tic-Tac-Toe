package com.bilaldjago.homekode;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.bilaldjago.homekode.gui.MainGUI;


public class Main {
	
	public static void main(String[] args) {
		
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(()-> {
			new MainGUI().setVisible(true);
		});
		
	}

}
