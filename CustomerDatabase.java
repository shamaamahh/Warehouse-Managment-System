import java.util.*;

import java.io.File;  
import java.io.FileWriter;  
import java.io.IOException;

import java.util.Scanner;
import java.io.FileNotFoundException;


public class CustomerDatabase
{
	//declaration
	Customer[] customerArray;

	public CustomerDatabase(){};

	public void clearAccounts(){
		customerArray = new Customer[0];
		SaveAccounts();
    }

	public void addAccount(String name, String password, String accountType){
		LoadAccounts();
		boolean containsAccount = false;
		if (containsAccount == false){

			Customer newCustomer = new Customer(name, password, accountType);

			int tempLength = customerArray.length;
			Customer[] tempArray = customerArray.clone();

			customerArray = new Customer[tempLength + 1];
			for (int i = 0; i < tempLength; i++){
				customerArray[i] = tempArray[i];
			}
			customerArray[tempLength] = newCustomer;
		} else {
			//Need to display some sort of error message for that password being used
        }

		SaveAccounts();
    }


	public void removeAccount (String password){
		LoadAccounts();
	
		int tempLength = customerArray.length;
		Customer[] tempArray = customerArray.clone();
		int tempID = 0;

		customerArray = new Customer[tempLength - 1];
		for (int i = 0; i < tempLength; i++){
			if (i != tempID){
				customerArray[tempID] = tempArray[i];
				tempID++;
			}
        }

		SaveAccounts();
    }

	public Customer getAccount(String checkPassword){
		LoadAccounts();
		System.out.println("Length = " + customerArray.length);

		Customer toReturn;
		int returnID = -5;
		for (int i = 0; i < customerArray.length; i++){
			if (customerArray[i].getPassword().equals(checkPassword)){
				//System.out.println("i = " + i + "; Password = " + customerArray[i].getPassword() + "; checkPassword = " + checkPassword);
				returnID = i;
            }
        }

		//This is a temporary failsafe in case the password does not exist
		if (returnID == -5){
			toReturn = new Customer("failsafe", "failsafe", "failsafe");
        } else {
			toReturn = customerArray[returnID];
        }

		return toReturn;
    }



	public void SaveAccounts(){
		try {
            File myObj = new File("userData.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
			return;
        }

		String toSave = CompileAsString();
		System.out.println("toSave = " + toSave);

        try {
            FileWriter myWriter = new FileWriter("userData.txt");
            myWriter.write(toSave);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
		
    }

	public void LoadAccounts(){
		String data = "";

        try {
            File myObj = new File("userData.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
			return;
        }

		System.out.println("Data = " + data);
		LoadFromString(data);
    }

	private String CompileAsString(){
		String toReturn = "";

		for (int i = 0; i < customerArray.length; i++){
			toReturn += customerArray[i].getName() + "," + customerArray[i].getPassword() + "," + customerArray[i].accountType + ",";
			if (customerArray[i].accountType.equals("Manager")){
				toReturn += "NULL;";
            } else {
				toReturn += customerArray[i].getAmountspent() + ";";
            }
        }

		return toReturn;
    }

	private void LoadFromString(String data){
		String[] dataArray = data.split(";");
		customerArray = new Customer[dataArray.length - 1];

		for (int i = 1; i < dataArray.length; i++){
			String[] components = dataArray[i].split(",");
			if (components[2].equals("Manager")){
				customerArray[i-1] = new Customer(components[0], components[1], components[2], 0);
			} else {
				customerArray[i-1] = new Customer(components[0], components[1], components[2], Double.parseDouble(components[3]));
            }
        }
    }
}