
public class QueryProcessor {

    // ====================> Attribute <====================
    private AVLTree<String, LinkedList<String>> invertedIndex;

    // ====================> Constructor <====================
    public QueryProcessor(AVLTree<String, LinkedList<String>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    // ====================> Methods <====================

    // ====================> Boolean Query <====================
    // ~~~~~~~~~~~~~~~~~~~~> AND <~~~~~~~~~~~~~~~~~~~~
    public LinkedList<String> andQuery(String term1, String term2) {
        LinkedList<String> list1 = null, list2 = null;

        if (invertedIndex.find(term1)) {
            list1 = invertedIndex.retrieve();
        }

        if (invertedIndex.find(term2)) {
            list2 = invertedIndex.retrieve();
        }

        if (list1 == null || list2 == null) {
            return new LinkedList<>();
        }

        LinkedList<String> result = new LinkedList<>();
        list1.findFirst();

        while (!list1.last()) {
            String doc = list1.retrieve();
            if (list2.contains(doc)) {
                result.insert(doc);
            }
            list1.findNext();
        }

        if (list2.contains(list1.retrieve())) {
            result.insert(list1.retrieve());
        }

        return result;
    }

    // ~~~~~~~~~~~~~~~~~~~~> OR <~~~~~~~~~~~~~~~~~~~~
    public LinkedList<String> orQuery(String term1, String term2) {
        LinkedList<String> list1 = null, list2 = null;

        if (invertedIndex.find(term1)) {
            list1 = invertedIndex.retrieve();
        }

        if (invertedIndex.find(term2)) {
            list2 = invertedIndex.retrieve();
        }

        LinkedList<String> resultSet = new LinkedList<>(); 

        if (list1 != null) {
            list1.findFirst();

            while (!list1.last()) {
                if (!resultSet.contains(list1.retrieve())) {
                    resultSet.insert(list1.retrieve());
                }
                list1.findNext();
            }

            if (!resultSet.contains(list1.retrieve())) {
                resultSet.insert(list1.retrieve());
            }
        }

        if (list2 != null) {
            list2.findFirst();

            while (!list2.last()) {
                if (!resultSet.contains(list2.retrieve())) {
                    resultSet.insert(list2.retrieve());
                }
                list2.findNext();
            }

            if (!resultSet.contains(list2.retrieve())) {
                resultSet.insert(list2.retrieve());
            }
        }

        return resultSet;
    }

    // ~~~~~~~~~~~~~~~~~~~~> AND & OR <~~~~~~~~~~~~~~~~~~~~
    public LinkedList<String> andOrQuery(String term1, LinkedList<String> resultSet) {
        LinkedList<String> list1 = null;
        if (invertedIndex.find(term1)) {
            list1 = invertedIndex.retrieve();
        }

        if (list1 == null) {
            return resultSet;
        }

        list1.findFirst();
        while (!list1.last()) {
            String doc = list1.retrieve();
            if (!resultSet.contains(doc)) {
                resultSet.insert(doc);
            }
            list1.findNext();
        }

        if (!resultSet.contains(list1.retrieve())) {
            resultSet.insert(list1.retrieve());
        }

        return resultSet;
    }

    // ====================> Query Processer <====================
    public LinkedList<String> handleQuery(String Query) {
        String[] andTerms;
        String[] orTerms = Query.split(" OR ");
        LinkedList<String> Results = null;

        if (orTerms.length == 2) { // Process first OR
            andTerms = orTerms[0].split(" AND ");
            LinkedList<String> firstResult = null;

            if (andTerms.length == 2) { // Process AND in the first OR
                firstResult = andQuery(andTerms[0].trim().toLowerCase(), andTerms[1].trim().toLowerCase());
            } else if (andTerms.length == 1) { // Word1 OR Word2
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    firstResult = invertedIndex.retrieve();
                } else {
                    firstResult = new LinkedList<>();
                }
            } else {
                return null; 
            }

            andTerms = orTerms[1].split(" AND "); // Process Second OR
            LinkedList<String> secondResult = null;

            if (andTerms.length == 2) { // Process AND in the second OR
                secondResult = andQuery(andTerms[0].trim().toLowerCase(), andTerms[1].trim().toLowerCase()); 
            } else if (andTerms.length == 1) { // Word1 AND Word2
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    secondResult = invertedIndex.retrieve();
                } else {
                    secondResult = new LinkedList<>(); 
                }
            } else {
                return null;
            }
            Results = orQueryResults(firstResult, secondResult); 
        } else if (orTerms.length == 1) { // No OR, check for AND
            andTerms = Query.split(" AND ");
            if (andTerms.length == 2) { // Process AND
                Results = andQuery(andTerms[0].trim().toLowerCase(), andTerms[1].trim().toLowerCase()); 
            } else if (andTerms.length == 1) { // Single term
                String term = andTerms[0].trim();
                if (invertedIndex.find(term)) {
                    Results = invertedIndex.retrieve();
                } else {
                    Results = new LinkedList<>(); 
                }
            } else {
                return null; 
            }
        } else {
            return null; 
        }

