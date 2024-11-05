public class BST<K,V> {
    private static class BSTNode<K,V> {
        K key;
        V value;
        BSTNode<K,V> left, right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }

    private BSTNode<K,V> root, current;

    public BST() {
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
        if (root == null) {
            root = current = new BSTNode<>(key, value);
            return true;
        }
        return insertRec(root, key, value);
    }

    private boolean insertRec(BSTNode<K,V> node, K key, V value) {
        int cmp;
        if (key instanceof String){ //in invertedIndex it will be string
            cmp = ((String)key).compareTo((String)node.key);
        }
        else{ // in indexing it will key be integer
            cmp = ((Integer)key).compareTo((Integer)node.key);
        }
        if (cmp < 0) {
            if (node.left == null) {
                node.left = new BSTNode<>(key, value);
                current = node.left;
                return true;
            } else {
                return insertRec(node.left, key, value);
            }
        } else if (cmp > 0) {
            if (node.right == null) {
                node.right = new BSTNode<>(key, value);
                current = node.right;
                return true;
            } else {
                return insertRec(node.right, key, value);
            }
        } else {
            // Key already exists; you can choose to update the value
            current = node;
            return false; // Or update the value and return true
        }
    }

    public boolean find(K key) {
        current = findRec(root, key);
        return current != null;
    }

    private BSTNode<K,V> findRec(BSTNode<K,V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = ((String)key).compareTo((String)node.key);
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

    private void traverseInorder(BSTNode<K, V> n) {
        if (n != null) {
            traverseInorder(n.left);
            System.out.println("Word: " + n.key + " -> Documents: " + n.value);//will be changed after
            traverseInorder(n.right);
        }
}}
