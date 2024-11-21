
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
        return current != null && current.next() == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (current != null) {
            current = current.next();
        }
    }

    public T retrieve() {
        if (current != null) {
            return current.data();
        }
        return null;
    }

    public void update(T val) {
        current.setData(val);
    }

    public void insert(T val) {
        Node<T> tmp;

        if (empty()) {
            current = head = new Node<>(val);
        } else {
            if (current == null) {
                current = head;
            }
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

    public Boolean contains(T val) {
        Node<T> temp = head;

        while (temp != null) {
            if (temp.data().equals(val)) {
                return true;
            }
            temp = temp.next();
        }
        return false;
    }

    // ====================> toString <====================
    @Override
    public String toString() {
        String relation = "";

        Node<T> temp = head;
        while (temp != null) {
            if (temp.next()!=null){
                relation += temp.data() + ", ";
            }
            else{
                relation += temp.data();
            }
            temp = temp.next();
        }
        return relation;
    }
}
