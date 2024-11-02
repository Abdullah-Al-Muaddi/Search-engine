
public class Node<T> {

// ====================> Attributes <====================
    private T data;
    private Node<T> next;

// ====================> Constructor <====================
    public Node() {
        data = null;
        next = null;
    }

    public Node(T val) {
        data = val;
        next = null;
    }

// ====================> Setters <====================
    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

// ====================> Getters <====================
    public T data() {
        return data;
    }

    public Node<T> next() {
        return next;
    }
}
