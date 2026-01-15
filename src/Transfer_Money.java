import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Transfer_Money {
    private Connection connection;
    private Scanner sc;

    public Transfer_Money(Connection connection,Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public void TransferMoney(long sender_account_no) {
        sc.nextLine();
        System.out.println("Enter Receiver Account to transfer");
        long reciever_account_no = sc.nextLong();
        System.out.println("Enter Amount to transfer");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter PIN");
        String PIN = sc.nextLine();
        try{
            connection.setAutoCommit(false);
            if(sender_account_no != 0 && reciever_account_no!=0){
                String query = "SELECT * FROM Accounts WHERE Account_No = ? and PIN = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,sender_account_no);
                preparedStatement.setString(2,PIN);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    double CURRENT_BALANCE = resultSet.getDouble("Balance");
                    if(CURRENT_BALANCE>=amount){
                        String r_query =  "SELECT * FROM Accounts WHERE Account_No = ?";
                        PreparedStatement ps_reciever = connection.prepareStatement(r_query);
                        ps_reciever.setLong(1,reciever_account_no);
                        if (ps_reciever.executeQuery().next()){
                            String debit_query = "UPDATE ACCOUNTS SET BALANCE = BALANCE - ?  WHERE Account_No = ? ";
                            String credit_query = "UPDATE ACCOUNTS SET BALANCE = BALANCE + ?  WHERE Account_No = ? ";

                            PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
                            PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);

                            creditPreparedStatement.setDouble(1, amount);
                            creditPreparedStatement.setLong(2, reciever_account_no);
                            debitPreparedStatement.setDouble(1, amount);
                            debitPreparedStatement.setLong(2, sender_account_no);
                            int rowsAffected1 = debitPreparedStatement.executeUpdate();
                            int rowsAffected2 = creditPreparedStatement.executeUpdate();
                            if(rowsAffected1 >0 && rowsAffected2>0){
                                System.out.println("Transaction Successful!");
                                System.out.println("Transferred Successfully");
                                connection.commit();
                                connection.setAutoCommit(true);
                                return;
                            }
                            else {
                                System.out.println("Transaction Failed!");
                                connection.rollback();
                                connection.setAutoCommit(true);
                            }

                        }
                        else System.out.println("Transaction Failed! Reciever Account Not Found");
                    }
                    else System.out.println("Insufficient Balance!");
                }
                else System.out.println("Invalid PIN");
            }
            else System.out.println("Invalid Account No");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Credit_Money(long Account_no) throws SQLException, ClassNotFoundException {
        sc.nextLine();
        System.out.println("Enter Amount :");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter PIN :");
        String pin = sc.nextLine();

        try{
            connection.setAutoCommit(false);
            if(Account_no>0){
                String query = "SELECT * FROM Accounts WHERE Account_No = ? and PIN = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,Account_no);
                preparedStatement.setString(2,pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    String credit_query = "UPDATE ACCOUNTS SET BALANCE = BALANCE + ?  WHERE Account_No = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
                    preparedStatement1.setDouble(1,amount);
                    preparedStatement1.setLong(2,Account_no);
                    int affected_rows = preparedStatement1.executeUpdate();
                         if(affected_rows>0){
                             System.out.println("Credit Successfully of RS." + amount);
                             connection.commit();
                             connection.setAutoCommit(true);
                             return;
                         }
                         else{
                             System.out.println("Transaction Failed");
                             connection.rollback();
                             connection.setAutoCommit(true);
                         }

                }
                else System.out.println("Invalid Security PIN");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void Debit_Money(long Account_no) throws SQLException, ClassNotFoundException {
        sc.nextLine();
        System.out.println("Enter Amount :");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter PIN :");
        String pin = sc.nextLine();
        try{
            connection.setAutoCommit(false);
            if(Account_no>0){
                String query = "SELECT * FROM Accounts WHERE Account_No = ? and PIN = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,Account_no);
                preparedStatement.setString(2,pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    double current_balance = resultSet.getDouble("balance");
                    if(current_balance>=amount){
                        String debit_query = "UPDATE ACCOUNTS SET BALANCE = BALANCE - ?  WHERE Account_No = ? ";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1,amount);
                        preparedStatement1.setLong(2,Account_no);
                        int affected_rows = preparedStatement1.executeUpdate();
                        if(affected_rows>0){
                            System.out.println("Debit Successfully of RS"+ amount);
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }
                        else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }
                    else System.out.println("Insufficient Balance");
                }
                else System.out.println("Invalid Security PIN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Get_Balance(long Account_no) {
        sc.nextLine();
        System.out.println("Enter your Pin: ");
        String pin = sc.nextLine();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select BALANCE from ACCOUNTS where account_no = ? AND PIN = ?");
            preparedStatement.setLong(1,Account_no);
            preparedStatement.setString(2,pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("BALANCE: "+balance);
            }
            else System.out.println("Incorrect pin");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}