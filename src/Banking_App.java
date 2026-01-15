import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Banking_App {

    private static final String URL = "jdbc:mysql://localhost:3306/BANKING_SYSTEM";
    private static final String USER = "root";
    private static final String PASSWORD = "2312";

    public static void main(String[] args) throws SQLException ,ClassNotFoundException{

        try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in);
            User user = new User(connection,sc);
            Accounts account = new Accounts(connection,sc);
            Transfer_Money transferMoney = new Transfer_Money(connection,sc);

            String MAIL;
            long ACC_NO =0;

            while(true){
                System.out.println("Welcome to Banking System");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int choice  = sc.nextInt();
                switch (choice){
                    case 1:
                        user.register();
                        break;
                    case 2:
                        MAIL = user.login();
                        if(MAIL!= null){
                            System.out.println();
                            System.out.println("User Logged In");
                            if(!account.Account_Exists(MAIL)){
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                if(sc.nextInt()==1){
                                    ACC_NO = account.Open_Account(MAIL);
                                    System.out.println("ACCOUNT OPENED");
                                    System.out.println("Your Account Number is : "+ACC_NO);
                                }
                                else{
                                    break;
                                }
                            }else {
                                ACC_NO = account.Get_Account_No(MAIL);
                            }

                            int choice2 = 0;
                            while (choice2 !=5){
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice: ");
                                choice2 = sc.nextInt();
                                switch (choice2){
                                    case 1:
                                        transferMoney.Debit_Money(ACC_NO);
                                        break;
                                    case 2 :
                                        transferMoney.Credit_Money(ACC_NO);
                                        break;
                                    case 3 :
                                        transferMoney.TransferMoney(ACC_NO);
                                        break;
                                    case 4 :
                                        transferMoney.Get_Balance(ACC_NO);
                                        break;
                                    case 5 :
                                            break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                        break;

                                }
                            }
                        }
                        else System.out.println("Incorrect Email or Password!");
                        break;
                    case 3:
                        System.out.println("Thank You for using Banking System!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
