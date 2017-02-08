import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



public class PrimitiveVariable {
	private File file1;
	

	private static HashMap<String, Integer> variableCountFile1;
	
	private Set<String> variableTypeSet;
       static int comment,comment1,c,counter1;
	static int var ,prime;
       static double p=0.0;
       int functioncount,totalCharacters;
       static int totalline;
       static int totalVariablesFile1 ;
       
        String currentLineFile1 = null;
       
	
	public PrimitiveVariable(File file1) {
		this.file1 = file1;
		
		variableCountFile1 = new HashMap<String, Integer>();
		
		variableTypeSet = new HashSet<String>(
				Arrays.asList(new String[] { "int", "float", "long", "boolean", "double", "char"}));
	}
	

public int variableCount() throws FileNotFoundException, IOException {
		FileReader fileRead1 = new FileReader(file1);

		BufferedReader br1 = new BufferedReader(fileRead1);
		
	List<String> contentFile1 = br1.lines().collect(Collectors.toList());
		
		try {
           
			br1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Iterator<String> itr1 = contentFile1.iterator();
		
               
		
		List<String> commentsFile1 = new ArrayList<String>();
		
		boolean multiLineComment = false;
		boolean multiLineComment2 = false;
		while (itr1.hasNext()) {
			boolean reachedCodeFile1 = false;
			
			currentLineFile1 = itr1.next();
                          
			
			while (!reachedCodeFile1 ) {
                            
				if (!reachedCodeFile1) {
                    	 		
					if (currentLineFile1.trim().isEmpty() || currentLineFile1.startsWith("public class")
							|| currentLineFile1.startsWith("class")) {
                                            
						currentLineFile1 = itr1.next();
					

					} else if (currentLineFile1.contains("*/")) {
					
						multiLineComment = false;
              comment++;
						currentLineFile1 = itr1.next();
					} else if (multiLineComment) {
						currentLineFile1 = itr1.next();
                comment++;
					} else if (currentLineFile1.contains("/*")) {
					comment++;
						multiLineComment = true;
					} else if (currentLineFile1.trim().startsWith("//")) {
					
						commentsFile1.add(currentLineFile1);
						currentLineFile1 = itr1.next();
               comment1++;
                                                
					
					}
                                       
                                       
               else {
						getCurrLineVariables(currentLineFile1,"file1");
						reachedCodeFile1 = true;
                                                
					
              if(currentLineFile1.contains("private void")||currentLineFile1.contains("public int")||(currentLineFile1.contains("void"))||(currentLineFile1.contains("private int"))){
                    functioncount++;
                    
                        //System.out.println(currentLineFile1);
                
                String[] str1 = currentLineFile1.replaceAll("\\s+", " ").replaceAll("\\W", "").split(" ");
                for (String s : str1) {
					totalCharacters += s.length();
				}
                
            
        }
        }
					
				}

			}
       }

















public static void main(String[] args) throws IOException {
		
                    PrimitiveVariable pr = new PrimitiveVariable(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java"));
                   String File="C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
                    countLine(File);



}