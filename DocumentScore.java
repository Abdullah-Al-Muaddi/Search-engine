
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

    // ====================> Methods <====================
    public void incrementScore() {
        this.score++;
    }

    public void set(String docId, int score) {
        this.docId = docId;
        this.score = score;
    }

    // ====================> toString <====================
    @Override
    public String toString() {
        return docId + "\t" + score;
    }
}
