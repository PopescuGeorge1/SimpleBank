import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Branch {
    private final String branchName;
    private final HashMap<Integer, Customer> keyChain;
    private Bank bank;
    private String branchDirectoryName;

    public Branch(Bank b,String branchName){
        this.branchName=branchName;
        this.bank=b;
        b.addBankBranch(this);
        createBranchFolder();
        keyChain =new HashMap<>();//not sure how needed is now
    }



    private void createBranchFolder(){
        String path = System.getProperty("user.dir") + "\\" + bank.getBankName() + "\\" + this.branchName;
        branchDirectoryName = path.toString();
        File directory = new File(branchDirectoryName);
        if (! directory.exists()) {
            directory.mkdir();
        }
    }

    public void addBranchCustomer(Customer c) {
        //find file, get data
        ArrayList<String> testList = new ArrayList<>();
        String path = System.getProperty("user.dir") + "\\" + bank.getBankName() + "\\" + this.branchName;
        Scanner scan = null;
        try {
            scan = new Scanner(new BufferedReader(new FileReader(path + ".txt")));
            while (scan.hasNextLine()) {
                String input = scan.nextLine();
//                System.out.println(input);
                String testData = input;
                testList.add(testData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //rewrite old
        FileWriter fw = null;
        try {
            fw = new FileWriter(path + ".txt");
            for(int i=0;i<testList.size();i++){
                String testDatastr = testList.get(i);
                fw.write(testDatastr);
                fw.write("\n");
            }
            //write new
            if(!isBranchCustomer(testList,c))
                c.saveToDoc(fw, c);
            fw.close();
            scan.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean isBranchCustomer(ArrayList<String> arr, Customer c){
        String name, number, address;
        String cAddress=c.getCustomerAddress()+" ";
        String cName = c.getCustomerName()+" ";
        String cNumber = c.getCustomerNumber()+" ";
        String []tempName, tempAddress, tempNumber;
        boolean ok=false;
        String []strArr;
        for(int i=1;i<arr.size();i++) {
            name = "";
            number = "";
            address = "";
            strArr = arr.get(i).split(",");
            tempName = strArr[1].split(" ");
            tempAddress = strArr[2].split(" ");
            tempNumber = strArr[3].split(" ");
            for (int k = 1; k < tempName.length; k++)
                name += tempName[k] + " ";
            for (int k = 1; k < tempAddress.length; k++)
                address += tempAddress[k] + " ";
            for (int k = 3; k < tempNumber.length; k++)
                number += tempNumber[k]+" ";
            if (address.equalsIgnoreCase(cAddress) && name.equalsIgnoreCase(cName) && number.equalsIgnoreCase(cNumber))
                ok = true;
        }
        return ok;
    }

    public void getBranchCustomer(int account){
        if(isCustomer(account))
            keyChain.get(account).getCustomerData(account);
        else
            System.out.println("Searched customer was not found");
        //should get customer from the csv (if console starts new, the buffer is empty so what client will it get???)


    }

    public void printAllBranchCustomers() {
        for (Integer i : keyChain.keySet()) {
            keyChain.get(i).checkBalance();
        }
    }

    public void deleteCustomerAccount(int customerAccount){
        if(isCustomer(customerAccount)) {
            keyChain.get(customerAccount).removeCustomer(customerAccount);
            keyChain.remove(customerAccount);
        }
        else
            System.out.println("Cannot perform operation \n Customer not found");
    }

    public void saveToBranchDoc() {
        ArrayList<String[]>list = new ArrayList<>();
        String directoryPath = System.getProperty("user.dir")+"\\"+"FirstBank\\"+getBranchName();
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(directoryPath+".txt"/*getBranchName()*//*+".txt"*/)));
            while(scan.hasNextLine()) {
                String input = scan.nextLine();
                String[] data = input.split(",");
                list.add(data);
            }
            FileWriter fw = new FileWriter(getBranchName()+".txt");
            for(String[] i : list){
                fw.write("Customer account: "+i[0]+","+"Name: "+i[1]+","+" Adress: "+
                        i[2]+","+"Customer phone contact: "+i[3]+
                        ","+"Balance: "+i[4]+"\n");

            }
            for (Integer i : keyChain.keySet())
//                keyChain.get(i).saveToDoc(fw, keyChain.get(i).getCustomerAccount());

            fw.close();
            scan.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        //upgrade: if customer account already there, don't add it again
    }

    public void readBranchData(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(getBranchName()+"customerData.txt")));
            while(scanner.hasNextLine()){
                String input = scanner.nextLine();
                String []data = input.split(",");
                System.out.println(data[0]+", "+data[1]+", "+data[2]+", "+data[3]+", "+data[4]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(scanner !=null) {
                scanner.close();
            }
        }
    }

    public void customerUpdate(int customerAccount){
        if(isCustomer(customerAccount))
            keyChain.get(customerAccount).updateCustomerData();
        else
            System.out.println("Customer not found");
    }

    public void findBranchCustomer (int account){
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(getBranchName() + "customerData.txt")));
            String input = scan.nextLine();
            String []data = input.split(",");
            if(account==Integer.parseInt(data[0])){
                //create to use then delete customer??
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean isCustomer(int account){
        if (keyChain.containsKey(account))
            return true;
        else
            return false;
    }

    public String getBranchName() {
        return branchName;
    }

}
