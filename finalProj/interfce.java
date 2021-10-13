public class interfce {

    public static void main(String[] args){
    
        System.out.print("Welcome to Big Data Processing Application\n");
        System.out.print("Please type the number that corresponds to which application you would like to run:\n");

        boolean running = true;
        int userInput = Integer.valueOf(args[0]);


        //Inf loop, easy fix
        while(running == true){

            System.out.println("Type the number here >" + args[0]);
            
            
            switch (userInput){
                case 1: userInput = 1;
                        System.out.print("Apache Hadoop\n");
                        break;
                case 2: userInput = 2;
                        System.out.print("Apache Spark\n");
                        break;
                case 3: userInput = 3;
                        System.out.print("Jupyter Notebook\n");
                        break;
                case 4: userInput = 4;
                        System.out.print("SonarQube and SonarScanner\n");
                        break;
            }

            if (userInput > 4) {
                System.out.println("Thank you for using!");
                running = false;
            }
            

        }
        
    }
}
    