package warehouse;

public class Variables {
	public static final int
		REGALCOUNT = 8,						// Anz. der Regale
		COMPARTMENTSIDEBYSIDE = 10,			// Anz. der Fächer nebeneinander
		COMPARTMENTONTOPOFEACHOTHER = 10,	// Anz. der Fächer übereinander
		RANDOM = 50,						// wenn FILLEVERYCOMPARTMENT = false steht das für die Anzahl der anzulegeneden Teile
		CAPACITY = 10;						// Fachkapazität - bei Änderung muss die Datei neu angelegt werden (händisch)
	public static final boolean
		FILLEVERYCOMPARTMENT = false,		// füllt jedes Fach - dauert ggf. sehr lange!
		MULTIPARTTYPPERCOMPARTMENT = false;	// gibt an, ob verschiedene Teile mit verschiedenen Namen in ein Fach eingeordnet werden dürfen
}
