package warehouse;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
	private JTextField contentInfoLabel = new JTextField("Inhaltsanzeige: Alle Regale");
	int tableLength = 80; // vorläufige Hilfsvariable für Tabellenlänge
	private JButton sortByNameBtn = new JButton("nach Bezeichnung sortieren");
	private JButton sortByPartNumberBtn = new JButton("nach Teilenr. sortieren");

	// Testbuttons
	public static DefaultTableModel model;

	// private JPanel testBtnPanel;
	// JButton buttonAddRow = new JButton("add row");
	// JButton buttonRemRow = new JButton("remove row");

	// Elemente Info Panel
	private JLabel positionTransportSystemLabel = new JLabel("Standort des Fahrzeuges: ");
	private JLabel drivewayLabel = new JLabel("Zurückgelegter Fahrweg: ");
	private JLabel basicUnitLabel = new JLabel("Die Größe eines Faches entspricht 10 Grundeinheiten.");
	private static JTextField positionTransportSystemText = new JTextField(TransportVehicle.getPosX() + " " + TransportVehicle.getPosY() + " " + TransportVehicle.getPosZ());
	private JTextArea drivewayText = new JTextArea("Weg in x-Richtung: x\nWeg in y-Richtung: y\nWeg in z-Richtung: z");
	
	public MainFrame() {
		initTable();
		initGUI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1150, 750));
		// this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocation(0, 0);
		this.setTitle("Lagerverwaltung");
		this.setVisible(true);
	}

	public JLabel getPositionTransportSystemLabel() {
		return positionTransportSystemLabel;
	}

	public void setPositionTransportSystemLabel(JLabel positionTransportSystemLabel) {
		this.positionTransportSystemLabel = positionTransportSystemLabel;
	}

	public JTextField getPositionTransportSystemText() {
		return positionTransportSystemText;
	}

	//public static void setPositionTransportSystemText(newPositionTransportSystemText) {
	//	positionTransportSystemText = new JTextField(newPositionTransportSystemText);
	//}

	private void initTable() {
		// Die Namen der Columns
		String[] titles = new String[] { "Regal", "Fach", "Bezeichnung",
				"Teilenummer", "Größe" };
		//positionTransportSystemText = new JTextField("Test");

		// Das Model das wir verwenden werden. Hier setzten wir gleich die
		// Titel, aber es ist später immer noch möglich weitere Columns oder
		// Rows hinzuzufügen.
		model = new DefaultTableModel(titles, 0);

		// Das JTable initialisieren
		table = new JTable(model);

		// Buttons, damit das alles schöner aussieht.
		// buttonAddRow = new JButton("add row");
		// buttonRemRow = new JButton("remove row");
		//
		// buttonRemRow.setEnabled(false);

		// Den Buttons ein paar Reaktionen geben
		// buttonAddRow.addActionListener(this);
		//
		// int size = model.getRowCount();
		//
		// buttonRemRow.addActionListener(this);
		//
		// testBtnPanel = new JPanel();
		// testBtnPanel.setLayout(new GridLayout(1,2));
		// testBtnPanel.add(buttonAddRow);
		// testBtnPanel.add(buttonRemRow);

	}

	public static Vector createDataVector(Part part, Compartment compartment,
			String neueZeile, int datenbreite) {
		Vector vector = new Vector(5);
		vector.add(compartment.getPosY());
		vector.add(compartment.getPosX() + " " + compartment.getPosZ());
		vector.add(part.getDescription());
		vector.add(part.getPartnumber());
		vector.add(part.getSize());

		return vector;
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
		// tablePanel.add(testBtnPanel,BorderLayout.SOUTH);
	}

	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(null);

		positionTransportSystemLabel.setBounds(20, 80, 150, 30);
		infoPanel.add(positionTransportSystemLabel);

		positionTransportSystemText.setBounds(190, 85, 150, 20);
		positionTransportSystemText.enable(false);
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
	}

	private void sortByNames() {
	}

	public static void addARow(Part part, Compartment compartment) {
		// einen neuen Vector mit Daten herstellen
		Vector newDatas = createDataVector(part, compartment, "neueZeile", 1);

		// eine neue Row hinzufügen
		model.addRow(newDatas);
	}

	public static void removeARow(Part part, Compartment compartment) {
		int size = model.getRowCount();

		for (int i = 0; i < size; i++) {
			if (model.getValueAt(i, 3).equals(part.getPartnumber())) {
				model.removeRow(i);
				return;
			}
		}
	}

}
