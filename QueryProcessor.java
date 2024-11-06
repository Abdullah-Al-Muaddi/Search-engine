public class QueryProcessor {
    
    // ====================> Attributes <====================
    private BST<String, LinkedList<String>> invertedIndex;

    // ====================> Constructor <====================
    public QueryProcessor(BST<String, LinkedList<String>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    // ====================> Methods <====================

    // Boolean AND Query
    public LinkedList<String> andQuery(String term1, String term2) {
        LinkedList<String> list1 = invertedIndex.find(term1) ? invertedIndex.retrieve() : null;
        LinkedList<String> list2 = invertedIndex.find(term2) ? invertedIndex.retrieve() : null;
    
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
        LinkedList<String> list1 = invertedIndex.find(term1) ? invertedIndex.retrieve() : null;
        LinkedList<String> list2 = invertedIndex.find(term2) ? invertedIndex.retrieve() : null;

        LinkedList<String> resultSet = new LinkedList<>(); // Result to hold union of docs

        // Add all documents from list1 to resultSet
        list1.findFirst();
        if (list1 != null) {
            // list1.findFirst();
            while (!list1.last()) {
                if (!resultSet.contains(list1.retrieve())) {
                    resultSet.insert(list1.retrieve());
                }
                list1.findNext();
            }
            if (!resultSet.contains(list1.retrieve())) resultSet.insert(list1.retrieve());
        }

        // Add all documents from list2 to resultSet
        list2.findFirst();
        if (list2 != null) {
            // list2.findFirst();
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

    // Ranked Retrieval using Term Frequency
    public LinkedList<DocumentScore> rankedQuery(String query) {
        String[] terms = query.toLowerCase().split(" "); // Split query into terms
        LinkedList<DocumentScore> rankedResults = new LinkedList<>(); // Holds results with scores

        for (String term : terms) {
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
    
    

}