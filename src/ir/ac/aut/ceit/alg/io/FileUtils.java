package ir.ac.aut.ceit.alg.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

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
