
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.Arrays;

public class Inventory {
    private String[][] products; // use 2D array instead of ArrayList
    private int numProducts; // keep track of number of products
    private int productCount = 0;
    public Inventory() {
        // read number of lines in file to determine size of products array
        int numLines = 0;
        try {
            InputStream is = getClass().getResourceAsStream("inventory.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (reader.readLine() != null) {
                numLines++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to read products from file.");
        }
        // initialize products 2D array
        products = new String[numLines][3];
        // read products from file (if any)
        readProductsFromFile();
    }


    public void addItem(String name, double price, int quantity) {
        // add new product to the products 2D array
        products[numProducts][0] = name;
        products[numProducts][1] = Double.toString(price);
        products[numProducts][2] = Integer.toString(quantity);
        numProducts++;
        // check if products 2D array should be grouped
        if (numProducts % 10 == 0) {
            groupProductsToFile();
        }
        // write products to file
        writeProductsToFile();
    }

    public void removeItem(String name) {
        // find the product with the given name and remove it
        for (int i = 0; i < numProducts; i++) {
            if (products[i][0].equals(name)) {
                // shift all the products after this product one index to the left
                for (int j = i; j < numProducts - 1; j++) {
                    products[j] = products[j + 1];
                }
                numProducts--;
                // write products to file
                writeProductsToFile();
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void updateItem(String name, double price, int quantity) {
        // find the product with the given name and update its quantity and price
        for (int i = 0; i < numProducts; i++) {
            if (products[i][0].equals(name)) {
                products[i][1] = Double.toString(price);
                products[i][2] = Integer.toString(quantity);
                // write products to file
                writeProductsToFile();
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public String[] getItem(String name) {
        // return information of product with given name
        for (int i = 0; i < numProducts; i++) {
            if (products[i][0].equals(name)) {
                return products[i];
            }
        }
        System.out.println("Item not found in inventory.");
        return null;
    }

    public void displayInventory() {
        // display all products in inventory
        for (int i = 0; i < numProducts; i++) {
            String[] product = products[i];
            System.out.println(product[0] + ": " + product[2] + " Available" + " for $" + product[1] + " each");
        }
    }

    void readProductsFromFile() {
        try {
            // open file for reading
            BufferedReader reader = new BufferedReader(new FileReader("/Users/shamaamahahmad/Desktop/inventory.txt"));
            String line;
            // read each line and add the product to the products 2D array
            while ((line = reader.readLine()) != null) {
                String[] product = line.split(",");
                products[numProducts] = product;
                numProducts++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to read products from file.");
        }
    }

    private void writeProductsToFile() {
        try {
            // open file for writing
            PrintWriter writer = new PrintWriter(new FileWriter("/Users/shamaamahahmad/Desktop/inventory.txt"));
            // write each product to the file in CSV format
            for (int i = 0; i < numProducts; i++) {
                String[] product = products[i];
                writer.println(String.join(",", product));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write products to file.");
        }
    }

    private void groupProductsToFile() {
        try {
            // open file for writing
            PrintWriter writer = new PrintWriter(new FileWriter("/Users/shamaamahmad/Desktop/inventory_grouped.txt"));
            // sort products by name
            Arrays.sort(products, 0, numProducts, (p1, p2) -> p1[0].compareTo(p2[0]));
            // group products by name and write them to file in CSV format
            String[] currentGroup = null;
            for (int i = 0; i < numProducts; i++) {
                String[] product = products[i];
                if (currentGroup == null || !currentGroup[0].equals(product[0])) {
                    if (currentGroup != null) {
                        writer.println(String.join(",", currentGroup));
                    }
                    currentGroup = new String[] { product[0], product[1], product[2] };
                } else {
                    double totalPrice = Double.parseDouble(currentGroup[1]) + Double.parseDouble(product[1]);
                    int totalQuantity = Integer.parseInt(currentGroup[2]) + Integer.parseInt(product[2]);
                    currentGroup[1] = Double.toString(totalPrice);
                    currentGroup[2] = Integer.toString(totalQuantity);
                }
            }
            if (currentGroup != null) {
                writer.println(String.join(",", currentGroup));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to group products to file.");
        }
    }
}