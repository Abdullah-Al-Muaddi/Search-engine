import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws Exception{


        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data\\dataset.csv")));
        while(reader.readLine()!=null) {
            StringTokenizer tokens = new StringTokenizer((String) reader.readLine(), ","); // this will read first line and separates values by (,) and stores them in tokens.

            // tokens.nextToken(); // this method will read the tokens values on each call.
            System.out.println(tokens.nextToken());
            reader.readLine();
}
reader.close();







    }
}