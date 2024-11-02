
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
        return current.next() == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        current = current.next();
    }

    public T retrieve() {
        return current.data();
    }

    public void update(T val) {
        current.setData(val);
    }

    public void insert(T val) {
        Node<T> tmp;

        if (empty()) {
            current = head = new Node<>(val);
        } else {
            tmp = current.next();
            current.setNext(new Node<>(val));
            current = current.next();
            current.setNext(tmp);
        }
    }

    public void remove() {
        if (current == head) {
            head = head.next();
        } else {
            Node<T> tmp = head;

            while (tmp.next() != current) {
                tmp = tmp.next();
            }

            tmp.setNext(current.next());
        }

        if (current.next() == null) {
            current = head;
        } else {
            current = current.next();
        }
    }
}
