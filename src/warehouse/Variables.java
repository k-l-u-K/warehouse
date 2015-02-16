package warehouse;

public class Variables {
	public static final int
		REGALCOUNT = 8,						// Anz. der Regale
		COMPARTMENTSIDEBYSIDE = 10,			// Anz. der Fächer nebeneinander
		COMPARTMENTONTOPOFEACHOTHER = 10,	// Anz. der Fächer übereinander
		FILLRANDOMCOUNT = 50,				// Anz. der einzulagernden Teile (wenn nicht komplett gefüllt werden soll)
		COMPARTMENTCAPACITY = 10;			// Fachkapazität - bei Änderung muss die Datei neu angelegt werden (händisch)
	public static final boolean
		MULTIPARTTYPPERCOMPARTMENT = false;	// gibt an, ob verschiedene Teile mit verschiedenen Namen in ein Fach eingeordnet werden dürfen
}
