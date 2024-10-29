import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws Exception{
        String line="";


        BufferedReader reader = new BufferedReader(new FileReader("data\\dataset.csv"));
        while((line = reader.readLine())!=null) {
            String[] data = line.split(",");
            System.out.println(data[0]);
            String[] words = data[1].toLowerCase().split(" ");//convert it to lowercase and then split it into words


   
}
reader.close();







    }
}