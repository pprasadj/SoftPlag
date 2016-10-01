
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class File1Copy {
 
        public static void main(String[] args) {
               
                int radius = 0;
                System.out.println("Please enter radius of a circle");
               
                try
                {
                        //get the radius from console
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        radius = Integer.parseInt(br.readLine());
                }
                //if invalid value was entered
                catch(NumberFormatException ne)
                {
                        System.out.println("Invalid radius value" + ne);
                        System.exit(0);
                }
                
                
                
                
                
                
                
                
                
                
                /*
                 * 
                 * 
                 * 
                 * 
                 * 
                 * heajfjafjafjkhakj
                 * 
                 * 
                 * 
                 * helllo
                 */
                
                
                
                
                catch(IOException ioe)
                {
                        System.out.println("IO Error :" + ioe);
                        System.exit(0);
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                //NOTE : use Math.PI constant to get value of pi
                double area = Math.PI * radius * radius ;
               
                System.out.println("Area of a circle is " + area);
        }
}