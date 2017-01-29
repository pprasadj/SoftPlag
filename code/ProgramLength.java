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
				
				for (String s : str1) {
					totalCharacters += s.length();
				}
				
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 MeanProgramLength=(float)totalCharacters/totalLines;
		  System.out.println("Total Characters: " + totalCharacters);
		
		  System.out.println("Toal Lines: " + totalLines);
                  System.out.println("MeanProgramLength: "+ MeanProgramLength);
		
		}
 public static void main(String[] args) throws IOException {
		try {
			String File = "C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
			lengthCounts(File);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
