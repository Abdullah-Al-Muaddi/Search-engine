
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList; // Change it to manual
import java.util.List; 

/*
1- Documenet processing
    1.1- Using BST to store stop.txt
    1.2- Store the data (In which way? Consider the speed)
    1.3- Delete stop words from data

2- Indexing
    2.1- 
    2.2- 
    2.3- 
    2.4- 

3- Inverted indexing 
    3.1- Where is the word stored in which documenet by using BST
    3.2- 
    3.3- 
    3.4- 

    (WORD){
    CHECK While
    Return string (TEXT.txt 12 2)
    }

4- Query Processing ("WORD1 AND/OR WORD2")
    4.1- Get List for the first word and second word
    4.2- Create for loop based on the first word list to check second word list (Check is it AND or OR)
    4.3- Store the same documenets in List
    4.4- Return

5- Ranking ("Sentance")
    5.1- For loop based on the docements
    5.2- Search for each word checking number
    5.3- return docement number and number of the word
    5.4- Store docements number and ranking in list
    5.5- For loop displaying (e.g. Document 1: (Data,1)+(structures,2)=3) in decrearing order

6- LinkedList stores all words in the documenets 

*/

public class Main{
    public static void main(String[] args) throws Exception{
        String line="";
        String word="";

        List<String> stopWords= new ArrayList<>(); // We may change it to hashset because it much faster.

        BufferedReader stopWords_reader = new BufferedReader(new FileReader("data/stop.txt"));
        BufferedReader reader = new BufferedReader(new FileReader("data\\dataset.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/newDataset.txt"));

        while((line=stopWords_reader.readLine())!=null)
            stopWords.add(line.trim());

        line=""; // Make it empty again

        while((line = reader.readLine())!=null && !(line.equals(",,"))) {
            String[] data = line.split(",",2); // Limit=2 means we don't care about the commas that in 
            // System.out.println(data[0]);
            String[] words = data[1].toLowerCase().split(" "); // Convert it to lowercase, and then split it into words.

            for (int i =1 ; i <words.length;i++)
                if (!stopWords.contains(words[i])){
                    word = words[i].replaceAll("\\W+",""); // Remove all punctuations and non-alphanumerical characters
                    writer.write(word+" ");
                }

            writer.newLine();
        }
        reader.close();
        writer.close();
        stopWords_reader.close();
    }
}