package warehouse;

import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
	private JMenuItem[] storageRacks = new JMenuItem[Variables.REGALCOUNT+1];
	private JMenuItem transferToStock;
	private JMenuItem releaseFromStock;
	private JMenuItem fillRandom;
	private JMenuItem fillRandomComplete;
	private JMenuItem removeAll;

	// Elemente Table Panel
	private JPanel tableTopPanel;
	private JTable mainTable;
	private JButton saveBtn = new JButton("Speichern");

	private JLabel contentInfoLabel = new JLabel("Inhaltsanzeige: Alle Regale");
	// ausgewähltes Regal bei der Anzeige, 0 = alle Regale
	private static int selectedRack = 0;

	// Tabellenlayouts und Sortierung
	private static DefaultTableModel modelMain;
	private static DefaultTableModel modelPartAmount;
	private TableRowSorter<DefaultTableModel> sorterMain;
	private TableRowSorter<DefaultTableModel> sorterPartAmount;

	// Elemente Info Panel	
	private JPanel infoTopPanel;
	private JPanel infoBottomPanel;

	private JTable partAmountTable;

	private JLabel basicUnitLabel = new JLabel("Die Größe eines Faches entspricht " + Variables.COMPARTMENTCAPACITY + " Grundeinheiten (GE).");
	private JLabel lastActionLabel = new JLabel("Letzte Aktion:");
	private JLabel drivewayLabel = new JLabel("Zurückgelegter Fahrweg: ");
	private JLabel restCapacityLabel = new JLabel("Restkapazität: ");
	private JLabel restCompartmentLabel = new JLabel("freie Fächer: ");
	private static JLabel restCapacitySingleRackLabel = new JLabel("");
	private static JLabel restCompartmentSingleRackLabel = new JLabel("");
	private static JTextArea drivewayText = new JTextArea("Weg in x-Richtung: 0\nWeg in y-Richtung: 0\nWeg in z-Richtung: 0");
	private static JTextArea lastActionText = new JTextArea("");
	private static JTextArea restCapacityText = new JTextArea("");
	private static JTextArea restCompartmentText = new JTextArea("");
	private static JTextArea restCapacitySingleRackText = new JTextArea("");
	private static JTextArea restCompartmentSingleRackText = new JTextArea("");


	public MainFrame() {
		initMainTable();
		initPartAmountTable();
		initGUI();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	        	 // Beim Beenden nachfragen, ob gespeichert werden soll
	        	 exit();
	    	}
	    });
		// Größe, Position und Titel festlegen
		this.setMinimumSize(new Dimension(1150, 750));
		this.setLocationRelativeTo(null);
		this.setTitle("Lagerverwaltung");
		this.setVisible(true);
		// Datei laden und Restkapazität und freie Fächer aktualisieren
		loadFile();
		setRestCapacityText();
		setRestCompartmentText();
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

	private void initMainTable() {
		// Die Namen der Spalten
		String[] titles = new String[] { "Regal", "Fach", "Bezeichnung", "Teilenummer", "Größe in GE" };

		// Tabelle erzeugen
		modelMain = new DefaultTableModel(titles, 0);

		// Das JTable initialisieren
		mainTable = new JTable(modelMain);
		mainTable.setEnabled(false);
	
		// Spaltenbreite für Beschreibung festlegen
		mainTable.getColumnModel().getColumn(2).setPreferredWidth(200);

		// Über Spaltenüberschirft soll später sortiert werden können 
		sorterMain = new TableRowSorter<DefaultTableModel>(modelMain);
		mainTable.setRowSorter(sorterMain);
		sorterMain.setModel(modelMain);

		// Korrektur der Sortierung bei der Teilenummer
		sorterMain.setComparator(3, new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg0 - arg1;
			}
		});

		// Spalten 2 und 3 sind Sortierbar über den Spaltenkopf
		for (int i = 0; i < 5; i++) {
			if (i==2 || i==3)
				sorterMain.setSortable(i, true);
			else
				sorterMain.setSortable(i, false);
		}
	}

	// Info-Anzeige über die Anzahl der Teile
	private void initPartAmountTable() {
		String[] titles = new String[] { "Bezeichnung", "Anzahl" };
		modelPartAmount = new DefaultTableModel(titles, 0);

		// Das JTable initialisieren
		partAmountTable = new JTable(modelPartAmount);
		partAmountTable.setEnabled(false);

		sorterPartAmount = new TableRowSorter<DefaultTableModel>(modelPartAmount);
		partAmountTable.setRowSorter(sorterPartAmount);
		sorterPartAmount.setModel(modelPartAmount);

		sorterPartAmount.setComparator(1, new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg0 - arg1;
			}
		});
		
		sorterPartAmount.setSortable(0, false);
		partAmountTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		partAmountTable.setEnabled(false);
	}

	// Menü
	private void initMenu() {
		menu = new JMenuBar();

		storeInfo = new JMenu("Lageranzeige");
		storeOpt = new JMenu("Lageroptionen");

		for (int i = 0; i < Variables.REGALCOUNT+1; i++) {
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
		fillRandom = new JMenuItem("Lager zufällig befüllen");
		fillRandomComplete = new JMenuItem("Lager zufällig vollständig befüllen");
		removeAll = new JMenuItem("Lager leeren");

		storeOpt.add(transferToStock);
		storeOpt.add(releaseFromStock);
		storeOpt.add(fillRandom);
		storeOpt.add(fillRandomComplete);
		storeOpt.add(removeAll);

		transferToStock.addActionListener(this);
		releaseFromStock.addActionListener(this);
		fillRandom.addActionListener(this);
		fillRandomComplete.addActionListener(this);
		removeAll.addActionListener(this);

		menu.add(storeInfo);
		menu.add(storeOpt);

		this.setJMenuBar(menu);
	}

	// oberes Feld mit Speichern-Button und Regalnr.-Anzeige
	private void initTablePanel() {
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());

		tableTopPanel = new JPanel();
		tableTopPanel.setLayout(new GridLayout(1, 3));
		tablePanel.add(tableTopPanel, BorderLayout.NORTH);

		// erzwungener Rahmen
		tablePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20));

		tableTopPanel.add(contentInfoLabel);

		tableTopPanel.add(saveBtn);
		saveBtn.addActionListener(this);
		tablePanel.add(new JScrollPane(mainTable), BorderLayout.CENTER);
	}
	
	// Panel, welches allg. Infos über letzte Aktion, Fahrtweg etc. anzeigt
	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,1));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20));
		
		infoTopPanel = new JPanel();
		infoTopPanel.setLayout(null);
		
		infoBottomPanel = new JPanel();
		infoBottomPanel.setLayout(new BorderLayout());
		infoBottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
		
		basicUnitLabel.setBounds(20, 20, 350, 40);
		infoTopPanel.add(basicUnitLabel);
		
		lastActionLabel.setBounds(20, 80, 100, 30);
		infoTopPanel.add(lastActionLabel);
		
		lastActionText.setBounds(120, 80, 280, 35);
		initLockedText(lastActionText);
		infoTopPanel.add(lastActionText);

		drivewayLabel.setBounds(20, 150, 150, 30);
		infoTopPanel.add(drivewayLabel);

		drivewayText.setBounds(190, 143, 210, 50);
		initLockedText(drivewayText);
		infoTopPanel.add(drivewayText);	

		restCapacityLabel.setBounds(20, 230, 100, 30);
		infoTopPanel.add(restCapacityLabel);

		restCapacityText.setBounds(120, 237, 100, 17);
		initLockedText(restCapacityText);
		infoTopPanel.add(restCapacityText);

		restCompartmentLabel.setBounds(20, 255, 100, 30);
		infoTopPanel.add(restCompartmentLabel);

		restCompartmentText.setBounds(120, 262, 100, 17);
		initLockedText(restCompartmentText);
		infoTopPanel.add(restCompartmentText);
		
		// Anzeigen für einzelnes Lager - zu Beginn unsichtbar
		restCapacitySingleRackLabel.setBounds(260, 230, 150, 30);
		infoTopPanel.add(restCapacitySingleRackLabel);
		restCapacitySingleRackLabel.setVisible(false);

		restCapacitySingleRackText.setBounds(410, 237, 100, 17);
		initLockedText(restCapacitySingleRackText);
		infoTopPanel.add(restCapacitySingleRackText);
		restCapacitySingleRackText.setVisible(false);

		restCompartmentSingleRackLabel.setBounds(260, 255, 150, 30);
		infoTopPanel.add(restCompartmentSingleRackLabel);
		restCompartmentSingleRackLabel.setVisible(false);

		restCompartmentSingleRackText.setBounds(410, 262, 100, 17);
		initLockedText(restCompartmentSingleRackText);
		infoTopPanel.add(restCompartmentSingleRackText);
		restCompartmentSingleRackText.setVisible(false);

		infoBottomPanel.add(new JScrollPane(partAmountTable));

		infoPanel.add(infoTopPanel);
		infoPanel.add(infoBottomPanel);
	}
	
	// Initialisieren eines für Benutzereingaben gesperrten Textfeldes
	private void initLockedText(JTextArea textField) {
		textField.setEnabled(false);
		textField.setBackground(infoPanel.getBackground());
		textField.setDisabledTextColor(Color.BLACK);		
	}

	// Methode zum Aktualisieren der letzten Aktion
	public static void setLastActionText(String lastAction, Part part) {
		lastActionText.setText(lastAction + part.getDescription()
				+ " \n(Teilenummer: " + part.getPartnumber() + ", Größe: "
				+ part.getSize() + ")");
	}

	// zum Aktualisieren der Anzeige des letzten Fahrwegs des Fahrzeugs
	public static void setDrivewayText() {
		drivewayText.setText("Weg in x-Richtung: " + TransportVehicle.getPosX()
				+ "\nWeg in y-Richtung: " + TransportVehicle.getPosY()
				+ "\nWeg in z-Richtung: " + TransportVehicle.getPosZ());
	}

	// zum Aktualisieren der Anzeige der restlichen Kapazität
	public static void setRestCapacityText() {
		restCapacityText.setText(Integer.toString(Warehouse.restCapacity()));
		restCapacitySingleRackLabel.setText("Restkapazität in Regal " + selectedRack + ":");
		restCapacitySingleRackText.setText(Integer.toString(Warehouse.restCapacitySingleRack(selectedRack)));		
	}

	// zum Aktualisieren der Anzeige der restlichen freien Fächer
	public static void setRestCompartmentText() {
		restCompartmentText.setText(Integer.toString(Warehouse.restCompartments()));
		restCompartmentSingleRackLabel.setText("Freie Fächer in Regal " + selectedRack + ":");
		restCompartmentSingleRackText.setText(Integer.toString(Warehouse.restCompartmentsSingleRack(selectedRack)));		
	}

	// Aktionen abfangen und Methoden zuweisen
	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource().equals(saveBtn))
			saveFile();
		if (source.getSource().equals(transferToStock))
			new TransferDialog();
		if (source.getSource().equals(releaseFromStock))
			new ReleaseDialog();
		if (source.getSource().equals(fillRandom)) {
			int result = JOptionPane.showConfirmDialog(null,
					"Sind sie sicher, dass sie das Lager zufällig befüllen möchten?\nAlle nicht gespeicherten Änderungen gehen dann verloren!",
					"Lager zufällig befüllen", JOptionPane.YES_NO_OPTION);
			if (result == 0) 
				Warehouse.fillRandom(false);						
		}
		if (source.getSource().equals(fillRandomComplete)) {
			int result = JOptionPane.showConfirmDialog(null,
					"Sind sie sicher, dass sie das Lager zufällig befüllen möchten?\nAlle nicht gespeicherten Änderungen gehen dann verloren!\nBeachten Sie, dass dieser Vorgang einige Sekunden dauern kann!",
					"Lager zufällig befüllen",JOptionPane.YES_NO_OPTION);
			if (result == 0) 
				Warehouse.fillRandom(true);	
		}
		if (source.getSource().equals(removeAll)) {
			int result = JOptionPane.showConfirmDialog(null,
					"Sind sie sicher, dass sie das gesamte Lager leeren wollen?\nAlle nicht gespeicherten Änderungen gehen dann verloren!",
					"Lager vollständig leeren", JOptionPane.YES_NO_OPTION);
			if (result == 0)
				Warehouse.removeAll();						
		} else {
			for (int i = 0; i < Variables.REGALCOUNT+1; i++) {
				if (source.getSource().equals(storageRacks[i])) {
					contentInfoLabel.setText("Inhaltsanzeige: " + storageRacks[i].getText());
					if (i == 0) {
						sorterMain.setRowFilter(RowFilter.regexFilter(" *"));
						restCompartmentSingleRackLabel.setVisible(false);
						restCompartmentSingleRackText.setVisible(false);
						restCapacitySingleRackLabel.setVisible(false);
						restCapacitySingleRackText.setVisible(false);
					} else {
						sorterMain.setRowFilter(RowFilter.numberFilter(ComparisonType.EQUAL, i, 0));
						restCompartmentSingleRackLabel.setVisible(true);
						restCompartmentSingleRackText.setVisible(true);
						restCapacitySingleRackLabel.setVisible(true);
						restCapacitySingleRackText.setVisible(true);
					}
					selectedRack = i;
					setRestCapacityText();
					setRestCompartmentText();					
				}
			}
		}
	}

	// Zeile bei neuen Teilen hinzufügen
	public static void addRowMainTable(Part part, Compartment compartment) {
		// einen neuen Vector mit Daten herstellen
		@SuppressWarnings("rawtypes")
		Vector<Comparable> newRowMain = createVectorMainTable(part, compartment);
		// eine neue Row hinzufügen
		modelMain.addRow(newRowMain);
	}

	// Zeile entfernen, wenn Teile ausgelagtert werden
	public static void removeRowMainTable(Part part) {
		int size = modelMain.getRowCount();

		for (int i = 0; i < size; i++) {
			if (modelMain.getValueAt(i, 3).equals(part.getPartnumber())) {
				modelMain.removeRow(i);
				return;
			}
		}
	}

	// Zeile bei der Gesamtanzahlanzeige Hinzufügen, wenn Teil eingelagert wird
	@SuppressWarnings("rawtypes")
	public static void addRowPartAmountTable (Part part) {	
		Vector<Comparable> newRowPartAmount = createVectorPartAmountTable(part,1);
		modelPartAmount.addRow(newRowPartAmount);
	}

	// Zeile bei der Gesamtanzahlanzeige aktualisieren, wenn Teil ein- / ausgelagert wird
	public static void editRowPartAmountTable (Part part, int newAmount) {
		int size = modelPartAmount.getRowCount();
		for (int i = 0; i < size; i++) {
			if (modelPartAmount.getValueAt(i, 0).equals(part.getDescription())) {
				modelPartAmount.setValueAt(newAmount, i, 1);
				return;
			}
		}
	}

	// Zeile bei der Gesamtanzahlanzeige entfernen, wenn Teil ausgelagert wird
	public static void removeRowPartAmountTable (Part part) {
		int size = modelPartAmount.getRowCount();

		for (int i = 0; i < size; i++) {
			if (modelPartAmount.getValueAt(i, 0).equals(part.getDescription())) {
				modelPartAmount.removeRow(i);
				return;
			}
		}
	}

	// Hinzufügen der Zeilen zur Anzeige des Inhalts der Regale und Fächer mit den Teilen
	@SuppressWarnings("rawtypes")
	public static Vector<Comparable> createVectorMainTable(Part part, Compartment compartment) {
		Vector<Comparable> vector = new Vector<Comparable>(5);
		vector.add((((compartment.getPosY()-Variables.REGALDISTANCE)/Variables.REGALDISTANCE)/Variables.COMPARTMENTDWIDTH)+1);
		vector.add(compartment.getPosX() + " " + compartment.getPosZ());
		vector.add(part.getDescription());
		vector.add(part.getPartnumber());
		vector.add(part.getSize());
		return vector;
	}

	// Hinzufügen der Zeilen zur Anzeige des Inhalts der Gesamtanzahlanzeige
	@SuppressWarnings("rawtypes")
	public static Vector<Comparable> createVectorPartAmountTable(Part part, int i) {
		Vector<Comparable> vector = new Vector<Comparable>(2);
		vector.add(part.getDescription());
		vector.add(i);
		return vector;
	}

	// Beim Beenden nach dem Speichern fragen
	private void exit() {
		// Eingabe des Benutzers wird in int-Wert gespeichert: 0 für 1. Antwort, 1 für 2. Antwort etc.		
		int result = JOptionPane.showConfirmDialog(null,"Sollen die Änderungen gespeichert werden?","Programm beenden",JOptionPane.YES_NO_OPTION);

			      switch(result) {
			         case JOptionPane.YES_OPTION:
			         saveFile();	 
			         System.exit(0);

			         case JOptionPane.NO_OPTION:
			        	 System.exit(0);
			      }
	}

	// Beim Speichern der Datei die Methode aufrufen
	private void saveFile() {
		FileHandle.serialize();
	}

	// Beim Laden der Datei die Methode aufrufen
	private void loadFile() {
		FileHandle.deserialize();
	}

}
