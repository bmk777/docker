
import java.util.Scanner;
public class run {
    
    public static void main(String [] args){
        
        Scanner in = new Scanner(System.in);
        // Display the menu
        System.out.println("1\t Apache Hadoop");
        System.out.println("2\t Apache Sparch");
        System.out.println("3\t Jupyter Notebook");
        System.out.println("4\t SonarQube and SonarScanner");

        
        
        //Get user's choice
        int choice = 0;
        while(choice != -1){
            System.out.println("Please enter your choice:");
            choice=in.nextInt();
        //Display the title of the chosen module
        switch (choice) {
            case 1: System.out.println("Apache Hadoop"); 
	   break;
            case 2: System.out.println("Apache Sparch");
                    break;
            case 3: System.out.println("Jupyter Notebook"); 
                    break;
            case 4: System.out.println("SonarQube and SonarScanner"); 
                     break;
            default: System.out.println("Invalid choice");
            }
        }//end of switch
    }//end of the main method
}//end of class