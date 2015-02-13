package warehouse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class HandleFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String file =".\\data\\DateiZumEinlesen.ser";
	//OutputStream ostream = new FileOutputStream(file);
	//PrintWriter writer = new PrintWriter(ostream);

	public static void serialize(Regal[] regale) {
    	try {
    		System.out.println(regale);
    		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file,true));
    		stream.writeObject(regale);
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
