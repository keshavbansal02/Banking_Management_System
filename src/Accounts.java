import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner sc;

    public Accounts(Connection connection,Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public long Open_Account(String email){
        if(!Account_Exists(email)){
            String Open_Account_Query  = "INSERT INTO ACCOUNTS(ACCOUNT_NO, FULL_NAME, EMAIL, BALANCE, PIN) VALUES (?,?,?,?,?)";
            sc.nextLine();
            System.out.println("Enter your full name: ");
            String FULL_NAME = sc.nextLine();
            System.out.println("Deposit Initial Ammount : ");
            double BALANCE = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter your PIN: ");
            String PIN = sc.nextLine();

            try{
                long ACCOUNT_NO = Generate_Account_no();
                PreparedStatement preparedStatement = connection.prepareStatement(Open_Account_Query);
                preparedStatement.setLong(1, ACCOUNT_NO);
                preparedStatement.setString(2, FULL_NAME);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, BALANCE);
                preparedStatement.setString(5, PIN);
                int affected_rows = preparedStatement.executeUpdate();
                if(affected_rows > 0){
                    System.out.println("Account Opened successfully!");
                    return ACCOUNT_NO;
                }
                else System.out.println("Account Not Opened!");

            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else throw new RuntimeException("Account Already Exists");
        return Get_Account_No(email);
    }

    public long Get_Account_No(String email) {
        String Query = "SELECT ACCOUNT_NO FROM ACCOUNTS WHERE EMAIL = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("ACCOUNT_NO");
            }
            else throw new RuntimeException("Account Not Found");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Not Found");

    }

    private long Generate_Account_no(){
        try{
            String Query = "SELECT ACCOUNT_NO from ACCOUNTS ORDER BY ACCOUNT_NO DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            if(resultSet.next()){
                long acc_no = resultSet.getLong("ACCOUNT_NO");
                return acc_no+1;
            }
            else return 42496590001L;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 42496590001L;
    }

    public boolean Account_Exists(String email){
        String Query = "Select * from ACCOUNTS where EMAIL = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}