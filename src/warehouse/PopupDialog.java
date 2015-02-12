package warehouse;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class PopupDialog extends JDialog implements ActionListener, KeyListener {
	private static final long serialVersionUID = 6795242289802898275L;

	protected Container cp;
	protected JPanel panel;

	protected JButton okayBtn;
	protected JButton beschreibbutton;
	protected JButton teilnummerbutton;
	protected JButton auswahlBtn;
	protected JLabel nameLabel = new JLabel("Bezeichnung: *");
	protected JLabel itemNrLabel = new JLabel("Teilenummer: ");
	protected JLabel infoLabel = new JLabel(
			"Pflichtfelder sind mit * gekennzeichnet.");
	protected JTextField[] inpTextField;
	
	//protected JComboBox teilnummerbox = new JComboBox(....toString());
	//protected JComboBox nameBox = new JComboBox(""); 

	protected void initPopupDialog() {
		cp = this.getContentPane();

		panel = new JPanel();
		panel.setLayout(null);

		nameLabel.setBounds(20, 20, 150, 30);
		panel.add(nameLabel);
		nameLabel.setVisible(false);

		itemNrLabel.setBounds(20, 60, 150, 30);
		panel.add(itemNrLabel);
		itemNrLabel.setVisible(false);

//		itemCountLabel.setBounds(20, 100, 150, 30);
//		panel.add(itemCountLabel);

		for (int i = 0; i < 2; i++) {
			inpTextField[i] = new JTextField("");
			inpTextField[i].setFocusTraversalKeysEnabled(false);
			inpTextField[i].addKeyListener(this);
			inpTextField[i].setDisabledTextColor(Color.black);			
		}

		inpTextField[0].setBounds(140, 25, 170, 20);
		panel.add(inpTextField[0]);
		inpTextField[0].setVisible(false);

		inpTextField[1].setBounds(140, 65, 100, 20);
		panel.add(inpTextField[1]);
		inpTextField[1].setVisible(false);

//		inpTextField[2].setBounds(140, 105, 100, 20);
//		panel.add(inpTextField[2]);
	}
	
	@Override
	public void keyPressed(KeyEvent source) {
	}
	
	@Override
	public void keyReleased(KeyEvent source) {
	}

	@Override
	public void keyTyped(KeyEvent source) {
	}
}
