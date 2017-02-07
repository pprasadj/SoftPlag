/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soft;

/**
 *
 * @author Manish
 */
public class Wordcosine {
    int count;
    StringBuffer s = new StringBuffer();
         void Cosine_score(String word1, String word2) {
          /*   String [] s3= word1.split("");
             String []s4 =word2.split("");
             for (String s : s3){ 
               
             System.out.println(s);
             }
             for (String s1 : s4){ 
             System.out.println(s1);
             }
*/
        // int st1 = word1.length();
        // int st2 = word2.length();
    String s = (word1 + word2).toLowerCase(); //start with entire contents of both strings
    int i = 0;
    while(i < s.length()){
        char c = s.charAt(i);
        if(i != s.lastIndexOf(c)) //If c occurs multiple times in s, remove first one
            s = s.substring(0, i) + s.substring(i+1, s.length());
        else i++; //otherwise move pointer forward
    }
             System.out.println(s);
             String s = s.
}

/** Returns the intersection of the two strings, case insensitive. 
    Takes O( |S1| * |S2| ) time. */
             
             
             
             
             
       
         public static void main(String[] args) {
        
    
         Wordcosine cs1 = new Wordcosine();
        //double Cos_sim_score= cs1.Cosine_score("My project is Software plagiarism", "My project is Software Plagiarism");
        System.out.println("[Word # VectorA # VectorB]");
        cs1.Cosine_score("banana","bananapie");
    }
}
   

