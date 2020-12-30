public class BankingSystemDrive {

    public static void main(String[] args) {

/*
         "Welcome to the SimpleBank 1.0 interface. SimpleBank is a banking system " +
                "where you can add new banks, branches and customers, manage customer transactions." +
                "and update customer data. \n "
*/
        Bank b = getBank("FirstBank");
//        b.getAllBankBranches();
//        b.deleteBranch("Branch 2");
        b.addBankBranch(new Branch(getBank("FirstBank"),"Branch y"));

//        Bank bank3 = new Bank("ThirdBank");
//        bank1.getAllBankBranches();
//
//        Branch b2 = new Branch(bank1,"Branch 2");
//        Branch b3 = new Branch(bank1,"Branch 3"   );
//        Branch b4 = new Branch(bank1,"Branch 4");
//
//        Customer c1 = new Customer(b1,"Andrew", "074619094", "address 1", 150);
//        Customer c2 = new Customer(b1,"John", "038545434", "address 2", 320);
//        Customer c3 = new Customer(b1,"Alex", "0845345232", "address 3", 1400);
        //scrierea functioneaza ok
//
//        Customer c4 = new Customer(b2,"Sebastian", "0845345232", "address 4", 3143);
//        Customer c5 = new Customer(b2,"Vlad", "0845345232", "address 5", 2122);


//        Bank bank2 = new Bank("SecondBank");
//        Branch b5 = new Branch(bank2, "Branch 5");
//        b1.saveToBranchDoc(); arrayIndexOutOfBounds at reading
//        bank1.getBranch(b1.getBranchName());
//        bank1.getBranch(b2.getBranchName());
//        bank1.deleteBranch("Branch 2"); //make delete from bank csv also
//        bank1.getAllBankBranches();



    }
    public static Bank getBank(String bankName){
        return new Bank(bankName);
    }

}

/*
    SimpleBank Instructions
    Create customer object, branch object, bank object
    customer -> branch -> bank

    Bank
        ->bankName
        ->bankBranches
    M.addBankBranch
    M.printBranchData
    M.printAllBankBranches

    Branch
        ->BranchName
        ->keyChain
    M.addBranchCustomer
    M.printAllBranchCustomers
    M.printBranchCustomers
    M.deleteCustomer
    M.saveToBranchDoc
    M.readBranchData
    M.customerUpdateData

    Customer
        ->Account
        =>Name
        =>Balance
    M.addTransaction
    M.checkBalance
    M.depositAmount
    M.withdrawAmount


     */
