package warehouse;

import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

public class ReleaseDialog extends PopupDialog {
	private static final long serialVersionUID = -5702112735084889000L;

	public ReleaseDialog() {
		inpTextField = new JTextField[3];
		initPopupDialog();
		initReleaseFrame();
		this.setTitle("Auslagern");
		this.setModal(true);
		this.setSize(350, 280);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initReleaseFrame() {
		okayBtn = new JButton("Auslagern bestätigen");
		okayBtn.setBounds(75, 205, 200, 40);
		panel.add(okayBtn);
		okayBtn.addActionListener(this);

		infoLabel.setBounds(110, 170, 250, 30);
		//panel.add(infoLabel);
		
		beschreibbutton = new JButton("nach Bezeichnung suchen");
		beschreibbutton.setBounds(75, 35, 200, 30);
		panel.add(beschreibbutton);
		beschreibbutton.addActionListener(this);
		
		
        
		teilnummerbutton = new JButton ("nach Teilenummer suchen");
        teilnummerbutton.setBounds(75, 75, 200 , 30);
        panel.add(teilnummerbutton);
        teilnummerbutton.addActionListener(this);
        
        
        
		cp.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if(source.getSource() == this.beschreibbutton){
			nameLabel.setVisible(true);
			inpTextField[0].setVisible(true);
			teilnummerbutton.setVisible(false);
			beschreibbutton.setVisible(false);
           /* if (!(inpTextField[0].getText().equals(""))) {
            	LinkedList<Part> searchedParts = TransportVehicle.findPartName(inpTextField[0].getText());
				for (Part parts : searchedParts) 
					System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(parts.getPartnumber())));
            }*/
		}
		if(source.getSource() == this.teilnummerbutton){
			itemNrLabel.setVisible(true);
			inpTextField[1].setVisible(true);
			beschreibbutton.setVisible(false);
			teilnummerbutton.setVisible(false);
           /* if (!inpTextField[1].getText().equals(""))
            	System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(Integer.parseInt(inpTextField[1].getText()))));
				}*/
		}
		if(source.getSource() == this.okayBtn){
			if (!(inpTextField[0].getText().equals(""))) {
				LinkedList<Part> searchedParts = TransportVehicle.findPartName(inpTextField[0].getText());
					for (Part parts : searchedParts){ 
						System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(parts.getPartnumber())));
					}
			}
			if (!inpTextField[1].getText().equals("")){
         	System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(Integer.parseInt(inpTextField[1].getText()))));
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent source) {
		if (source.getKeyCode() == KeyEvent.VK_TAB) {
			for (int i = 0; i < 3; i++) {
				if (source.getSource().equals(inpTextField[i])) {
					if (i == 2) {
						inpTextField[0].requestFocus();
						break;
					}
					inpTextField[i + 1].requestFocus();
				}
			}
		}
	}
}

// Idee aus http://www.coderanch.com/t/379737/java/java/catching-TAB-key-event
// übernommen