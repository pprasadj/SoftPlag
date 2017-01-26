import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProgramLength {
	
	private static void lengthCounts(String File) throws FileNotFoundException, IOException {
 
		int totalLines = 0;
		int totalCharacters = 0;
                float MeanProgramLength=0.0F;
 
		String str;
 
		try (BufferedReader br = new BufferedReader(new FileReader(File))) {
			
			while ((str = br.readLine()) != null) {
	
				totalLines++;
 
	
				String[] str1 = str.replaceAll("\\s+", " ").replaceAll("\\W", "").split(" ");
				
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
 public static void main(String[] args) throws IOException {
		try {
			String File = "C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
			lengthCounts(File);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
