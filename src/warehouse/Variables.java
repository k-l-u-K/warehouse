package warehouse;

public class Variables {
	public static final int
		REGALCOUNT = 8,						// Anz. der Regale
		COMPARTMENTSIDEBYSIDE = 10,			// Anz. der Fächer nebeneinander
		COMPARTMENTONTOPOFEACHOTHER = 10,	// Anz. der Fächer übereinander
		FILLRANDOMCOUNT = 50,				// wenn FILLEVERYCOMPARTMENT = false steht das für die Anzahl der anzulegeneden Teile
		COMPARTMENTCAPACITY = 10;			// Fachkapazität - bei Änderung muss die Datei neu angelegt werden (händisch)
	public static final boolean
		MULTIPARTTYPPERCOMPARTMENT = true;	// gibt an, ob verschiedene Teile mit verschiedenen Namen in ein Fach eingeordnet werden dürfen
}
