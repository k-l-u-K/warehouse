package warehouse;

import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

public class ReleaseDialog extends PopupDialog {
	private static final long serialVersionUID = -5702112735084889000L;
	private JComboBox<Part> comboboxTeile = new JComboBox<Part>();
    private DefaultComboBoxModel<Part> model = new DefaultComboBoxModel<Part>();
	public ReleaseDialog() {
		inpTextField = new JTextField[2];
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
		okayBtn.setVisible(false);

		// infoLabel.setBounds(110, 170, 250, 30);
		//panel.add(infoLabel);
		
		beschreibbutton = new JButton("nach Bezeichnung suchen");
		beschreibbutton.setBounds(75, 35, 200, 30);
		panel.add(beschreibbutton);
		beschreibbutton.addActionListener(this);
		
		teilnummerbutton = new JButton ("nach Teilenummer suchen");
        teilnummerbutton.setBounds(75, 75, 200 , 30);
        panel.add(teilnummerbutton);
        teilnummerbutton.addActionListener(this);
       		
		comboboxTeile.setBounds(10, 80, 320, 30);
		panel.add(comboboxTeile);
		comboboxTeile.setVisible(false);
        
		auswahlBtn = new JButton("Teile suchen");
		auswahlBtn.setBounds(75, 205, 200, 40);
		panel.add(auswahlBtn);
		auswahlBtn.addActionListener(this);
		auswahlBtn.setVisible(false);
        
		cp.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource().equals(beschreibbutton)){
			nameLabel.setVisible(true);
			inpTextField[0].setVisible(true);
			teilnummerbutton.setVisible(false);
			beschreibbutton.setVisible(false);
			auswahlBtn.setVisible(true);
           /* if (!(inpTextField[0].getText().equals(""))) {
            	LinkedList<Part> searchedParts = Warehouse.findPartName(inpTextField[0].getText());
				for (Part parts : searchedParts) 
					System.out.println(Warehouse.teilAuslagern(Warehouse.findPartID(parts.getPartnumber())));
            }*/
		}
		
		if (source.getSource().equals(teilnummerbutton)){
			itemNrLabel.setVisible(true);
			inpTextField[1].setVisible(true);
			beschreibbutton.setVisible(false);
			teilnummerbutton.setVisible(false);
			okayBtn.setVisible(true);
           /* if (!inpTextField[1].getText().equals(""))
            	System.out.println(Warehouse.teilAuslagern(Warehouse.findPartID(Integer.parseInt(inpTextField[1].getText()))));
				}*/
		}
		
		if (source.getSource().equals(auswahlBtn)) {
			if (inpTextField[0].getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Es wurde keine Beschreibung eingegeben.");
				return;
			} 
			
			try {
				LinkedList<Part> searchedParts = Warehouse.findPartName(inpTextField[0].getText());
				for (Part parts : searchedParts)
					model.addElement(parts);
				
				inpTextField[0].setEnabled(false);
				auswahlBtn.setVisible(false);				
				comboboxTeile.setModel(model);
				comboboxTeile.setVisible(true);
				okayBtn.setVisible(true);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(this,"Zu dieser Beschreibung konnte\nkein Teil im Lager gefunden werden.");
				inpTextField[0].setText("");
			}
		}
		
		
		if (source.getSource().equals(okayBtn)){
			// Auslagern via Beschreibung
			if (!(inpTextField[0].getText().isEmpty())) {
				Warehouse.outsourceParts(Warehouse.findPartID(((Part) comboboxTeile.getSelectedItem()).getPartnumber()));
				this.setVisible(false);
				return;
			}
			
			// Auslagern via Teilenummer
			if (inpTextField[1].getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Es wurde keine Teilenummer eingegeben.");
				return;			
			} 
			
			try {
				Warehouse.outsourceParts(Warehouse.findPartID(Integer.parseInt(inpTextField[1].getText())));
				this.setVisible(false);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(this,"Zu dieser Teilenummer konnte\nkein Teil im Lager gefunden werden.");				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,"Die eingegebene Teilenummer darf nur aus Ziffern bestehen.");
			} finally {
				inpTextField[1].setText("");				
			}
		}
	}
}

