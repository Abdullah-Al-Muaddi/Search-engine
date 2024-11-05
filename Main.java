
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

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
public class Main {

    public static void main(String[] args) throws Exception {
        String line = "";
        String word = "";
        int docId=0;

    //---------------------------------------> Binary Search Tree (Will be changed to AVL afterward)<-----------------------------------------
        BST<String,Integer> stopWords = new BST<>();
        BST<Integer, LinkedList<String>> forwardIndex = new BST<>();//used to save each document with it exact words
        BST<String, LinkedList<String>> invertedIndex = new BST<>(); 

    //---------------------------------------> File Read / Write <------------------------------------<
        BufferedReader stopWords_reader = new BufferedReader(new FileReader("data/stop.txt"));
        BufferedReader reader = new BufferedReader(new FileReader("data\\dataset.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/newDataset.txt"));

    //--------------------------------------->Linked Lists<--------------------------------------------
    // LinkedList<String> indexing = new LinkedList<>();



        while ((line = stopWords_reader.readLine()) != null) {
            stopWords.insert(line.trim(),null);
        }

        line = ""; // Make it empty again
        reader.readLine();//we want to skip first line
        while ((line = reader.readLine()) != null && !(line.equals(",,"))) { // we have to edit it so it skips first line
            String[] data = line.split(",", 2); // Limit=2 means we don't care about the commas that in 
            // System.out.println(data[0]);
            String[] words = data[1].toLowerCase().split(" "); // Convert it to lowercase, and then split it into words.
            LinkedList<String> indexing = new LinkedList<>();

            for (int i = 1; i < words.length; i++) {
                if (!stopWords.find(words[i])) {
                    word = words[i].replaceAll("\\W+", ""); // Remove all punctuations and non-alphanumerical characters
                    indexing.insert(word);
                    writer.write(word + " ");
                    //Inverted indexing
                    LinkedList<String> docs;
                    if (invertedIndex.find(word)){ //if the word happen to be saved already we just retrieve linked list
                        docs=invertedIndex.retrieve();
                    }
                    else{ //word hasn't been saved in the invertedIndex BST
                        docs=new LinkedList<>();
                        invertedIndex.insert(word,docs); 
                    }
                    docs.insert(String.valueOf(docId)); // add the document id (STRING) to docs list



                }
            }
            forwardIndex.insert(docId,indexing);
            // System.out.println("Document ID " + docId + ": " + indexing);//for testing perposes
            
            writer.newLine();
            docId++;
        }

        //inverted index
        // invertedIndex.traverseInorder(); //for testing perposes

        
        reader.close();
        writer.close();
        stopWords_reader.close();
    }

}