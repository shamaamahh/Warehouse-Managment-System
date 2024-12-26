import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainMenu {
    Login login;
    Customer customer;

    private String username;
    public String[][] itemsArr;
    public static String[][] userDataArray;
    public String[][] cartArr;

    

    public void SaveArrayToFile(String[][] array, String filename)
    {
        
            File file = new File(filename);
            String dataString = "";
            for (int x = 0; x < array.length; x++)
            {
                for(int y = 0; y < array[x].length; y++)
                {
                    dataString += array[x][y] + ",";
                }
                dataString += (";" + "\n");
            }
            //System.out.println("DataString: " + dataString);
            try
            {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(dataString);
                myWriter.close(); 

            } catch (IOException e)
            {
                System.out.println("File not found!");
            }

                
                
            

        
        //catch (IOException e)
        //{
          //  System.out.println("File not found!");
        //}
    }

    public void Cart()
    {   
        cartArr = cartData();
        
        String dataString = "";
        int x = 0;
        while (x < cartArr.length)
        {
            String input = JOptionPane.showInputDialog (null, "How many units of " + cartArr[x][0] + " would you like to purchase", "Amount", JOptionPane.INFORMATION_MESSAGE);
            cartArr[x][3] = input;
            double stock = Double.parseDouble(cartArr[x][2]);
            double useramount = Double.parseDouble(cartArr[x][3]);
            if (useramount > stock)
            {
                JOptionPane.showMessageDialog(null, "Attempting to Purchase more than available stock!");
            }
            else
            {
                x++;
            }
        }
        for (int p = 0; p < cartArr.length; p++)
        {
            for (int z = 0; z < itemsArr.length; z++)
            {
                String cartItem = cartArr[p][0];
                String inventorycheckString = itemsArr[z][0];
                double stock = Double.parseDouble(cartArr[p][2]);
                double useramount = Double.parseDouble(cartArr[p][3]);
                if (cartItem.equals(inventorycheckString))
                {
                    //System.out.println("Item Found!");
                    double remainingItems = stock - useramount;
                    String remainderString = Double.toString(remainingItems);
                    itemsArr[z][2] = remainderString;
                    //needs to save the items array back into the file and refresh the table
                    break;
                }
                //System.out.println(cartArr[p][z]);
            }
        }
        double totalAmount = 0; 
        for (int row = 0; row < cartArr.length; row++)
        {
            double priceDouble = Double.parseDouble(cartArr[row][1]);
            double amountDouble = Double.parseDouble(cartArr[row][3]);
            double totalForProduct = priceDouble * amountDouble;
            totalAmount += totalForProduct;
        }
        System.out.println(totalAmount);
        JOptionPane.showMessageDialog(null, "Total: " + totalAmount);
        try{
            String purchaseString = Double.toString(totalAmount);
            FileWriter clearCart = new FileWriter("cart.txt", false);
            for (int i = 0; i < userDataArray.length; i++)
            {
                if (username.equals(userDataArray[i][0]))
                {
                    if (userDataArray[i][3].equals("NULL"))
                    {
                        userDataArray[i][3] = purchaseString;
                        String filename = "userData.txt";
                        SaveArrayToFile(userDataArray, filename);
                    }
                    else if (!userDataArray[i][3].equals("NULL"))
                    {
                        Double previouspurchasesamt = Double.parseDouble(userDataArray[i][3]);
                        Double added = totalAmount + previouspurchasesamt;
                        purchaseString = Double.toString(added);
                        userDataArray[i][3] = purchaseString;
                        String filename = "userData.txt";
                        SaveArrayToFile(userDataArray, filename);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Cart File Not Found!");
        }

    }


    public String[][] cartData()
    {
        String datastr = "";
        String[][] result;
        try{
            File cartFile = new File("cart.txt");
            FileReader fr = new FileReader(cartFile);
            Scanner myReader = new Scanner(fr);
            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();
                datastr += data;
            }
            myReader.close();
            String[] cartRows = datastr.split(";");
            result = new String[cartRows.length][2];
            for (int i = 0; i < cartRows.length; i++)
            {
                String[] columns = cartRows[i].split(",");
                result[i] = new String[columns.length];
                for (int j = 0; j < columns.length; j++) 
                {
                result[i][j] = columns[j];
                }
                cartArr = result;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return cartArr;
        

        
    }
    public String[][] inventoryData(String data) {
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

    public void InventoryData() {
        String dataString = "";
        try {// need to add file creation stuff too
            File inventoryFile = new File("inventory.txt");
            FileReader fr = new FileReader(inventoryFile);
            Scanner myReader = new Scanner(fr);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                // System.out.println(data);
                dataString += data;
            }
            myReader.close();
            itemsArr = inventoryData(dataString);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }

    }

    public MainMenu(String username, String manager) {
        this.username = username;
        if (manager.equals("Manager")) {
            System.out.println(username + " is a Manager");
            ManagerMenu(username);
        } else if (manager.equals("Customer")) {
            System.out.println(username + " is a Customer");
            CustomerMenu(username);

        } else {
            System.out.println("error");
        }
    }

    public double getPrice(String item) {
        for (int x = 0; x < itemsArr.length; x++) {
            if (itemsArr[x][0].equals(item)) {
                System.out.println("Match Found!");
            }
        }
        return 1.0;
    }

    public void CustomerMenu(String username) {
        InventoryData();

        JFrame customerFrame = new JFrame("Customer Menu");
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        String[] columnNames = { "Item", "Price", "Stock", "Add" };
        DefaultTableModel model = new DefaultTableModel(itemsArr, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return Boolean.class;
                }
                return super.getColumnClass(column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JTable customerItemTable = new JTable(model);
        customerItemTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        customerItemTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        customerItemTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        customerItemTable.getColumnModel().getColumn(3).setPreferredWidth(20);

        customerItemTable.setDefaultRenderer(Boolean.class, new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(JLabel.CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JCheckBox checkbox = new JCheckBox();
                if (value instanceof Boolean) {
                    checkbox.setSelected((Boolean) value);
                }
                return checkbox;
            }
        });
        customerItemTable.setDefaultEditor(Boolean.class, new DefaultCellEditor(new JCheckBox()));
        JScrollPane customerItemTableScrollPane = new JScrollPane(customerItemTable);
        
        panel1.add(customerItemTableScrollPane);

        JButton addToCartButton = new JButton("add to cart");
        addToCartButton.addActionListener(new ActionListener()  //needs to be able to add to the text file as well
        {
            public void actionPerformed(ActionEvent e) 
            {
                try{
                    File cartFile = new File("cart.txt");
                    if (!cartFile.exists())
                    {
                        cartFile.createNewFile();
                    }
                    FileWriter writer = new FileWriter(cartFile);
                    for (int i = 0; i < customerItemTable.getRowCount(); i++)
                    {
                       
                        Object value = customerItemTable.getValueAt(i, 3);
                        if (value instanceof Boolean && value != null) 
                        {
                            boolean selected = ((Boolean) value).booleanValue();
                            System.out.println(selected);
                            String item = (String) customerItemTable.getValueAt(i, 0);
                            String price = (String) customerItemTable.getValueAt(i, 1);
                            String stock = customerItemTable.getValueAt(i, 2).toString();
                            writer.write(item + ", " + price + ", " + stock + ", AMOUNT" + ";\n");
                        }
                            
                    }
                
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Items added to cart.");
                    Cart();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding items to cart.");
                }
            }
        });




        panel1.add(Box.createVerticalStrut(10));
        panel1.add(Box.createVerticalGlue());
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(addToCartButton);
        buttonBox.add(Box.createHorizontalGlue());
        panel1.add(buttonBox);
        panel1.add(Box.createVerticalGlue());
        //panel1.add(Box.createHorizontalGlue());

        



        JPanel panel3 = new JPanel();


        //this should use the Customer.java class and resave the UserArr into the User.txt file 
        JButton changeUsernameButton = new JButton("Change username");
        changeUsernameButton.setBounds(0, 600, 130, 30);
        changeUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer customer = new Customer();
                String newName = JOptionPane.showInputDialog(null, "Enter your new Username: ", "Username", JOptionPane.INFORMATION_MESSAGE);
                customer.setName(newName);//need to recode the customer part so that it saves it into the array
                
            }
        });

        JButton changePasswordButton= new JButton("Change Password");
        changePasswordButton.setBounds(0, 600, 130, 30);
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Customer customer = new Customer(username);
                String password = JOptionPane.showInputDialog(null, "Enter your new Password: ", "Username", JOptionPane.INFORMATION_MESSAGE);
                //needs to use jeremy's code to set password
                String name = JOptionPane.showInputDialog(null, "Enter your new Password: ", "Username", JOptionPane.INFORMATION_MESSAGE);
            }
            
        });




        JButton customerLogoutButton = new JButton("Quit");
        customerLogoutButton.setBounds(20, 400, 130, 30);
        customerLogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel3.add(changeUsernameButton);
        panel3.add(changePasswordButton);
        panel3.add(customerLogoutButton);
        



        tabbedPane.addTab("Purchase", panel1);
        tabbedPane.addTab("Account", panel3);

        customerFrame.getContentPane().add(tabbedPane);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setSize(1000, 1000);
        customerFrame.setVisible(true);
    }


    public void ManagerMenu(String username) {
        InventoryData();
        JFrame managerFrame = new JFrame("Manager Menu");

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        tabbedPane.addTab("Restock Items", panel1);
        tabbedPane.addTab("Sales Report", panel2);
        tabbedPane.addTab("Customer Accounts", panel3);

        managerFrame.getContentPane().add(tabbedPane);
        managerFrame.setSize(500, 300);
        managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerFrame.setVisible(true);
        managerFrame.setLocationRelativeTo(null);

        createRestockTab(panel1);
        createSalesReportTab(panel2);
        createAccountAccessTab(panel3);
    }

    private void createAccountAccessTab(JPanel panel) {
        ManagerControls mc = new ManagerControls(userDataArray);

        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Name", "Password", "Reward"};
        Object[][] data = mc.getCustomerInfo();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createSalesReportTab(JPanel panel) {
        panel.setLayout(new BorderLayout());

        ManagerControls mc = new ManagerControls(userDataArray);
        JLabel totalSalesLabel = new JLabel(String.format("Total Sales: $%.2f", mc.getTotalSales()));
        panel.add(totalSalesLabel, BorderLayout.NORTH);

        String[] columnNames = {"Customer", "Sales"};
        Object[][] data = mc.getCustomerSales();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createRestockTab(JPanel panel) {
        JOptionPane.showMessageDialog(null,
                "Click each row to restock item");

        String[] columns = new String[]{"Name", "Price", "Quantity"};
        TableModel model = new SimpleTableModel(itemsArr, columns);
        JTable table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                String name = (String) model.getValueAt(selectedRow, 0);
                double price = Double.valueOf(model.getValueAt(selectedRow, 1).toString().trim());
                int quantity = Integer.valueOf(model.getValueAt(selectedRow, 2).toString().trim());
                System.out.println(selectedRow);

                String input = (String) JOptionPane.showInputDialog(null, "Input restock quantity, must be a positive number",
                        "Restock " + name, JOptionPane.YES_NO_CANCEL_OPTION, null, null, null);
                if (input != null) {
                    try {
                        int added = Integer.parseInt(input.trim());
                        if (added > 0) {
                            quantity += added;
                            itemsArr[selectedRow][2] = String.valueOf(quantity);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Invalid input");
                        }
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid input");
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public void MainMenuSetup(String username, String manager) {
        JFrame mainMenuFrame = new JFrame("Main Menu");

        if (manager.equals("Manager")) {
            ManagerMenu(username);
        } else if (manager.equals("Customer")) {
            CustomerMenu(username);
        }

        mainMenuFrame.setSize(500, 300);
        mainMenuFrame.setVisible(true);
    }

    class SimpleTableModel extends AbstractTableModel {
        private Object[][] data;
        private String[] columns;

        public SimpleTableModel(Object[][] data, String[] columns) {
            this.data = data;
            this.columns = columns;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    public static void main(String[] args) {
        Login login = new Login();
        userDataArray = login.userDataArr;

        System.out.println("Hello");
    }

}
