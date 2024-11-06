// ====================> DocumentScore Class <====================
public class DocumentScore {
    
    // ====================> Attributes <====================
    private String docId;
    private int score;

    // ====================> Constructor <====================
    public DocumentScore(String docId, int score) {
        this.docId = docId;
        this.score = score;
    }

    // ====================> Getters <====================
    public String getDocId() {
        return docId;
    }

    public int getScore() {
        return score;
    }

    // ====================> Increment Score <====================
    public void incrementScore() {
        this.score++;
    }

    // ====================> Set Document ID and Score <====================
    public void set(String docId, int score) {
        this.docId = docId;
        this.score = score;
    }

    // ====================> toString Override <====================
    @Override
    public String toString() {
        return "Document " + docId + ": Score = " + score;
    }
}
