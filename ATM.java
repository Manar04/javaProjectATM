package atm;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    public static Scanner kbd = new Scanner(System.in);
    
    public static ArrayList<User> allUser = new ArrayList();
    
    public static boolean checkAccount(String number, String password)
    {

        for(int i=0 ; i<allUser.size() ; i++){
            if(number.equals(allUser.get(i).getUN()) && password.equals(allUser.get(i).getPW())){
                return true;
            }
        }
        return false;
    }

        public static int admin_menu()
    {
        int menuChoice;
        do
        { 
            System.out.print("\nPlease Choose From the Following Options:"
                    + "\n 1. Add new user \n 2. Display allUser \n 3. Log out\n\n");
            menuChoice = kbd.nextInt();

            if (menuChoice < 1 || menuChoice > 3){
                System.out.println("error");
            }

        }while (menuChoice < 1 || menuChoice > 3);

        return menuChoice;
    }
    
    public static int user_menu()
    {
        int menuChoice;
        do
        { 
            System.out.print("\nPlease Choose From the Following Options:"
                    + "\n 1. Display Balance \n 2. Deposit"
                    + "\n 3. Withdraw\n 4. Log Out\n\n");
            menuChoice = kbd.nextInt();

            if (menuChoice < 1 || menuChoice > 4){
                System.out.println("error");
            }

        }while (menuChoice < 1 || menuChoice > 4);

        return menuChoice;
    }

    public static void displayBalance(String number)
    {
        for(int i=0 ; i<allUser.size() ; i++){
            if(allUser.get(i).getUN().equals(number)){
                String displayBalance = allUser.get(i).getBALANCE();
                System.out.println("\nYour Current Balance is $"+ displayBalance);
            }
        }
    }

    public static void deposit(double x, double y, String number)
    {
        double depositAmt = x, currentBal = y;
        double newBalance = depositAmt + currentBal;

        System.out.printf("Your New Balance is $%.2f\n",  newBalance);
        for(int i=0 ; i<allUser.size() ; i++){
            if(number.equals(allUser.get(i).getUN())){
                allUser.get(i).setBALANCE(newBalance + "");
            }
        }
    }

    public static void withdraw(double x, double y, String number)
    {
        double withdrawAmt = y, currentBal = x;
        double newBalance = currentBal - withdrawAmt;
        System.out.printf("Your New Balance is %.2f\n", newBalance);
        for(int i=0 ; i<allUser.size() ; i++){
            if(number.equals(allUser.get(i).getUN())){
                allUser.get(i).setBALANCE(newBalance + "");
            }
        }
    }
    
    public static boolean checkAvailable(String number){
        for(int i=0 ; i<allUser.size() ; i++){
            if(number.equals(allUser.get(i).getUN())){
                return true;
            }
        }
        return false;
    }
    
    public static void addNewUser(){
        boolean flag;
        String account_num, account_pw , account_role;
        do{
        System.out.println("Account Number: ");
        account_num = kbd.next();
        System.out.println("Account Pasords: ");
        account_pw = kbd.next();
        System.out.println("Do you want to make user's is admin <yes or no> : ");
        account_role = kbd.next();
        flag = checkAvailable(account_num);
        if(flag == false){
            allUser.add(new User(account_num,account_pw,"0.0",account_role.equals("YES") || account_role.equals("yes") ? true : false));
        }
        else{
            System.out.println("This number not available, try again!");
        }
        }while(flag == true);
    }

    public static void main(String[] args) {

        allUser.add(new User("1111-5","pw1","0.0",true));
        
        String accountNumber, accountPassword;
        int loginCountPerDay = 0, menuOption = 0;
        
        double depositAmt = 0, withdrawAmt = 0, currentBalance = 0; 
        
        boolean isAccountAccepted = false;
        boolean AccountIsAdmin = false;
        
        do{
            System.out.println("Please Enter Your Account Number: ");
            accountNumber = kbd.next();

            System.out.println("Enter Your Password: ");
            accountPassword = kbd.next();

            isAccountAccepted = checkAccount(accountNumber, accountPassword);

            loginCountPerDay++;

            if (loginCountPerDay >= 3 && isAccountAccepted == false){
                System.out.print("Maximum Login Attempts Reached.");
                System.exit(0);
            }
            
            if (isAccountAccepted){
                displayBalance(accountNumber);
            }
            else{
                System.out.println("erroe");
            }
        }while(isAccountAccepted == false);
        
        for(int i=0 ; i<allUser.size() ; i++){
            if(accountNumber.equals(allUser.get(i).getUN()) && allUser.get(i).getROLE() == true){
                AccountIsAdmin = true;
                break;
            }
            else{
                AccountIsAdmin = false;
            }
        }
        
        if(AccountIsAdmin){
            while (menuOption != 3)
        { 
            menuOption = admin_menu();
            switch (menuOption)
            {
            case 1:
                addNewUser();
                break;
                case 2:
                for(int i =0 ; i<allUser.size() ; i++){
                    System.out.println(allUser.get(i).getUN() + " " + allUser.get(i).getPW() + " " + allUser.get(i).getBALANCE() + " " + allUser.get(i).getROLE());
                }
                break;
                case 3:
                System.out.print("\nThank For Using My ATM.  Have a Nice Day.  Good-Bye!");
                System.exit(0);
                break;
            }
        }
        }
        
        else{
            while (menuOption != 4)
        { 
            menuOption = user_menu();
            switch (menuOption)
            {
            case 1:
                displayBalance(accountNumber);
                break;
            case 2:
                System.out.print("\nEnter Amount You Wish to Deposit: $ ");
                depositAmt = kbd.nextDouble();
                
                for(int i=0 ; i<allUser.size() ; i++){
                    if(accountNumber.equals(allUser.get(i).getUN())){
                        currentBalance = Double.parseDouble(allUser.get(i).getBALANCE());
                    }
                }
                deposit(depositAmt, currentBalance , accountNumber);
                break;
            case 3:
                System.out.print("\nEnter Amount You Wish to Withdrawl: $ ");
                withdrawAmt = kbd.nextDouble();
                
                for(int i=0 ; i<allUser.size() ; i++){
                    if(accountNumber.equals(allUser.get(i).getUN())){
                        currentBalance = Double.parseDouble(allUser.get(i).getBALANCE());
                    }
                }
                while(withdrawAmt > currentBalance){
                    System.out.print("ERROR: INSUFFICIENT FUNDS!! "
                            + "PLEASE ENTER A DIFFERENT AMOUNT: $");
                    withdrawAmt = kbd.nextDouble();
                }

                withdraw(withdrawAmt, currentBalance  , accountNumber);
                break;
            case 4:
                System.out.print("\nThank For Using My ATM.  Have a Nice Day.  Good-Bye!");
                System.exit(0);
                break;
            }
        }
        }
        
    }
}


class User{
    private String UN,PW,BALANCE;
    private boolean ROLE;
    
    public  User(String un, String pw, String balance, boolean role){
        this.UN = un;
        this.PW = pw;
        this.BALANCE = balance;
        this.ROLE = role;
    }
    
    public String getUN(){
        return UN;
    }
    
    public void setUN(String newUN){
        UN = newUN;
    }
    
    public String getPW(){
        return PW;
    }
    
    public void setPW(String newPW){
        PW = newPW;
    }
    
    public String getBALANCE(){
        return BALANCE;
    }
    
    public void setBALANCE(String newBALANCE){
        BALANCE = newBALANCE;
    }
    
    public boolean getROLE(){
        return ROLE;
    }
    
}