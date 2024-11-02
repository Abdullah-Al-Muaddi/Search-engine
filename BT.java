
public class BT<T> {
    // Delete the comments if not needed

    enum Order {
        INORDER, POSTORDER, PREORDER
    };

    enum Relative {
        ROOT, LEFTCHILD, RIGHTCHILD, PARENT
    };

    private static class BTNode<T> {

        T data;
        BTNode<T> left, right;

        public BTNode(T data) {
            this.data = data;
            left = right = null;
        }
    }

    private BTNode<T> root, current;

    public BT() {
        root = current = null;
    }

    public boolean empty() {
        return root == null;
    }

    public T retrieve() {
        return current.data;
    }

    public void update(T data) {
        current.data = data;
    }

    public boolean insert(Relative rel, T data) {
        switch (rel) {
            case ROOT:
                root = current = new BTNode<>(data);
                return true;
            case PARENT:
                return false;
            case LEFTCHILD:
                if (current.left != null) {
                    return false;
                }
                current.left = new BTNode<>(data);
                current = current.left;
                return true;
            case RIGHTCHILD:
                if (current.right != null) {
                    return false;
                }
                current.right = new BTNode<>(data);
                current = current.right;
                return true;
            default:
                return false;
        }
    }

    public boolean find(Relative rel) {
        switch (rel) {
            case ROOT:
                current = root;
                return true;
            case PARENT:
                if (current == root) {
                    return false;
                }
                current = findParent(current, root);
                return true;
            case LEFTCHILD:
                if (current.left == null) {
                    return false;
                }
                current = current.left;
                return true;
            case RIGHTCHILD:
                if (current.right == null) {
                    return false;
                }
                current = current.right;
                return true;
            default:
                return false;
        }
    }

    private BTNode<T> findParent(BTNode<T> child, BTNode<T> node) {
        if (node == null || (node.left == null && node.right == null)) {
            return null;
        }
        if (node.left == child || node.right == child) {
            return node;
        }
        BTNode<T> foundNode = findParent(child, node.left);
        if (foundNode != null) {
            return foundNode;
        }
        return findParent(child, node.right);
    }

    // public void deleteSubtree() {
    //     if (current == root) {
    //         root = current = null;
    //     } else {
    //         BTNode<T> parent = findParent(current, root);
    //         if (parent != null) {
    //             if (parent.left == current) {
    //                 parent.left = null;
    //             } else {
    //                 parent.right = null;
    //             }
    //         }
    //         current = root;
    //     }
    // }
    // public void traverse(Order ord) {
    //     switch (ord) {
    //         case INORDER:
    //             traverseInorder(root);
    //             break;
    //         case PREORDER:
    //             traversePreorder(root);
    //             break;
    //         case POSTORDER:
    //             traversePostorder(root);
    //             break;
    //     }
    // }
    // private void traversePreorder(BTNode<T> node) {
    //     if (node != null) {
    //         System.out.println(node.data);
    //         traversePreorder(node.left);
    //         traversePreorder(node.right);
    //     }
    // }
    // private void traverseInorder(BTNode<T> node) {
    //     if (node != null) {
    //         traverseInorder(node.left);
    //         System.out.println(node.data);
    //         traverseInorder(node.right);
    //     }
    // }
    // private void traversePostorder(BTNode<T> node) {
    //     if (node != null) {
    //         traversePostorder(node.left);
    //         traversePostorder(node.right);
    //         System.out.println(node.data);
    //     }
    // }
    // public int countLeafs() {
    //     return countLeafsRec(root);
    // }
    // private int countLeafsRec(BTNode<T> node) {
    //     if (node == null)
    //         return 0;
    //     if (node.left == null && node.right == null)
    //         return 1;
    //     return countLeafsRec(node.left) + countLeafsRec(node.right);
    // }
    // public static <T> int countLeafs(BT<T> bt) {
    //     if (bt.empty())
    //         return 0;
    //     bt.find(Relative.ROOT);
    //     return countLeafsRec(bt);
    // }
    // private static <T> int countLeafsRec(BT<T> bt) {
    //     if (bt.isLeaf())
    //         return 1;
    //     int count = 0;
    //     if (bt.find(Relative.LEFTCHILD)) {
    //         count += countLeafsRec(bt);
    //         bt.find(Relative.PARENT);
    //     }
    //     if (bt.find(Relative.RIGHTCHILD)) {
    //         count += countLeafsRec(bt);
    //         bt.find(Relative.PARENT);
    //     }
    //     return count;
    // }
    // private boolean isLeaf() {
    //     return current.left == null && current.right == null;
    // }
}
