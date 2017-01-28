import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PSM12{
public static void main(String[] args)
{
    String filePath = "C:\\Users\\Vishal\\Documents\\File1.txt";
    
	//Variables declaration for class keyword count
    String inputClassCount = "class";
    double classcount = 0, classcountBuffer = 0, classcountLine = 0;
    String classlineNumber = "";
    String classline = "";
    BufferedReader cbr;
	
    //Variables declaration for implement keyword count
    String inputImplementCount = "implement";
    double implementcount = 0, implementcountBuffer = 0, implementcountLine = 0;
    String implementlineNumber = "";
    String implementline = "";
    BufferedReader ibr;
	
try {
        cbr = new BufferedReader(new FileReader(filePath));
        try {
            while((classline = cbr.readLine()) != null)
            {
                classcountLine++;
                //System.out.println(line);
                String[] words = classline.split(" ");

                for (String word : words) {
                  if (word.equals(inputClassCount)) {
                    classcount++;
                    classcountBuffer++;
                  }
                }

                if(classcountBuffer > 0)
                {
                    classcountBuffer = 0;
                    classlineNumber += classcountLine + ",";
                }
            }
            cbr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
        ibr = new BufferedReader(new FileReader(filePath));
        try {
            while((implementline = ibr.readLine()) != null)
            {
                implementcountLine++;
                //System.out.println(line);
                String[] words = implementline.split(" ");

                for (String word : words) {
                  if (word.equals(inputImplementCount)) {
                    implementcount++;
                    implementcountBuffer++;
                  }
                }

                if(implementcountBuffer > 0)
                {
                    implementcountBuffer = 0;
                    implementlineNumber += implementcountLine + ",";
                }
            }
            ibr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		System.out.println("Keyword Class found at--"+classcount+" times");
		System.out.println("Word found at--"+classlineNumber);
		System.out.println("Keyword Implement found at--"+implementcount+" times");
		System.out.println("Word found at--"+implementlineNumber);
	}
}