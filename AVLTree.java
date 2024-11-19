
public class AVLTree<K, V> {

    private static class AVLNode<K, V> {

        // ====================> Attributes <====================
        K key;
        V value;
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int height;

        // ====================> Constructor <====================
        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
            height = 1; // Initial height for a new node
        }
    }

    // ====================> Attributes <====================
    private AVLNode<K, V> root;
    private AVLNode<K, V> current;

    // ====================> Constructor <====================
    public AVLTree() {
        root = current = null;
    }

    // ====================> Methods <====================
    public boolean empty() {
        return root == null;
    }

    public V retrieve() {
        if (current != null) {
            return current.value;
        } else {
            return null;
        }
    }

    public void update(V value) {
        if (current != null) {
            current.value = value;
        }
    }

    public boolean insert(K key, V value) {
        root = insertRec(root, key, value);
        return true;
    }

    private AVLNode<K, V> insertRec(AVLNode<K, V> node, K key, V value) {
        if (node == null) {
            current = new AVLNode<>(key, value);
            return current;
        }

        int cmp = compareKeys(key, node.key);

        if (cmp < 0) {
            node.left = insertRec(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, key, value);
        } else {
            current = node;
            return node;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);
        
        if (balance > 1 && compareKeys(key, node.left.key) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && compareKeys(key, node.right.key) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && compareKeys(key, node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && compareKeys(key, node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private int getHeight(AVLNode<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(AVLNode<K, V> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        if (current == y) {
            current = x;
        }
        return x;
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        if (current == x) {
            current = y;
        }
        return y;
    }

    private int compareKeys(K key1, K key2) {
        int cmp;
        if (key1 instanceof String && key2 instanceof String) {
            cmp = ((String) key1).compareTo((String) key2);
        } else if (key1 instanceof Integer && key2 instanceof Integer) {
            cmp = ((Integer) key1).compareTo((Integer) key2);
        } else {
            throw new IllegalArgumentException("Unsupported key type");
        }
        return cmp;
    }

    public boolean find(K key) {
        current = findRec(root, key);
        return current != null;
    }

    private AVLNode<K, V> findRec(AVLNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = compareKeys(key, node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return findRec(node.left, key);
        } else {
            return findRec(node.right, key);
        }
    }

    public void traverseInorder() {
        traverseInorder(root);
    }

    private void traverseInorder(AVLNode<K, V> n) {
        if (n != null) {
            traverseInorder(n.left);
            System.out.println("Key: " + n.key + " -> Value: " + n.value);
            traverseInorder(n.right);
        }
    }
}
