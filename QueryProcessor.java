public class QueryProcessor {
    
    // ====================> Attributes <====================
    private AVLTree<String, LinkedList<String>> invertedIndex;

    // ====================> Constructor <====================
    public QueryProcessor(AVLTree<String, LinkedList<String>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    // ====================> Methods <====================

    // Boolean AND Query
    public LinkedList<String> andQuery(String term1, String term2) {
        LinkedList<String> list1=null,list2 =null;
        if (invertedIndex.find(term1))
           list1 =invertedIndex.retrieve();

        if(invertedIndex.find(term2))
            list2 = invertedIndex.retrieve();
    
        if (list1 == null || list2 == null) return new LinkedList<>(); // Return empty if no match
    
        LinkedList<String> result = new LinkedList<>(); // Result to hold intersection
        list1.findFirst(); // Ensure it starts at the beginning
        while (!list1.last()) {
            String doc = list1.retrieve();
            if (list2.contains(doc))
             result.insert(doc);
            list1.findNext();
        }
        if (list2.contains(list1.retrieve())) result.insert(list1.retrieve()); // Add last if matches
    
        return result;
    }

    // Boolean OR Query
    public LinkedList<String> orQuery(String term1, String term2) {

        LinkedList<String> list1=null,list2 =null;
        if (invertedIndex.find(term1))
           list1 =invertedIndex.retrieve();

        if(invertedIndex.find(term2))
            list2 = invertedIndex.retrieve();

        LinkedList<String> resultSet = new LinkedList<>(); // Result to hold union of docs

        // Add all documents from list1 to resultSet
        if (list1 != null) {
            list1.findFirst();
            while (!list1.last()) {
                if (!resultSet.contains(list1.retrieve())) {
                    resultSet.insert(list1.retrieve());
                }
                list1.findNext();
            }
            if (!resultSet.contains(list1.retrieve())) resultSet.insert(list1.retrieve());
        }

        // Add all documents from list2 to resultSet
        if (list2 != null) {
            list2.findFirst();
            while (!list2.last()) {
                if (!resultSet.contains(list2.retrieve())) {
                    resultSet.insert(list2.retrieve());
                }
                list2.findNext();
            }
            if (!resultSet.contains(list2.retrieve())) resultSet.insert(list2.retrieve());
        }
        return resultSet;
    }
    public LinkedList<String> andOrQuery(String term1, LinkedList<String> resultSet) {
        LinkedList<String> list1=null;
        if (invertedIndex.find(term1))
           list1 =invertedIndex.retrieve();
        
           if (list1 == null) {
            return resultSet; // Return the original resultSet if no results for term1
        }

           list1.findFirst();//to start from the beginning
            while(!list1.last()){
                String doc = list1.retrieve();
                if(!resultSet.contains(doc)){
                    resultSet.insert(doc);
                }
                list1.findNext();
            }

              //To  handle the last element
       if (!resultSet.contains(list1.retrieve())) {
        resultSet.insert(list1.retrieve());
    }
        



        return resultSet;
    }
 
    public LinkedList<String> handleQuery(String Query) {
        String[] andTerms;
        String[] orTerms = Query.split(" OR ");
        LinkedList<String> Results = null; // Initialize the results
    
        if (orTerms.length == 2) {
            // Process first OR term
            andTerms = orTerms[0].split(" AND ");
            LinkedList<String> firstResult = null;
    
            if (andTerms.length == 2) {
                // Process AND within first OR term
                firstResult = andQuery(andTerms[0].trim(), andTerms[1].trim());
            } else if (andTerms.length == 1) {
                // Single term in first OR term
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    firstResult = invertedIndex.retrieve();
                } else {
                    firstResult = new LinkedList<>(); // Term not found
                }
            } else {
                return null; // Invalid format
            }
    
            // Process second OR term
            andTerms = orTerms[1].split(" AND ");
            LinkedList<String> secondResult = null;
    
            if (andTerms.length == 2) {
                // Process AND within second OR term
                secondResult = andQuery(andTerms[0].trim(), andTerms[1].trim());
            } else if (andTerms.length == 1) {
                // Single term in second OR term
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    secondResult = invertedIndex.retrieve();
                } else {
                    secondResult = new LinkedList<>(); // Term not found
                }
            } else {
                return null; // Invalid format
            }
    
            // Combine the results of the two OR terms
            Results = orQueryResults(firstResult, secondResult); // OR query
    
        } else if (orTerms.length == 1) {
            // No OR, check for AND
            andTerms = Query.split(" AND ");
            if (andTerms.length == 2) {
                // Process AND query
                Results = andQuery(andTerms[0].trim(), andTerms[1].trim()); // AND query
            } else if (andTerms.length == 1) {
                // Single term
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    Results = invertedIndex.retrieve();
                } else {
                    Results = new LinkedList<>(); // Term not found
                }
            } else {
                return null; // Invalid format
            }
        } else {
            return null; // Invalid format
        }
    
        return Results;
    }
    public LinkedList<String> orQueryResults(LinkedList<String> list1, LinkedList<String> list2) {
        LinkedList<String> resultSet = new LinkedList<>(); // Result to hold union of docs
    
        // Add all documents from list1 to resultSet
        if (list1 != null) {
            list1.findFirst();
            while (!list1.last()) {
                String doc = list1.retrieve();
                if (!resultSet.contains(doc)) {
                    resultSet.insert(doc);
                }
                list1.findNext();
            }
            if (!resultSet.contains(list1.retrieve())) resultSet.insert(list1.retrieve());
        }
    
        // Add all documents from list2 to resultSet
        if (list2 != null) {
            list2.findFirst();
            while (!list2.last()) {
                String doc = list2.retrieve();
                if (!resultSet.contains(doc)) {
                    resultSet.insert(doc);
                }
                list2.findNext();
            }
            if (!resultSet.contains(list2.retrieve())) resultSet.insert(list2.retrieve());
        }
    
        return resultSet;
    }
    


 // Ranked Retrieval using Term Frequency
