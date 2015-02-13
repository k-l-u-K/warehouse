package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileHandle implements Serializable {
	private static final long serialVersionUID = -7725023475097213226L;
	
	private static String file =".\\data\\DateiZumEinlesen.ser";
	//OutputStream ostream = new FileOutputStream(file);
	//PrintWriter writer = new PrintWriter(ostream);

	public static void serialize(Compartment[][] compartments) {
    	try {
    		System.out.println(compartments);
    		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file,true));
    		stream.writeObject(compartments);
    		stream.close();
    	}
    	catch ( IOException e ) { System.err.println( e ); }
	}
  	
  	public void deserialize() {
  		try {
  			ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
  			Compartment compartments = (Compartment) o.readObject();
  			o.close();
      	}
    	catch ( IOException e ) { System.err.println( e ); }
    	catch ( ClassNotFoundException e ) { System.err.println( e ); }
	}
}
