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
		okayBtn.setBounds(75, 180, 200, 40);
		panel.add(okayBtn);
		okayBtn.addActionListener(this);

		infoLabel.setBounds(110, 140, 250, 30);
		panel.add(infoLabel);

		cp.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if (!(inpTextField[0].getText().equals(""))) {
			//String test = inpTextField[0].getText();
			String test = "22";
			LinkedList<Part> saerchedParts = TransportVehicle.findPartName(test);
			for (Part parts : saerchedParts) {
				System.out.println(parts);
				System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(parts.getPartnumber())));
			}
		}
		if (!inpTextField[1].getText().equals("")) {
			//inpTextField[0].setText("");
			System.out.println("Mache was");
			System.out.println(TransportVehicle.teilAuslagern(TransportVehicle.findPartID(Integer.parseInt(inpTextField[1].getText()))));
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