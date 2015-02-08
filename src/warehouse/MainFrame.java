package warehouse;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7462047233762639130L;

	// Container, Panel
	private Container cp;
	private JPanel tablePanel;
	private JPanel infoPanel;

	// MenuBar
	private JMenuBar menu;
	private JMenu storeInfo;
	private JMenu storeOpt;
	private JMenuItem[] storageRacks = new JMenuItem[9];
	private JMenuItem transferToStock;
	private JMenuItem releaseFromStock;

	// Elemente Table Panel
	private JPanel tableTopPanel;
	private JTable table;
	private String[][] tableContent;
	private JTextField contentInfoLabel = new JTextField(
			"Inhaltsanzeige: Alle Regale");
	int tableLength = 80; // vorläufige Hilfsvariable für Tabellenlänge
	private JButton sortByNameBtn = new JButton("nach Bezeichnung sortieren");
	private JButton sortByPartNumberBtn = new JButton("nach Teilenr. sortieren");

	// Elemente Info Panel
	private JLabel positionTransportSystemLabel = new JLabel(
			"Standort des Fahrzeuges: ");
	private JLabel drivewayLabel = new JLabel("Zurückgelegter Fahrweg: ");
	private JLabel basicUnitLabel = new JLabel(
			"Die Größe eines Faches entspricht 10 Grundeinheiten.");
	private JTextField positionTransportSystemText = new JTextField("x,y,z");
	private JTextArea drivewayText = new JTextArea(
			"Weg in x-Richtung: x\nWeg in y-Richtung: y\nWeg in z-Richtung: z");

	public MainFrame() {
		//dataRead(); - im Main (GUI-Init erst danach)
		initTable();
		initGUI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1150, 750));
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocation(0, 0);
		this.setTitle("Lagerverwaltung");
		this.setVisible(true);
	}

	//private void dataRead() { - outgesourced im Main
	//}

	private void initTable() {
		String[] tableHead = { "Regal", "Fach", "Bezeichnung", "Teilenummer",
				"Anzahl", "Größe" };
		tableContent = new String[tableLength][6];
		table = new JTable(tableContent, tableHead);

		for (int i = 0; i < tableLength; i++) {
			tableContent[i][0] = Integer.toString(1);
			tableContent[i][1] = Integer.toString(2);
			tableContent[i][2] = ("Bezeichnung");
			tableContent[i][3] = Integer.toString(3);
			tableContent[i][4] = Integer.toString(4);
			tableContent[i][5] = Integer.toString(5);
		}

		table.setEnabled(false);

	}

	public void initGUI() {
		initMenu();

		cp = this.getContentPane();

		initTablePanel();
		initInfoPanel();

		cp.setLayout(new GridLayout(1, 2));
		cp.add(tablePanel);
		cp.add(infoPanel);
	}

	private void initMenu() {
		menu = new JMenuBar();

		storeInfo = new JMenu("Lageranzeige");
		storeOpt = new JMenu("Lageroptionen");

		for (int i = 0; i < 9; i++) {
			if (i == 0) {
				storageRacks[i] = new JMenuItem("Alle Regale");
			} else {
				storageRacks[i] = new JMenuItem("Regal " + i);
			}
			storeInfo.add(storageRacks[i]);
			storageRacks[i].addActionListener(this);
		}

		transferToStock = new JMenuItem("Einlagern");
		releaseFromStock = new JMenuItem("Auslagern");
		storeOpt.add(transferToStock);
		storeOpt.add(releaseFromStock);

		transferToStock.addActionListener(this);
		releaseFromStock.addActionListener(this);

		menu.add(storeInfo);
		menu.add(storeOpt);

		this.setJMenuBar(menu);
	}

	private void initTablePanel() {
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());

		tableTopPanel = new JPanel();
		tableTopPanel.setLayout(new GridLayout(1, 3));
		tablePanel.add(tableTopPanel, BorderLayout.NORTH);

		// erzwungener Rahmen
		tablePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20));

		contentInfoLabel.setEditable(false);
		contentInfoLabel.setBackground(tablePanel.getBackground());
		contentInfoLabel.setBorder(null);
		tableTopPanel.add(contentInfoLabel);

		tableTopPanel.add(sortByNameBtn);
		tableTopPanel.add(sortByPartNumberBtn);

		sortByNameBtn.addActionListener(this);
		sortByPartNumberBtn.addActionListener(this);

		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(null);

		positionTransportSystemLabel.setBounds(20, 80, 150, 30);
		infoPanel.add(positionTransportSystemLabel);

		positionTransportSystemText.setBounds(190, 85, 150, 20);
		positionTransportSystemText.setEditable(false);
		positionTransportSystemText.setBackground(infoPanel.getBackground());
		positionTransportSystemText.setBorder(null);
		infoPanel.add(positionTransportSystemText);

		drivewayLabel.setBounds(20, 150, 150, 30);
		infoPanel.add(drivewayLabel);

		drivewayText.setBounds(190, 143, 200, 60);
		drivewayText.setEditable(false);
		drivewayText.setBackground(infoPanel.getBackground());
		infoPanel.add(drivewayText);

		basicUnitLabel.setBounds(20, 20, 350, 40);
		infoPanel.add(basicUnitLabel);
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource() instanceof JButton) {
			if (source.getSource().equals(sortByNameBtn)) {
				sortByNames();
				// nach Namen sortieren
			} 
			
			if (source.getSource().equals(sortByPartNumberBtn)) {
				sortByPartNumber();
				// nach Nummer sortieren
			}
			table.repaint();
		} else {
			if (source.getSource().equals(transferToStock)) {
				new TransferDialog();
			}

			if (source.getSource().equals(releaseFromStock)) {
				new ReleaseDialog();
			} else {
				for (int i = 0; i < 9; i++) {
					if (source.getSource().equals(storageRacks[i])) {
						contentInfoLabel.setText("Inhaltsanzeige: "
								+ storageRacks[i].getText());
						if (i == 0) {
							// alle Regale anzeigen
						} else {
							// Regal i anzeigen
						}
						table.repaint();
					}
				}

			}
		}
	}

	private void sortByPartNumber() {
		// TODO Auto-generated method stub		
	}

	private void sortByNames() {
		// TODO Auto-generated method stub		
	}
}
