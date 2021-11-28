import java.util.Scanner;
public class run {
    
    public static void main(String [] args){
        
        Scanner in = new Scanner(System.in);
        // Display the menu
        System.out.println("1\t Locate a file");
        System.out.println("2\t generate engine");
        System.out.println("2\t Search word");

        
        //Get user's choice
        int choice = 0;
        String location = "";
        String word;
        while(choice != -1){
            System.out.println("Please enter your choice:");
            
            choice = in.nextInt();

        //Display the title of the chosen module
        switch (choice) {
            case 1: System.out.println("Locate a file"); 
                    System.out.println("Enter the location of the directory you would like to interact with");
                    System.out.println("E.g C:/users/ect/...");
                    //consume debrees
                    in.nextLine();
                    location = in.nextLine();
                    System.out.println("You entered " + location);

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