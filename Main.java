import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList; // Change it to manual
import java.util.List; 

public class Main{
    public static void main(String[] args) throws Exception{
        String line="";
        String word="";
        List<String> stopWords= new ArrayList<>();//we may change it to hashset because it much faster.
        BufferedReader stopWords_reader = new BufferedReader(new FileReader("data/stop.txt"));
        while((line=stopWords_reader.readLine())!=null){
            stopWords.add(line.trim());
        }
        line="";//make it empty again
        BufferedReader reader = new BufferedReader(new FileReader("data\\dataset.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/newDataset.txt"));

        while((line = reader.readLine())!=null && !(line.equals(",,"))) {
            String[] data = line.split(",",2); //Limit=2 means we don't care about the commas that in 
            // System.out.println(data[0]);
            String[] words = data[1].toLowerCase().split(" ");//convert it to lowercase, and then split it into words.

            for (int i =1 ; i <words.length;i++){
                if (!stopWords.contains(words[i])){
                    word = words[i].replaceAll("\\W+",""); //remove all punctuations and non-alphanumerical characters
                    writer.write(word+" ");
                }
            }
            writer.newLine();
   
}
reader.close();
writer.close();
stopWords_reader.close();



    }
}