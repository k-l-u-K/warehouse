package warehouse;

import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class ReleaseDialog extends PopupDialog {
	private static final long serialVersionUID = -5702112735084889000L;
	private JComboBox<Part> partSelectionComboBox = new JComboBox<Part>();
    private DefaultComboBoxModel<Part> model = new DefaultComboBoxModel<Part>();
    private JButton descriptionBtn;
	private JButton partIDBtn;
	private JButton partSelectionBtn;
	
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
		
		descriptionBtn = new JButton("nach Bezeichnung suchen");
		descriptionBtn.setBounds(75, 35, 200, 30);
		panel.add(descriptionBtn);
		descriptionBtn.addActionListener(this);
		
		partIDBtn = new JButton ("nach Teilenummer suchen");
        partIDBtn.setBounds(75, 75, 200 , 30);
        panel.add(partIDBtn);
        partIDBtn.addActionListener(this);
       		
		partSelectionComboBox.setBounds(10, 80, 320, 30);
		panel.add(partSelectionComboBox);
		partSelectionComboBox.setVisible(false);
        
		partSelectionBtn = new JButton("Teile suchen");
		partSelectionBtn.setBounds(75, 205, 200, 40);
		panel.add(partSelectionBtn);
		partSelectionBtn.addActionListener(this);
		partSelectionBtn.setVisible(false);
        
		cp.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource().equals(descriptionBtn)){
			nameLabel.setVisible(true);
			inpTextField[0].setVisible(true);
			partIDBtn.setVisible(false);
			descriptionBtn.setVisible(false);
			partSelectionBtn.setVisible(true);
           /* if (!(inpTextField[0].getText().equals(""))) {
            	LinkedList<Part> searchedParts = Warehouse.findPartName(inpTextField[0].getText());
				for (Part parts : searchedParts) 
					System.out.println(Warehouse.teilAuslagern(Warehouse.findPartID(parts.getPartnumber())));
            }*/
		}
		
		if (source.getSource().equals(partIDBtn)){
			itemNrLabel.setVisible(true);
			inpTextField[1].setVisible(true);
			descriptionBtn.setVisible(false);
			partIDBtn.setVisible(false);
			okayBtn.setVisible(true);
           /* if (!inpTextField[1].getText().equals(""))
            	System.out.println(Warehouse.teilAuslagern(Warehouse.findPartID(Integer.parseInt(inpTextField[1].getText()))));
				}*/
		}
		
		if (source.getSource().equals(partSelectionBtn)) {
			if (inpTextField[0].getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Es wurde keine Beschreibung eingegeben.");
				return;
			} 
			
			try {
				LinkedList<Part> searchedParts = Warehouse.findPartName(inpTextField[0].getText());
				for (Part parts : searchedParts)
					model.addElement(parts);
				
				inpTextField[0].setEnabled(false);
				partSelectionBtn.setVisible(false);				
				partSelectionComboBox.setModel(model);
				partSelectionComboBox.setVisible(true);
				okayBtn.setVisible(true);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(this,"Zu dieser Beschreibung konnte\nkein Teil im Lager gefunden werden.");
				inpTextField[0].setText("");
			}
		}
		
		
		if (source.getSource().equals(okayBtn)){
			// Auslagern via Beschreibung
			if (!(inpTextField[0].getText().isEmpty())) {
				Warehouse.outsourceParts(Warehouse.findPartID(((Part) partSelectionComboBox.getSelectedItem()).getPartnumber()));
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

