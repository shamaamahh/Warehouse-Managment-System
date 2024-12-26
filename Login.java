
/*
 * Harry Newman; Final Project; Login Class; May 12, 2023;
 */

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;



public class Login {

    String[][] userDataArr;
    Boolean loggedin = false;

    //login setup class
    public Login() 
    {
        userData();
        System.out.println("Logged in: " + loggedin);
        if (loggedin != true)
        {
            loginFrame();
        }
        
        
    }

    //adds account after new account is created
    public void addAccount(String data) {
        System.out.println(data);
        try {
            File userDataFile = new File("userData.txt");
            if (userDataFile.createNewFile()) {
                System.out.println("File created: " + userDataFile.getName());
            } else {
                System.out.println("File already exists.");
                try {
                    FileWriter myFileWriter = new FileWriter(userDataFile.getAbsoluteFile(), true);
                    myFileWriter.write("\n" + data);
                    myFileWriter.close();
                    System.out.println("File Written Successfully");
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    //takes data from userData and returns it as string
    public String[][] userData(String data) {
        String[] rows = data.split(";");
        String[][] result = new String[rows.length][2];
        for (int i = 0; i < rows.length; i++) {
            String[] columns = rows[i].split(",");
            result[i] = new String[columns.length];
            for (int j = 0; j < columns.length; j++) {
                result[i][j] = columns[j];
            }
        }
        return result;
    }

    public void userData() // needs to read data and return it as a string[][] to test for login
    {
        String dataString = "";
        try {
            File userDataFile = new File("userData.txt");
            if (userDataFile.createNewFile()) {
                System.out.println("File created: " + userDataFile.getName());
            } else {
                System.out.println("File already exists.");
                try {
                    Scanner myReader = new Scanner(userDataFile);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        dataString += data;
                        // System.out.println(data);
                    }
                    myReader.close();
                    userDataArr = userData(dataString);
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //creates the GUI for acccount creation
    public void createAccount() 
    {

        JFrame createAccountFrame = new JFrame("Account Creation");

        JTextField usernameField = new JTextField();
        usernameField.setToolTipText("username");
        usernameField.setBounds(125, 40, 250, 30);
        createAccountFrame.add(usernameField);

        JTextField passwordField = new JTextField();
        passwordField.setToolTipText("password");
        passwordField.setBounds(125, 70, 250, 30);
        createAccountFrame.add(passwordField);

        String[] morc = {"Manager", "Customer"}; 
        JComboBox managerBox = new JComboBox(morc);
        managerBox.setBounds(125, 100, 250, 30);
        createAccountFrame.add(managerBox);
        
        JButton enterButton = new JButton("Enter");
        enterButton.setBounds(125, 130, 250, 30);
        createAccountFrame.add(enterButton);
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String manager = (String)managerBox.getSelectedItem();
                String newuserdata = username + "," + password + "," + manager + ",NULL" + ";";
                addAccount(newuserdata);
                Login newaccount = new Login();// see if i can make this better
                createAccountFrame.dispose();
            }
        });

        createAccountFrame.setSize(500, 300);
        createAccountFrame.setLayout(null);
        createAccountFrame.setVisible(true);
    }


    //creates the login frame for loging in
    public void loginFrame()
    {
        JFrame loginFrame = new JFrame("Inventory Program Login");

        JTextArea loginTextArea = new JTextArea("Welcome");
        loginTextArea.setBounds(225, 20, 250, 30);
        loginTextArea.setOpaque(false);
        loginTextArea.setEditable(false);
        loginTextArea.setLineWrap(false);
        loginTextArea.setWrapStyleWord(false);
        loginFrame.add(loginTextArea);

        JTextField usernameField = new JTextField();
        usernameField.setToolTipText("username");
        usernameField.setBounds(125, 40, 250, 30);
        loginFrame.add(usernameField);

        JTextField passwordField = new JTextField();
        passwordField.setToolTipText("password");
        passwordField.setBounds(125, 70, 250, 30);
        loginFrame.add(passwordField);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(150, 100, 200, 30);
        loginFrame.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {// also want it to clear the text in the fields
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                for (int row = 0; row < userDataArr.length; row++) {
                    if (username.equals(userDataArr[row][0])) {
                        if (password.equals(userDataArr[row][1])) 
                        {
                            String manager = userDataArr[row][2];
                            System.out.println("login successful");
                            loginFrame.dispose();
                            loggedin = true;
                            MainMenu mainMenuFrame = new MainMenu(username, manager);
                            //mainMenuFrame.MainMenuSetup();
                            break;
                        }
                        else
                        {
                            System.out.println("Incorrect Password");
                        }
                    }
                }
            }
        });

        JButton createAccountButton = new JButton("create account");
        createAccountButton.setBounds(150, 140, 200, 30);
        loginFrame.add(createAccountButton);
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount();
                loginFrame.dispose();
            }
        });

        JButton exitButton = new JButton("exit");
        exitButton.setBounds(150, 165, 200, 30);
        loginFrame.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginFrame.setSize(500, 300);
        loginFrame.setLayout(null);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    

}
