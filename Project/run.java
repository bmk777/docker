import java.util.Scanner;
public class run {
    
    public static void main(String [] args){
        
        Scanner in = new Scanner(System.in);
        // Display the menu
        System.out.println("1\t Locate a file");
        System.out.println("2\t Search word");
 

        //USE SH FILE TO MAKE A FOR LOOP TO CONTINUSOUSLY READ USER INPUT
        //This is good shithttps://stackoverflow.com/questions/39496017/iterate-in-run-command-in-dockerfile
       // https://hpc-wiki.info/hpc/Sh-file so is this
       // https://www.cyberciti.biz/faq/bash-for-loop/#Examples so isx this (examples)

        
        //Get user's choice
        int choice = 0;
        String location;
        String word;
        while(choice != -1){
            System.out.println("Please enter your choice:");
            choice = in.nextInt();
        //Display the title of the chosen module
        switch (choice) {
            case 1: System.out.println("Locate a file"); 
                    //System.out.println("Please enter your file choice:");
                    //location = in.nextLine();
	                break;
            case 2: System.out.println("Search word");
                    //System.out.println("Please enter your word choice:");
                    //word = in.nextLine();
                    break;
            default: System.out.println("Invalid choice");
            }
        }//end of switch
    }//end of the main method
}//end of class