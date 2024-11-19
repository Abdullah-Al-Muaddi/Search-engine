
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {

        // =================================> Attributes <==================================
        String line = "";
        String word = "";
        int docId = 0;
        AVLTree<String, Integer> stopWords = new AVLTree<>();
        AVLTree<Integer, LinkedList<String>> forwardIndex = new AVLTree<>(); // Used to save each document with it exact words
        AVLTree<String, LinkedList<String>> invertedIndex = new AVLTree<>();
        BufferedReader stopWords_reader = new BufferedReader(new FileReader("data/stop.txt"));
        BufferedReader reader = new BufferedReader(new FileReader("data\\dataset.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/newDataset.txt"));

        // ==================================> Document Processing <==================================
        while ((line = stopWords_reader.readLine()) != null) {
            stopWords.insert(line.trim(), null);
        }

        line = "";
        reader.readLine(); // we want to skip first line
        while ((line = reader.readLine()) != null && !(line.equals(",,"))) {
            String[] data = line.split(",", 2); // Limit=2 means we don't care about the commas that in 
            // System.out.println(data[0]);                                   // FOR TESTING
            String[] words = data[1].toLowerCase().split(" "); // Convert it to lowercase, and then split it into words.
            LinkedList<String> indexing = new LinkedList<>();

            for (int i = 0; i < words.length; i++) {
                if (!stopWords.find(words[i])) {
                    word = words[i].replaceAll("\\W+", ""); // Remove all punctuations and non-alphanumerical characters
                    indexing.insert(word);
                    // writer.write(word + " ");                              // FOR TESTING

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~> Inverted Indexing <~~~~~~~~~~~~~~~~~~~~~~~~~~
                    LinkedList<String> docs;
                    if (invertedIndex.find(word)) { // If the word happen to be saved already we just retrieve linked list
                        docs = invertedIndex.retrieve();
                    } else { // Word hasn't been saved in the invertedIndex AVLTree
                        docs = new LinkedList<>();
                        invertedIndex.insert(word, docs);
                    }
                    docs.insert(String.valueOf(docId)); // Add the document id to docs list
                }
            }
            forwardIndex.insert(docId, indexing);
            // System.out.println("Document ID " + docId + ": " + indexing);  // FOR TESTING
            writer.newLine();
            docId++;
        }
        // invertedIndex.traverseInorder();                                   // FOR TESTING

        reader.close();
        writer.close();
        stopWords_reader.close();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~> Query Processor <~~~~~~~~~~~~~~~~~~~~~~~~~~
        QueryProcessor queryProcessor = new QueryProcessor(invertedIndex);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~> Create GUI <~~~~~~~~~~~~~~~~~~~~~~~~~~
        new GUI(queryProcessor);
    }
}