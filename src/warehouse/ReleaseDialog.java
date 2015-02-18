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
		this.setSize(350, 185);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

	// Initialisieren des Auslagernfensters
	private void initReleaseFrame() {
		// Überschreibung der Position der Teile-ID Eingabe
		itemNrLabel.setBounds(20, 20, 150, 30);
		inpTextField[1].setBounds(140, 25, 100, 20);
		
		descriptionBtn = new JButton("nach Bezeichnung suchen");
		descriptionBtn.setBounds(75, 35, 200, 30);
		panel.add(descriptionBtn);
		descriptionBtn.addActionListener(this);
		
		partIDBtn = new JButton ("nach Teilenummer suchen");
        partIDBtn.setBounds(75, 75, 200 , 30);
        panel.add(partIDBtn);
        partIDBtn.addActionListener(this);
        
        // Button, zum Suchen der Teile nach eingegebener Bezeichnung
		partSelectionBtn = new JButton("Teile suchen");
		partSelectionBtn.setBounds(75, 105, 200, 40);
		panel.add(partSelectionBtn);
		partSelectionBtn.addActionListener(this);
		partSelectionBtn.setVisible(false);
       	
        // ComboBox - enthält alle passenden Teile bei der Suche nach Bezeichnung
		partSelectionComboBox.setBounds(10, 60, 320, 30);
		panel.add(partSelectionComboBox);
		partSelectionComboBox.setVisible(false);
        
		okayBtn = new JButton("Auslagern bestätigen");
		okayBtn.setBounds(75, 105, 200, 40);
		panel.add(okayBtn);
		okayBtn.addActionListener(this);
		okayBtn.setVisible(false);
        
		cp.add(panel);
	}

	// Aktionen
	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource().equals(descriptionBtn)){
			nameLabel.setVisible(true);
			inpTextField[0].setVisible(true);
			partIDBtn.setVisible(false);
			descriptionBtn.setVisible(false);
			partSelectionBtn.setVisible(true);
		}
		
		if (source.getSource().equals(partIDBtn)){
			itemNrLabel.setVisible(true);
			inpTextField[1].setVisible(true);
			descriptionBtn.setVisible(false);
			partIDBtn.setVisible(false);
			okayBtn.setVisible(true);
		}
		
		// Auswahl nach Beschreibung zu suchen - Drücken des Teile suchen Buttons
		if (source.getSource().equals(partSelectionBtn)) {
			if (inpTextField[0].getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Es wurde keine Beschreibung eingegeben.");
				return;
			}
			try {
				// Anlegen einer Liste mit allen Teilen, die zur eingegebenen Beschreibung zu finden sind. Diese Liste wird dann der Inhalt der ComboBox.
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
				return;
			}
		}

		// Okay-Button
		if (source.getSource().equals(okayBtn)){
			// Auslagern via Beschreibung
			if (!(inpTextField[0].getText().isEmpty())) {
				Warehouse.outsourceParts(Warehouse.findPart(null, ((Part) partSelectionComboBox.getSelectedItem()).getPartnumber()));
				JOptionPane.showMessageDialog(this,"Das gewünschte Teil wurde erfolgreich ausgelagert.");
				this.setVisible(false);
				return;
			}
			
			// Auslagern via Teilenummer
			// Keine Teilenummer eingegeben.
			if (inpTextField[1].getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,"Es wurde keine Teilenummer eingegeben.");
				return;
			}
			try {
				// Teile-ID auslesen
				Part partID = Warehouse.findPart(null, Integer.parseInt(inpTextField[1].getText()));
				// Fehler, dass keine entsprechende Teilenummer gefunden wurde.
				if (partID == null) {
					JOptionPane.showMessageDialog(this,"Zu dieser Teilenummer konnte\nkein Teil im Lager gefunden werden.");
					return;
				}
				Warehouse.outsourceParts(partID);
				JOptionPane.showMessageDialog(this,"Das gewünschte Teil wurde erfolgreich ausgelagert.");
				this.setVisible(false);				
			} catch (NumberFormatException a) {
				JOptionPane.showMessageDialog(this,"Die eingegebene Teilenummer darf nur aus Ziffern bestehen.");
			} finally {
				inpTextField[1].setText("");				
			}
		}
	}
}
