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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Manish
 */
public class Cosine {
   public class Cos
 {
  int wd1;
  int wd2;
  Cos(int v1, int v2)
  {
   this.wd1=v1;
   this.wd2=v2;
  }
  public void Update_VAl(int v1, int v2)
  {
   this.wd1=v1;
   this.wd2=v2;
  }
 }
double Cosine_score(File f1,File f2) throws Exception
   {
       double sim_score=0.0000000;
       
       // finding unique words from two documents.
 Hashtable<String, Cos> frequency_wordv = new Hashtable<String, Cosine.Cos>();
  LinkedList<String> Distinct_words_text_1_2 = new LinkedList<String>();
		BufferedReader br1 = new BufferedReader(new FileReader(f1));  // taking input from file 1
		BufferedReader br2 = new BufferedReader(new FileReader(f2));  // taking input from file 2

		// taking contents in a list
                List<String> l1 = br1.lines().collect(Collectors.toList());
		List<String> l2 = br2.lines().collect(Collectors.toList()); 
                
               // removing spaces between two words and stored in a array
                for (String sim : l1 ){
                   String simi[] =sim.split("\\s");  
                    for(int i=0;i<simi.length;i++)
  {
   String tmp_wd = simi[i].trim();
   
   // now making a vector for distinct words which are in file1
   if(tmp_wd.length()>0)
   {
    if(frequency_wordv.containsKey(tmp_wd))
    {
     Cos vals1 = frequency_wordv.get(tmp_wd);
     int freq1 = vals1.wd1+1;
     int freq2 = vals1.wd2;
     vals1.Update_VAl(freq1, freq2);
     frequency_wordv.put(tmp_wd, vals1);
    }
    else
    {
     Cos vals1 = new Cos(1, 0);
     frequency_wordv.put(tmp_wd, vals1);
     Distinct_words_text_1_2.add(tmp_wd);
    }
   }
  }
                }
                 for (String sim1 : l2 ){
                   String sim2[] =sim1.split("\\s");  
                   for(int i=0;i<sim2.length;i++)
  {
   String tmp_wd = sim2[i].trim();
   if(tmp_wd.length()>0)
   {
    if(frequency_wordv.containsKey(tmp_wd))
    {
     Cos vals1 = frequency_wordv.get(tmp_wd);
     int freq1 = vals1.wd1;
     int freq2 = vals1.wd2+1;
     vals1.Update_VAl(freq1, freq2);
     frequency_wordv.put(tmp_wd, vals1);
    }
    else
    {
     Cos vals1 = new Cos(0, 1);
     frequency_wordv.put(tmp_wd, vals1);
     Distinct_words_text_1_2.add(tmp_wd);
    }
   }
  }
                     }
                  
                       
  return(sim_score);
   
                
                

  
   }
    
    
    public static void main(String[] args) throws Exception {
        
    File f1=new File("C:\\Users\\Manish\\Desktop\\Project\\file1.java");
     
    File f2=new File("C:\\Users\\Manish\\Desktop\\Project\\file2.java");
      Cosine cs1 = new Cosine();
    //double Cos_sim_score= cs1.Cosine_score("My project is Software plagiarism", "My project is Software Plagiarism");
   System.out.println("[Word # VectorA # VectorB]");
    double sim_score =cs1.Cosine_score(f1,f2);   
    System.out.println("the Cosine Similarity Score is " +sim_score);
    }
   
    
}
