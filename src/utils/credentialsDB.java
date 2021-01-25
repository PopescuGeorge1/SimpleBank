package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class credentialsDB {
	public static ArrayList<String[]> credentials() {
		//find file
        String directoryPath = System.getProperty("user.dir"); //where to look
        File[] filesInDirectory = new File(directoryPath).listFiles(); //list all files
        String path = null;
        try {
            for (File f : filesInDirectory) {
                String filePath = f.getAbsolutePath();
                String fileExtenstion = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length()); //check only the name part, not the entire path
                if ("credentials.txt".equals(fileExtenstion)) {
                	//file found
                    path = filePath;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //read file
        ArrayList<String[]>readedData = new ArrayList<>();
        if(path!=null)
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(path)));
            while (scan.hasNextLine()) {
                String input = scan.nextLine();
                String[] data = input.split(";");
                readedData.add(data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //print to console
//        for(String[] i : readedData){
//            for (String s : i) {
//                System.out.print(s + " ");
//            }
//            System.out.println("====");
//        }
        return readedData;
	}
}
