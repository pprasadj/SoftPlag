import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionLength {
	private File file1;
	
       static int comment,comment1,c,counter1;
       static int var, prime;
      static int functioncount,totalCharacters;
      static int totalline;
       
	
	public FunctionLength(File file1) {
		this.file1 = file1;
		
		}
		
		
		
		
		 public static void countLine(String File ) throws FileNotFoundException, IOException{
        String str;

		try (BufferedReader br = new BufferedReader(new FileReader(File))){
                while ((str = br.readLine()) != null){
                totalline++;
                }
                
                }
        }
        
        public static void main(String[] args) throws IOException {
		
                    FunctionLength object = new FunctionLength(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java"));
                     String File="C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
                    countLine(File);
			/*LineNumberReader  lnr = new LineNumberReader(new FileReader(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java")));
                    lnr.skip(Long.MAX_VALUE);
                counter1=lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
// Finally, the LineNumberReader object should be closed to prevent resource leak

                    lnr.close();*/
                    
                    }
                    
                    }

/*
public class FunctionLength {

    private static void Functionlength(String File) throws FileNotFoundException, IOException {
 
		
		int totalCharacters = 0;
    int functioncount=0;
           
		String str;

		try (BufferedReader br = new BufferedReader(new FileReader(File))){
		
                while ((str = br.readLine()) != null){
                   
                   if(str.contains("private void")||str.contains("public int")||(str.contains("void"))||(str.contains("private int"))){
                   
                      // functioncount++;
                       
                       System.out.println(str);
                String[] str1 = str.replaceAll("\\s+", " ").replaceAll("\\W", "").split(" ");
               
                for (String s : str1) {
                
					totalCharacters += s.length();
				}
            
                }
                
                }
               
                }
                 catch (IOException e) {
          e.printStackTrace();
		}
                
              System.out.println("totalCharacters:"+totalCharacters);
            //  System.out.println("functioncount :"+functioncount);
            
    }
    public static void main(String[] args) throws IOException {
       
        try {
			String File = "C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
			Functionlength(File);
		}
		 catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}*/