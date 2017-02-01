import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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



}

}




 catch (IOException e) {
			e.printStackTrace();
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
}
