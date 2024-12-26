
public class ManagerControls {
    private String[][] userDataArray;

    public ManagerControls(String[][] userDataArray) {
        this.userDataArray = userDataArray;
    }

    public double getTotalSales() {
        double totalSales = 0;
        for (String[] userData : userDataArray) {
            if (userData.length > 3 && userData[3] != null) {
                try {
                    double sales = Double.parseDouble(userData[3]);
                    totalSales += sales;
                } catch (NumberFormatException e) {
                }
            }
        }

        return totalSales;
    }

    public Object[][] getCustomerInfo() {
        int size = 0;
        for (int i = 0; i < userDataArray.length; i++) {
            if (userDataArray[i][2].equals("Customer")) {
                size++;
            }
        }

        Object[][] res = new Object[size][3];

        int idx = 0;
        for (int i = 0; i < userDataArray.length; i++) {
            if (!userDataArray[i][2].equals("Customer")) {
                continue;
            }

            String[] userData = userDataArray[i];
            res[idx] = new Object[3];
            res[idx][0] = userData[0];
            res[idx][1] = userData[1];
            res[idx][2] = "None";

            idx++;
        }

        return res;
    }

    public Object[][] getCustomerSales() {
        Object[][] res = new Object[userDataArray.length][2];
        for (int i = 0; i < userDataArray.length; i++) {
            String[] userData = userDataArray[i];
            res[i] = new Object[2];
            res[i][0] = userData[0];
            if (userData.length > 3 && userData[3] != null) {
                try {
                    Double sales = Double.parseDouble(userData[3]);
                    res[i][1] = sales;
                } catch (NumberFormatException e) {
                    res[i][1] = 0;
                }
            } else {
                res[i][1] = 0;
            }
            res[i][1] = "$" + res[i][1];
        }

        return res;
    }

    public void salesReport(String name) {

    }
}
