import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bank {
    private String bankName;
    private HashMap<String, Branch> bankBranches;
    private String directoryName;

    public Bank(String bankName) {
        this.bankName = bankName;
        directoryName=System.getProperty("user.dir")+"\\"+getBankName();
        bankBranches = new HashMap<>();
        if(!isBank(bankName))
            createBankFile(bankName);
    }

    public void addBankBranch(Branch b) {
        bankBranches.put(b.getBranchName(), b);
        if(!isBankBranch(b.getBranchName()))
            branchFileWrite(b.getBranchName());
    }

    public void getBranch(String branchName){
        //find branch
        String directoryPath = System.getProperty("user.dir")+"\\"+getBankName(); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files
        String branchPath = null;
        try {
            for (File f : filesInDirectory) {
                String filePath = f.getAbsolutePath();
                String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf(".")); //check only the name part, not the entire path
                if (branchName.equals(fileExtenstion)) {
//                System.out.println("CSV file found -> " + filePath); ok
                    branchPath = filePath;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //read branch
        ArrayList<String[]>readedData = new ArrayList<>();
        if(branchPath!=null)
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(branchPath /*getBranchName()*//*+".txt"*/)));
            while (scan.hasNextLine()) {
                String input = scan.nextLine();
                String[] data = input.split(",");
                readedData.add(data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //print to console
        for(String[] i : readedData){
            for (String s : i) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    public void getAllBankBranches(){
        String directoryPath = System.getProperty("user.dir")+"\\"+getBankName(); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files
        System.out.println(getBankName()+" branches:");
        for(File f : filesInDirectory) {
            String filePath = f.getAbsolutePath();
            String checkType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()); //check only the name part, not the entire path
            if (checkType.equalsIgnoreCase("txt")) {
               String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf(".")); //check only the name part, not the entire path
                if (!fileExtenstion.equals(getBankName()))
                    System.out.println(fileExtenstion);
            }
        }
    }

    public void deleteBranch(String branchName){
        //find branch
        String directoryPath = System.getProperty("user.dir")+"\\"+getBankName(); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files
        String branchPath = null;
        String branchDirPath = null;
        for(File f : filesInDirectory) {
            String filePath = f.getAbsolutePath();
            String checkType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            //check file type -csv
            if (checkType.equalsIgnoreCase("txt")) {
                String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.lastIndexOf("."));
                //check file name
                if (branchName.equals(fileExtenstion)) {
                    branchPath=filePath;
                }
            }
            //check file type - folder
            if (!checkType.equalsIgnoreCase("txt")) {
                String branchFolderExtension = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.length());
                //check folder name
                if(branchName.equalsIgnoreCase(branchFolderExtension)){
                    branchDirPath=filePath;
                }
            }
        }
        try {
            if (branchPath != null) {
                Files.delete(Paths.get(branchPath));
            }
            if(branchDirPath!=null){
                Files.delete(Paths.get(branchDirPath));
            }
        } catch (IOException e) {
            System.out.println("ceva eroare");
        }
    }

    public void createBankFile(String name){
        Path road = Paths.get(System.getProperty("user.dir")+"\\"+name);
//        directoryName = road.toString();
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
        File bankFile = new File(directoryName + "/" + name+".txt");
        try {
            FileWriter fw = new FileWriter(bankFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getBankName()+"\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void branchFileWrite(String branchName){
        File branchFile = new File(directoryName + "/" + branchName+".txt");
        if(!branchFile.exists()){
            try{
                FileWriter fw = new FileWriter(branchFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(branchName+"\n");
//                bw.write("empty");
//              updateBranchFile();
                bw.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public String getBankName() {
        return bankName;
    }

    public boolean isBank(String bankName){
        boolean ok=false;
        String directoryPath = System.getProperty("user.dir"); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files
        String branchPath = null;
        for(int i=1;i<filesInDirectory.length;i++) {
            String filePath = filesInDirectory[i].getAbsolutePath();
            String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length()); //check only the name part, not the entire path
            if (bankName.equals(fileExtenstion)) {
                ok = true;
            }
        }
        return ok;
    }

    public boolean isBankBranch(String branchName){
        boolean ok=false;
        String directoryPath = System.getProperty("user.dir")+"\\"+getBankName(); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files

        String branchPath = null;
        for(File f : filesInDirectory) {
            String filePath = f.getAbsolutePath();
            String checkType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
            if (checkType.equalsIgnoreCase("txt")) {
                String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.lastIndexOf(".")); //check only the name part, not the entire path
                if (branchName.equals(fileExtenstion)) {
                    ok = true;
                }
            }
        }
        return ok;
    }

    //replaced
    public void printAllBankBranches() {
        for (String i : bankBranches.keySet()) {
            //print branch list and data about the branches
            //branch number of clients and maybe total money deposited
            System.out.println(bankBranches.get(i).getBranchName());
        }
    }
    public void printBranchData(String branchName){
        // get branch clients
        System.out.println("Branch: "+branchName.toUpperCase());
        System.out.println("---------------------------------------");
        bankBranches.get(branchName).printAllBranchCustomers();
        System.out.println("---------------------------------------");
    }

    private boolean isBranch(String branchName){
        if(bankBranches.containsKey(branchName))
            return true;
        else
            return false;
    }

//up to here

}