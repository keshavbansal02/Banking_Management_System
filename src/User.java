import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner sc;

    public User(Connection connection,Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public void register(){
        sc.nextLine();
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        if(user_exist(email)){
            System.out.println("That email already exists!");
            return;
        }

        String register_query = "INSERT INTO USER (FULL_NAME,EMAIL,PASSWORD) VALUES (?,?,?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Registration Successfully!");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String login(){
        sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        String Login_Query =  "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Login_Query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
             ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
              return email;
            }
            else return null;
        }
        catch (SQLException e){
        e.printStackTrace();
        }
        return null;
    }

    public boolean user_exist(String email){
        String query = "SELECT * FROM USER WHERE EMAIL = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}