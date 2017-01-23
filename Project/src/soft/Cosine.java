/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Manish
 */
public class Cosine {
  
void Cosine_score(File f1,File f2) throws Exception
   {

		BufferedReader br1 = new BufferedReader(new FileReader(f1));
		BufferedReader br2 = new BufferedReader(new FileReader(f2));

		List<String> l1 = br1.lines().collect(Collectors.toList());
		List<String> l2 = br2.lines().collect(Collectors.toList());
               String [] countries = l1.toArray(new String[l1.size()]); 

               for(String s:countries)
               {
                     
                   System.out.println(s);
               }
           //     for (String s:l1)
            //    {
             /*   String pattern="[\\s]";
      String replace = "";
      Pattern p = Pattern.compile(pattern);
      Matcher m = p.matcher(s);
    String  str = m.replaceAll(replace);
    */
                // System.out.println(str);
           //     System.out.println(l2);
                //}
                
                
                Iterator<String> itr1 = l1.iterator();
		Iterator<String> itr2 = l2.iterator();
                
                System.out.println(itr1);
                System.out.println(itr2);
                
        double VectAB = 0.0000000;
       double VectA_Sq = 0.0000000;
         double VectB_Sq = 0.0000000;
       

        
      
       
   }
    
    
    public static void main(String[] args) throws Exception {
        
    File f1=new File("C:\\Users\\Manish\\Desktop\\Project\\file1.java");
     
    File f2=new File("C:\\Users\\Manish\\Desktop\\Project\\file2.java");
      Cosine cs1 = new Cosine();
    //double Cos_sim_score= cs1.Cosine_score("My project is Software plagiarism", "My project is Software Plagiarism");
    cs1.Cosine_score(f1,f2);   
    //System.out.println("the Cosine Similarity Score is " +Cos_sim_score);
    }
   
    
}
