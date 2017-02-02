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
	



















public static void main(String[] args) throws IOException {
		
                    PrimitiveVariable pr = new PrimitiveVariable(new File("C:\\Users\\kunal\\Desktop\\project\\code\\File1.java"));
                   String File="C:\\Users\\kunal\\Desktop\\project\\code\\File1.java";
                    countLine(File);



}