import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class code{
public static void main(String[] args)
{
    //file path
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
	
    //Variables declaration for new keyword count
    String inputNewCount = "new";
    double newcount = 0, newcountBuffer = 0, newcountLine = 0;
    String newlineNumber = "";
    String newline = "";
    BufferedReader nbr;
    
    //Variables declaration for private keyword count	
    String inputPrivateCount = "private";
    double privatecount = 0, privatecountBuffer = 0, privatecountLine = 0;
    String privatelineNumber = "";
    String privateline = "";
    BufferedReader pbr;
   
    try {
	//read the file using Buffer Reader    
        cbr = new BufferedReader(new FileReader(filePath));
        try {
            while((classline = cbr.readLine()) != null)
            {
                classcountLine++;
                //split() method splits the string into substring    
                String[] words = classline.split(" "); 
		//search for the word
                for (String word : words) {
                  if (word.equals(inputClassCount)) {
		    //increment the keyword count	  
                    classcount++; 
                    classcountBuffer++;
                  }
                }
                //show the line number where keyword exists 
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
        
        nbr = new BufferedReader(new FileReader(filePath));
        try {
            while((newline = nbr.readLine()) != null)
            {
                newcountLine++;
                //System.out.println(line);
                String[] words = newline.split(" ");

                for (String word : words) {
                  if (word.equals(inputNewCount)) {
                    newcount++;
                    newcountBuffer++;
                  }
                }

                if(newcountBuffer > 0)
                {
                    newcountBuffer = 0;
                    newlineNumber += newcountLine + ",";
                }
            }
            nbr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        pbr = new BufferedReader(new FileReader(filePath));
        try {
            while((privateline = pbr.readLine()) != null)
            {
                privatecountLine++;
                //System.out.println(line);
                String[] words = privateline.split(" ");

                for (String word : words) {
                  if (word.equals(inputPrivateCount)) {
                    privatecount++;
                    privatecountBuffer++;
                  }
                }

                if(privatecountBuffer > 0)
                {
                    privatecountBuffer = 0;
                    privatelineNumber += privatecountLine + ",";
                }
            }
            pbr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    //Show the count of keywords 
    System.out.println("Keyword Class found at--"+classcount+" times");
    System.out.println("Word found at--"+classlineNumber);
    System.out.println("Keyword Implement found at--"+implementcount+" times");
    System.out.println("Word found at--"+implementlineNumber);
    System.out.println("Keyword New found at--"+newcount+" times");
    System.out.println("Word found at--"+newlineNumber);
    System.out.println("Keyword Private found at--"+privatecount+" times");
    System.out.println("Word found at--"+privatelineNumber);
}
} 

  
