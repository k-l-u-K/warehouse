package warehouse;

import java.awt.event.*;

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
<<<<<<< HEAD
		// System.out.print("gesendet");
		// Konstruktor erwartet folgende Paramter:
		// String, int, int, int
		// Name, Nummer, Menge, Größe
		// Daher muss die Eingabe auf Zahlen beschränkt werden, wo nur Zahlen
		// sinnvoll sind
		
		new TransportVehicle().teilEinlagern(new Part(inpTextField[0].getText(),
				Integer.parseInt(inpTextField[1].getText()),
				Integer.parseInt(inpTextField[2].getText())));
		new TransportVehicle().teileAnzeigen();
		
		this.setVisible(false);
=======
		//System.out.print("gesendet");
		System.out.print(inpTextField[0].getText());
		//Konstruktor erwartet folgende Paramter:
		//String, int, int, int
		//Name, Nummer, Menge, Größe
		//Daher muss die Eingabe auf Zahlen beschränkt werden, wo nur Zahlen sinnvoll sind 		
		//Part part = new Part(inpTextField[0].getText(), Integer.parseInt(inpTextField[1].getText()), Integer.parseInt(inpTextField[2].getText()), Integer.parseInt(inpTextField[4].getText()));
		Warehouse.setCompartment(inpTextField[0].getText(), 12, 1, 3);
>>>>>>> origin/master
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