public LinkedList<DocumentScore> rankedQuery(String query) {
    String[] terms = query.toLowerCase().split(" "); // Split query into terms
    LinkedList<DocumentScore> rankedResults = new LinkedList<>(); // Holds results with scores

    for (int i = 0; i < terms.length; i++) {
        String term = terms[i];
        if (invertedIndex.find(term)) {
            LinkedList<String> docs = invertedIndex.retrieve();
            docs.findFirst();
            while (!docs.last()) {
                addOrUpdateScore(rankedResults, docs.retrieve());
                docs.findNext();
            }
            addOrUpdateScore(rankedResults, docs.retrieve());
        }
    }

    // Sort the rankedResults linked list in descending order
    rankedResults = sortRankedResults(rankedResults);

    return rankedResults;
}

    // Helper method to add or update the score for a document in rankedResults
    private void addOrUpdateScore(LinkedList<DocumentScore> list, String docId) {
        list.findFirst(); // Start from the beginning of the list
    
        // Traverse the list to find a matching docId
        while (!list.last() && list.retrieve() != null) {
            DocumentScore score = list.retrieve();
            if (score != null && score.getDocId().equals(docId)) {
                score.incrementScore();
                return;
            }
            list.findNext();
        }
    
        // Check the last element if present
        if (list.retrieve() != null && list.retrieve().getDocId().equals(docId)) {
            list.retrieve().incrementScore();
        } else {
            // If no matching docId is found, add a new DocumentScore entry
            list.insert(new DocumentScore(docId, 1));
        }
    }
// Method to sort the rankedResults linked list in descending order using selection sort
private LinkedList<DocumentScore> sortRankedResults(LinkedList<DocumentScore> list) {
    // Count the number of elements in the list
    int count = 0;
    list.findFirst();
    while (true) {
        if (list.retrieve() != null) {
            count++;
        }
        if (list.last()) {
            break;
        }
        list.findNext();
    }

    // Copy elements to an array
    DocumentScore[] scoreArray = new DocumentScore[count];
    int index = 0;
    list.findFirst();
    while (true) {
        if (list.retrieve() != null) {
            scoreArray[index] = list.retrieve();
            index++;
        }
        if (list.last()) {
            break;
        }
        list.findNext();
    }

    // Selection sort algorithm with secondary sorting by document numbers
    for (int i = 0; i < count - 1; i++) {
        int maxIndex = i;
        for (int j = i + 1; j < count; j++) {
            if (scoreArray[j].getScore() > scoreArray[maxIndex].getScore()) {
                maxIndex = j;
            } else if (scoreArray[j].getScore() == scoreArray[maxIndex].getScore()) {
                // Scores are equal, compare document numbers
                int docId1 = Integer.parseInt(scoreArray[j].getDocId());
                int docId2 = Integer.parseInt(scoreArray[maxIndex].getDocId());
                if (docId1 < docId2) {
                    maxIndex = j;
                }
            }
        }
        // Swap the found maximum element with the first element
        if (maxIndex != i) {
            DocumentScore temp = scoreArray[i];
            scoreArray[i] = scoreArray[maxIndex];
            scoreArray[maxIndex] = temp;
        }
    }

    // Reconstruct the linked list from the sorted array
    LinkedList<DocumentScore> sortedList = new LinkedList<>();
    for (int i = 0; i < count; i++) {
        sortedList.insert(scoreArray[i]);
    }

    return sortedList;
}

    

}