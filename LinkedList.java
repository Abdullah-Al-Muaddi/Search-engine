
public class LinkedList<T> {

    // ====================> Attributes <====================
    private Node<T> head;
    private Node<T> current;

    // ====================> Constructor <====================
    public LinkedList() {
        head = current = null;
    }

    // ====================> Methods <====================
    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current.getNext() == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        current = current.getNext();
    }

    public T retrieve() {
        return current.getData();
    }

    public void update(T val) {
        current.setData(val);
    }

    public void insert(T val) {
        Node<T> tmp;

        if (empty()) {
            current = head = new Node<>(val);
        } else {
            tmp = current.getNext();
            current.setNext(new Node<>(val));
            current = current.getNext();
            current.setNext(tmp);
        }
    }

    public void remove() {
        if (current == head) {
            head = head.getNext();
        } else {
            Node<T> tmp = head;

            while (tmp.getNext() != current) {
                tmp = tmp.getNext();
            }

            tmp.setNext(current.getNext());
        }

        if (current.getNext() == null) {
            current = head;
        } else {
            current = current.getNext();
        }
    }
}
