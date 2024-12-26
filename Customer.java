import java.util.*;

public class Customer
{
	String name;
	String password;
	String accountType;
	double amountspent = 0;

	public Customer(){}

	public Customer(String newName, String newPassword, String newAccountType){
		name = newName;
		password = newPassword;
		accountType = newAccountType;
		amountspent = 0;
    }

	public Customer(String newName, String newPassword, String newAccountType, double newAmountSpent){
		name = newName;
		password = newPassword;
		accountType = newAccountType;
		amountspent = newAmountSpent;
    }


	public String getName(){
		return name;
    }
	public void setName(String newName){
		name = newName;
    }

	public String getPassword(){
		return password;
    }
	public void setPassword(String newPassword){
		password = newPassword;
    }

	public String getAmountspent(){
		return "" + amountspent;
    }

	public void setAmountspent(double newPurchase){
		amountspent += newPurchase;
    }
}