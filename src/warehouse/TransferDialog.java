package warehouse;

import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class TransferDialog extends PopupDialog {
	private static final long serialVersionUID = -6861367256789342389L;

	private JLabel itemSizeLabel = new JLabel("Größe in GE: *");

	public TransferDialog() {
		inpTextField = new JTextField[3];
		initPopupDialog();
		initTransferFrame();
		this.setTitle("Einlagern");
		this.setModal(true);
		this.setSize(350, 330);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void initTransferFrame() {
		itemSizeLabel.setBounds(20, 140, 150, 30);
		panel.add(itemSizeLabel);

		inpTextField[2] = new JTextField("");
		inpTextField[2].setFocusTraversalKeysEnabled(false);
		inpTextField[2].addKeyListener(this);
		inpTextField[2].setBounds(140, 145, 100, 20);
		panel.add(inpTextField[2]);

		okayBtn = new JButton("Einlagern bestätigen");
		okayBtn.setBounds(75, 230, 200, 40);
		panel.add(okayBtn);
		okayBtn.addActionListener(this);

		infoLabel.setBounds(110, 180, 250, 30);
		panel.add(infoLabel);

		cp.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		// System.out.print("gesendet");
		// Konstruktor erwartet folgende Paramter:
		// String, int, int, int
		// Name, Nummer, Menge, Größe
		// Daher muss die Eingabe auf Zahlen beschränkt werden, wo nur Zahlen
		// sinnvoll sind

		Part part = new Part(inpTextField[0].getText(),
			Integer.parseInt(inpTextField[1].getText()),
			Integer.parseInt(inpTextField[2].getText()));

		// Compartment compartment =
		// Warehouse.get().regale[0].compartments[0][0];
		// Compartment compartment = TransportVehicle.findCompartment(part);

		TransportVehicle.teilEinlagern(part, TransportVehicle.findCompartment(part));

		// Zufallserzeugung
		// Random zufall = new Random();
		// for (int a = 0; a < 500; a++) {
		// new TransportVehicle().teilEinlagern(new Part("Teil " + a + 1,
		// a + 1, zufall.nextInt(10)));
		// }
		
		
		// schnellere Erzeugung von Items
		// this.setVisible(false);

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
