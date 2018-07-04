package ir.ac.aut.ceit.alg.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

    /**
     * read CSV file (NOTE: from the problem definition example the separator is " " like 1 2)
     * @param path address
     * @return an ArrayList of edges
     */
    public static ArrayList<int[]> readCSV(String path){
        ArrayList<int[]> result = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext()){
                result.add(new int[]{sc.nextInt(),sc.nextInt()});
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * write a string to a file
     * @param text the string
     * @param path the file
     */
    public static void write(String text, String path){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
