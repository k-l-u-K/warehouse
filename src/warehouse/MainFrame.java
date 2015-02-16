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
	private JMenuItem removeAll;

	// Elemente Table Panel
	private JPanel tableTopPanel;
	private JTable mainTable;
	private JButton saveBtn = new JButton("Speichern");

	private JTextField contentInfoLabel = new JTextField("Inhaltsanzeige: Alle Regale");

	// Testbuttons
	private static DefaultTableModel modelMain;
	private static DefaultTableModel modelPartAmount;
	private TableRowSorter<DefaultTableModel> sorterMain;
	private TableRowSorter<DefaultTableModel> sorterPartAmount;

	// Elemente Info Panel	
	private JPanel infoTopPanel;
	private JPanel infoBottomPanel;

	private JTable partAmountTable;

	private JLabel basicUnitLabel = new JLabel("Die Größe eines Faches entspricht " + Variables.CAPACITY + " Grundeinheiten (GE).");
	private JLabel lastActionLabel = new JLabel("Letzte Aktion:");
	private JLabel drivewayLabel = new JLabel("Zurückgelegter Fahrweg: ");
	private JLabel restCapacityLabel = new JLabel("Restkapazität: ");
	private JLabel restCompartmentLabel = new JLabel("freie Fächer: ");
	private static JTextArea drivewayText = new JTextArea("Weg in x-Richtung: 0\nWeg in y-Richtung: 0\nWeg in z-Richtung: 0");
	private static JTextArea lastActionText = new JTextArea("");
	private static JTextArea restCapacityText = new JTextArea("");
	private static JTextArea restCompartmentText = new JTextArea("");


	public MainFrame() {
		initMainTable();
		initPartAmountTable();
		initGUI();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {
	            exit();
	         }
	      });
		this.setMinimumSize(new Dimension(1150, 750));
		// this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocation(0, 0);
		this.setTitle("Lagerverwaltung");
		this.setVisible(true);
		loadFile();
		setRestCapacityText();
		setRestCompartmentText();
	}

	private void initMainTable() {
		// Die Namen der Columns
		String[] titles = new String[] { "Regal", "Fach", "Bezeichnung", "Teilenummer", "Größe in GE" };

		// Das Model das wir verwenden werden. Hier setzten wir gleich die Titel,
		// aber es ist später immer noch möglich weitere Rows hinzuzufügen.
		modelMain = new DefaultTableModel(titles, 0);

		// Das JTable initialisieren
		mainTable = new JTable(modelMain);
		mainTable.setEnabled(false);

		sorterMain = new TableRowSorter<DefaultTableModel>(modelMain);
		mainTable.setRowSorter(sorterMain);
		sorterMain.setModel(modelMain);

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

	private void initPartAmountTable() {
		String[] titles = new String[] { "Bezeichnung", "Anzahl" };

		// Das Model das wir verwenden werden. Hier setzten wir gleich die Titel,
		// aber es ist später immer noch möglich weitere Rows hinzuzufügen.
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
		partAmountTable.setEnabled(false);
	}

	@SuppressWarnings("rawtypes")
	public static Vector<Comparable> createVectorMainTable(Part part, Compartment compartment) {
		Vector<Comparable> vector = new Vector<Comparable>(5);
		vector.add((compartment.getPosY()/4)+1);
		vector.add(compartment.getPosX() + " " + compartment.getPosZ());
		vector.add(part.getDescription());
		vector.add(part.getPartnumber());
		vector.add(part.getSize());

		return vector;
	}

	@SuppressWarnings("rawtypes")
	public static Vector<Comparable> createVectorPartAmountTable(Part part, int i) {
		Vector<Comparable> vector = new Vector<Comparable>(2);
		vector.add(part.getDescription());
		vector.add(i);
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
		removeAll = new JMenuItem("Lager leeren");

		storeOpt.add(transferToStock);
		storeOpt.add(releaseFromStock);
		storeOpt.add(fillRandom);
		storeOpt.add(removeAll);

		transferToStock.addActionListener(this);
		releaseFromStock.addActionListener(this);
		fillRandom.addActionListener(this);
		removeAll.addActionListener(this);

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

		tableTopPanel.add(saveBtn);
		saveBtn.addActionListener(this);

		tablePanel.add(new JScrollPane(mainTable), BorderLayout.CENTER);
	}

	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,1));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20));
		
		infoTopPanel = new JPanel();
		infoTopPanel.setLayout(null);
		
		infoBottomPanel = new JPanel();
		infoBottomPanel.setLayout(new BorderLayout());
		infoBottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
		
		basicUnitLabel.setBounds(20, 20, 350, 40);
		infoTopPanel.add(basicUnitLabel);
		
		lastActionLabel.setBounds(20, 80, 100, 30);
		infoTopPanel.add(lastActionLabel);
		
		lastActionText.setBounds(120, 80, 350, 40);
		lastActionText.setEnabled(false);
		lastActionText.setBackground(infoPanel.getBackground());
		lastActionText.setDisabledTextColor(Color.BLACK);
		infoTopPanel.add(lastActionText);

		drivewayLabel.setBounds(20, 150, 150, 30);
		infoTopPanel.add(drivewayLabel);

		drivewayText.setBounds(190, 143, 200, 60);
		drivewayText.setEnabled(false);
		drivewayText.setBackground(infoPanel.getBackground());
		drivewayText.setDisabledTextColor(Color.BLACK);
		infoTopPanel.add(drivewayText);	

		restCapacityLabel.setBounds(20, 200, 150, 30);
		infoTopPanel.add(restCapacityLabel);

		restCapacityText.setBounds(120, 207, 350, 18);
		restCapacityText.setEnabled(false);
		restCapacityText.setBackground(infoPanel.getBackground());
		restCapacityText.setDisabledTextColor(Color.BLACK);
		infoTopPanel.add(restCapacityText);

		restCompartmentLabel.setBounds(20, 225, 150, 30);
		infoTopPanel.add(restCompartmentLabel);

		restCompartmentText.setBounds(120, 232, 300, 18);
		restCompartmentText.setEnabled(false);
		restCompartmentText.setBackground(infoPanel.getBackground());
		restCompartmentText.setDisabledTextColor(Color.BLACK);
		infoTopPanel.add(restCompartmentText);

		infoBottomPanel.add(new JScrollPane(partAmountTable));

		infoPanel.add(infoTopPanel);
		infoPanel.add(infoBottomPanel);
	}

	public static void setLastActionText(String lastAction, Part part) {
		lastActionText.setText(lastAction + part.getDescription()
				+ " \n(Teilenummer: " + part.getPartnumber() + ", Größe: "
				+ part.getSize() + ")");
	}

	public static void setDrivewayText() {
		drivewayText.setText("Weg in x-Richtung: " + TransportVehicle.getPosX()
				+ "\nWeg in y-Richtung: " + TransportVehicle.getPosY()
				+ "\nWeg in z-Richtung: " + TransportVehicle.getPosZ());
	}

	public static void setRestCapacityText() {
		restCapacityText.setText(Integer.toString(Warehouse.restCapacity()));
	}

	public static void setRestCompartmentText() {
		restCompartmentText.setText(Integer.toString(Warehouse.restCompartments()));
	}

	@Override
	public void actionPerformed(ActionEvent source) {
		if (source.getSource() instanceof JButton)
			if (source.getSource().equals(saveBtn))
				saveFile();
		if (source.getSource().equals(transferToStock))
			new TransferDialog();
		if (source.getSource().equals(releaseFromStock))
			new ReleaseDialog();
		if (source.getSource().equals(fillRandom))
			Warehouse.fillRandom();
		if (source.getSource().equals(removeAll))
			Warehouse.removeAll();
		else {
			for (int i = 0; i < Variables.REGALCOUNT+1; i++) {
				if (source.getSource().equals(storageRacks[i])) {
					contentInfoLabel.setText("Inhaltsanzeige: " + storageRacks[i].getText());
					if (i == 0)
						sorterMain.setRowFilter(RowFilter.regexFilter(" *"));
					else
						sorterMain.setRowFilter(RowFilter.numberFilter(ComparisonType.EQUAL, i, 0));
				}
			}
		}
	}

	public static void addARow(Part part, Compartment compartment) {
		// einen neuen Vector mit Daten herstellen
		@SuppressWarnings("rawtypes")
		Vector<Comparable> newDatas = createVectorMainTable(part, compartment);
		// eine neue Row hinzufügen
		modelMain.addRow(newDatas);
	}

	public static void removeARow(Part part) {
		int size = modelMain.getRowCount();

		for (int i = 0; i < size; i++) {
			if (modelMain.getValueAt(i, 3).equals(part.getPartnumber())) {
				modelMain.removeRow(i);
				return;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void addARowNewPartDiscription (Part part) {	
		Vector<Comparable> newPartDiscription = createVectorPartAmountTable(part,1);
		modelPartAmount.addRow(newPartDiscription);
	}

	public static void editRowPartDis (Part part, int j) {
		int size = modelPartAmount.getRowCount();
		for (int i = 0; i < size; i++) {
			if (modelPartAmount.getValueAt(i, 0).equals(part.getDescription())) {
				modelPartAmount.setValueAt(j, i, 1);
				return;
			}
		}
		
	}

	public static void removeRowPartDis (Part part) {
		int size = modelPartAmount.getRowCount();

		for (int i = 0; i < size; i++) {
			if (modelPartAmount.getValueAt(i, 0).equals(part.getDescription())) {
				modelPartAmount.removeRow(i);
				return;
			}
		}
	}
	
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

	private void saveFile() {
		FileHandle.serialize();
	}

	private void loadFile() {
		FileHandle.deserialize();
	}

}
