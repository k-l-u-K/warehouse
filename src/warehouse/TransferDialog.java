package warehouse;

import java.awt.event.*;

import javax.swing.*;

public class TransferDialog extends PopupDialog {
	private static final long serialVersionUID = -6861367256789342389L;

	private JLabel itemSizeLabel = new JLabel("Größe in GE: *");

	public TransferDialog() {
		inpTextField = new JTextField[4];
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

		inpTextField[3] = new JTextField("");
		inpTextField[3].setFocusTraversalKeysEnabled(false);
		inpTextField[3].addKeyListener(this);
		inpTextField[3].setBounds(140, 145, 100, 20);
		panel.add(inpTextField[3]);

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
		System.out.print("gesendet");
	}

	@Override
	public void keyPressed(KeyEvent source) {
		if (source.getKeyCode() == KeyEvent.VK_TAB) {
			for (int i = 0; i < 4; i++) {
				if (source.getSource().equals(inpTextField[i])) {
					if (i == 3) {
						inpTextField[0].requestFocus();
						break;
					}
					inpTextField[i + 1].requestFocus();
				}
			}
		}
	}

}
