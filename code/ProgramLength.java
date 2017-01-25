import java.io.FileNotFoundException;
import java.io.IOException;

public class ProgramLength {
 public static void main(String[] args) throws IOException {
		try {
			String File = "C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
			lengthCounts(File);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}