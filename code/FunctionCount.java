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


public class FunctionCount {
	private File file1;
	
       static int comment,comment1,c,counter1;
       static int var, prime;
      static int functioncount;
      static int totalline;
       
	
	public FunctionCount(File file1) {
		this.file1 = file1;
		
		}
	
	
	public int functionCount() throws FileNotFoundException {
		FileReader fileRead1 = new FileReader(file1);

		BufferedReader br1 = new BufferedReader(fileRead1);
		
	List<String> contentFile1 = br1.lines().collect(Collectors.toList());
		
		try {
			br1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Iterator<String> itr1 = contentFile1.listIterator();
		
                String currentLineFile1 = null;
		
		List<String> commentsFile1 = new ArrayList<String>();
		
		boolean multiLineComment = false;
		
		while (itr1.hasNext()) {
			boolean reachedCodeFile1 = false;
			
			currentLineFile1 = itr1.next();
			
			while (!reachedCodeFile1 ) {
				if (!reachedCodeFile1) {
                    			
					if (currentLineFile1.trim().isEmpty() || currentLineFile1.startsWith("public class")
							|| currentLineFile1.startsWith("class")) {
                                            
						currentLineFile1 = itr1.next();
					

					} 
                                        else if (currentLineFile1.contains("*/")) {
					
						multiLineComment = false;
                                                
						currentLineFile1 = itr1.next();
					}
                                        
                                                else if (multiLineComment) {
						comment++;
                                                
                                                    currentLineFile1 = itr1.next();
					} 
                                                else if (currentLineFile1.contains("/*")) {
                                                comment++;
						multiLineComment = true;
                                                
					} 
                                                else if (currentLineFile1.trim().startsWith("//")) {
                                                comment1++;
						commentsFile1.add(currentLineFile1);
						currentLineFile1 = itr1.next();
                                                
                                                
					
					}
                                       
                                       
                                        else {
						
						reachedCodeFile1 = true;
					
                                        if(currentLineFile1.contains("private void")||currentLineFile1.contains("public int")||(currentLineFile1.contains("void"))||(currentLineFile1.contains("private int"))){
                    functioncount++;
                        System.out.println(currentLineFile1);
               
                System.out.println("functioncount="+functioncount);
                
            
                }
                }
                                      
		}
                    }
			}
                
             c=comment+comment1;
               // System.out.println(c);
		
             return 0;
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
		
                    FunctionCount object = new FunctionCount(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java"));
                     String File="C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
                    countLine(File);
			/*LineNumberReader  lnr = new LineNumberReader(new FileReader(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java")));
                    lnr.skip(Long.MAX_VALUE);
                counter1=lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
// Finally, the LineNumberReader object should be closed to prevent resource leak

                    lnr.close();*/
                    var = object.functionCount();
                    //System.out.println("totalCharacters="+totalCharacters);
              // System.out.println("functioncount="+functioncount);
                  //  
                        System.out.println("Total No of comment lines="+ c);
                       // System.out.println("No of variables"+var);
                        System.out.println("Total No of  lines="+ totalline);
                        prime=totalline-c;
                        System.out.println("Total No of Non-comment lines="+ prime);
                        
                         	
	
        }
       
}