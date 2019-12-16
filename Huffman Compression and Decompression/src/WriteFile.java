import java.io.*;
import java.util.BitSet;

public class WriteFile {

	public static void writeStrings(String filename, String encoded) throws IOException {

		try {
			BitSet buffer = new BitSet();
			FileOutputStream fos;
			fos = new FileOutputStream(new File(filename), false);
			for (int i = 0; i < encoded.length(); i++) {
				if (encoded.charAt(i) == '1') {
					buffer.set(i);					
				} else {
					buffer.clear(i);					
				}
			}
			fos.write(buffer.toByteArray());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}