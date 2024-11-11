public class AVLTree<K, V> {
    private static class AVLNode<K, V> {
        K key;
        V value;
        AVLNode<K, V> left, right;
        int height;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
            height = 1; // Initial height for a new node
        }
    }

    private AVLNode<K, V> root, current;

    public AVLTree() {
        root = current = null;
    }

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
        return true; // Return true after insertion
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
            // Key already exists; update the value or keep as is
            current = node;
            return node;
        }

        // Update the height of the ancestor node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // Get the balance factor
        int balance = getBalance(node);

        // Balance the tree if needed

        // Left Left Case
        if (balance > 1 && compareKeys(key, node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && compareKeys(key, node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && compareKeys(key, node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && compareKeys(key, node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return the unchanged node pointer
    }

    // Helper method to get the height of the node
    private int getHeight(AVLNode<K, V> node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Helper method to get the balance factor of the node
    private int getBalance(AVLNode<K, V> node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // Right rotate subtree rooted with y
    private AVLNode<K, V> rightRotate(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        // Update current pointer if needed
        if (current == y)
            current = x;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    private AVLNode<K, V> leftRotate(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        // Update current pointer if needed
        if (current == x)
            current = y;

        // Return new root
        return y;
    }

    // Method to compare keys
    private int compareKeys(K key1, K key2) {
        int cmp;
        if (key1 instanceof String && key2 instanceof String) {
            cmp = ((String) key1).compareTo((String) key2);
        } else if (key1 instanceof Integer && key2 instanceof Integer) {
            cmp = ((Integer) key1).compareTo((Integer) key2);
        } else {
            // Handle other cases or throw an exception
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

    // Inorder traversal (for testing purposes)
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

