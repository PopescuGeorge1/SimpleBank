import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Customer {


    private class customerInfo {
        private String name;
        private String number;
        private String address;
        private double balance;

        private customerInfo(String name, String number, String address, double balance){
            this.name=name;
            this.number=number;
            this.address=address;
            this.balance=balance;
        }

        private void addTrans(double amount){
            this.balance+=amount;
        }

        private void updateData(){
            Scanner scan = new Scanner(System.in);
            boolean quit =false;
            String option;
            while(!quit){
                option=scan.nextLine();
                switch (option){
                    case "quit":quit=true;
                    break;
                    case "name":this.name = scan.nextLine();
                    break;
                    case"number":this.number = scan.nextLine();
                    break;
                    case"address":this.address=scan.nextLine();
                    break;
                    default:quit=true;
                }
            }
        }

        public void display(){
            System.out.println("Name: "+this.name+"\n"+"Balance: "+this.balance);
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public String getAddress() {
            return address;
        }

        public double getBalance() {
            return balance;

        }
    }


    private int customerAccount=1;
    private HashMap<Integer, customerInfo>cont = new HashMap<>();
    private customerInfo cInfo;
    private ArrayList<Double>transactions;
    private Branch branch;

    public Customer(Branch br, String name, String number, String address, double initialBalance){
        cInfo=new customerInfo(name, number, address, initialBalance);
        this.customerAccount=accountGen();
        cont.put(this.customerAccount,cInfo);
        transactions=new ArrayList<>();
        transactions.add(initialBalance);
        br.addBranchCustomer(this);
    }

    public void depositAmount(double amount){
        cont.get(getCustomerAccount()).addTrans(amount);
        transactions.add(amount);
    }

    public void withdrawAmount(double amount){
    //nush daca tre realizata operatiunea in functie de obiect sau sa adaug si contul ca sa verific daca e cine zice ca e
    //obiectu ar actiona precum cardul dar contul sau pinul ar fi o masura de securitate
    //cred ca voi adauga un PIN legat de cont si voi cere pinul la fiecare tranzactie (ca in realitate)
        if(cInfo.getBalance()+amount<0)
            System.out.println("We are sorry but your balance if insufficient for the chosen transaction");
        else {
            cont.get(getCustomerAccount()).addTrans(-amount);
            transactions.add(amount);
        }
    }

    public void checkBalance(){
        try {
            System.out.println("=================================");
            System.out.print("Account: "+this.customerAccount+"\n");
            cont.get(this.customerAccount).display();
            System.out.println("Transactions: ");
            for(double d : transactions)
                System.out.println("   -> "+d);
            System.out.println("=================================");
        }
        catch (InputMismatchException e){
            System.out.println("iar eroare");
        }
        //mai vezi catch/throw error
    }

//    public void saveToDoc(){
//        try{
//            fw=new FileWriter("customerData.txt");
//            fw.write(this.customerAccount+","+cInfo.getName()+","+cInfo.getBalance()+" ");
//            fw.close();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
        //ca sa functioneze tre inchis in acelasi block try{}
        //la fiecare scriere, rescrie tot documentul
        //=>tre metoda de scriere si de citire
        //concluzie de moment: pt update tre folosita functia de rescriere
        //nu functioneaza varianta cu utilizarea metodei de scriere cu fiecare obiect
        //  efectiv rescrie documentul => update data apoi saveToDoc
//    }

    public void getCustomerData(int customerAccount){

        System.out.println("=================================");
        System.out.println("Name: " + cInfo.getName() + "\n" +
                "Account: " + this.customerAccount + "\n" +
                "Phone Number: " + cInfo.getNumber() + "\n" +
                "Address: " + cInfo.getAddress() + "\n" +
                "Balance: " + cInfo.getBalance());
        System.out.println("=================================");

    }

    public void removeCustomer(int customerAccount){
        cont.remove(customerAccount);
        transactions.clear();
    }

    private int accountGen(){
        int x;
        for (int i=0;i<9;i++){
            x=(int)(Math.random()*9+1);
            this.customerAccount=this.customerAccount*10+x;
        }
        return (Math.abs(this.customerAccount));
    }

    public void updateCustomerData(){
        cInfo.updateData();
    }

    public int getCustomerAccount() {
        return customerAccount;
    }

    public String getCustomerAddress(){
        return cInfo.address;
    }
    public String getCustomerName(){
        return cInfo.name;
    }
    public String getCustomerNumber(){
        return cInfo.number;
    }


    public void saveToDoc(FileWriter fw, Customer c) {
        try {

//                String input = scan.nextLine();
//                String[] data = input.split(",");
//                fw.write("Customer account: "+data[0]+","+"Name: "+data[1]+","+" Adress: "+
//                        data[2]+","+"Customer phone contact: "+data[3]+
//                        ","+"Balance: "+data[4]+"\n");


                fw.write("Customer account: "+c.customerAccount+","+"Name: "+c.cInfo.getName()+","+"Adress: "+
                        c.cInfo.getAddress()+","+"Customer phone contact: "+c.cInfo.getNumber()+
                        ","+"Balance: "+c.cInfo.getBalance()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