        return Results;
    }

    public LinkedList<String> orQueryResults(LinkedList<String> list1, LinkedList<String> list2) {
        LinkedList<String> resultSet = new LinkedList<>();

        if (list1 != null) { // Add all the documents from list1 to resultSet
            list1.findFirst();
            while (!list1.last()) {
                String doc = list1.retrieve();
                if (!resultSet.contains(doc)) {
                    resultSet.insert(doc);
                }
                list1.findNext();
            }
            if (!resultSet.contains(list1.retrieve())) {
                resultSet.insert(list1.retrieve());
            }
        }

        if (list2 != null) { // Add all documents from list2 to resultSet
            list2.findFirst();
            while (!list2.last()) {
                String doc = list2.retrieve();
                if (!resultSet.contains(doc)) {
                    resultSet.insert(doc);
                }
                list2.findNext();
            }
            if (!resultSet.contains(list2.retrieve())) {
                resultSet.insert(list2.retrieve());
            }
        }
        return resultSet;
    }

    // ====================> Ranked Retrieval <====================
    public LinkedList<DocumentScore> rankedQuery(String query) {
  
        if (query.isEmpty())//check if empty or not
            return null;
        String[] terms = query.toLowerCase().split(" "); 
        LinkedList<DocumentScore> rankedResults = new LinkedList<>(); 

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
        if(!rankedResults.empty()) //we found the query at least once in the dataset
            rankedResults = sortRankedResults(rankedResults);

        return rankedResults;
    }

    private void addOrUpdateScore(LinkedList<DocumentScore> list, String docId) { // To add/update the score
        list.findFirst();

        while (!list.last() && list.retrieve() != null) {
            DocumentScore score = list.retrieve();
            if (score != null && score.getDocId().equals(docId)) {
                score.incrementScore();
                return;
            }
            list.findNext();
        }

        if (list.retrieve() != null && list.retrieve().getDocId().equals(docId)) {
            list.retrieve().incrementScore();
        } else {
            list.insert(new DocumentScore(docId, 1));
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~> Selection Sort <~~~~~~~~~~~~~~~~~~~~
    private LinkedList<DocumentScore> sortRankedResults(LinkedList<DocumentScore> list) {
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

        for (int i = 0; i < count - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < count; j++) {
                if (scoreArray[j].getScore() > scoreArray[maxIndex].getScore()) {
                    maxIndex = j;
                } else if (scoreArray[j].getScore() == scoreArray[maxIndex].getScore()) {
                    int docId1 = Integer.parseInt(scoreArray[j].getDocId());
                    int docId2 = Integer.parseInt(scoreArray[maxIndex].getDocId());
                    if (docId1 < docId2) {
                        maxIndex = j;
                    }
                }
            }

            if (maxIndex != i) {
                DocumentScore temp = scoreArray[i];
                scoreArray[i] = scoreArray[maxIndex];
                scoreArray[maxIndex] = temp;
            }
        }

        LinkedList<DocumentScore> sortedList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            sortedList.insert(scoreArray[i]);
        }
        return sortedList;
    }
}
