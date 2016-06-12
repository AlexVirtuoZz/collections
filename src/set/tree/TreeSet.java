package set.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class TreeSet<T extends Comparable<T>> implements Set<T> {
    private Node<T> root;
    private int size;

    public TreeSet() {
    }

    public TreeSet(Collection<? extends T> c) {
        addAll(c);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Specified object is null.");
        }
        Node<T> parent = findParentNode((T) o);
        if (parent == null || parent.value.compareTo((T) o) != 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException("Specific element is null.");
        }
        Node<T> parent = findParentNode(t);
        if (parent == null) {
            root = new Node(null, null, null, t);
            size++;
            return true;
        }
        int resOfCompare = t.compareTo(parent.value);
        if (resOfCompare == 0) {
            return false;
        } else {
            if (resOfCompare < 0) {
                parent.left = new Node(null, null, parent, t);
            } else {
                parent.right = new Node(null, null, parent, t);
            }
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("Specific element is null.");
        }
        Node<T> node = findParentNode((T) o);
        if (node == null || ((T) o).compareTo(node.value) != 0) {
            return false;
        } else {
            if (isLeaf(node)) {
                if (node == root) {
                    root = null;
                } else {
                    if (node.parent.left == node) {
                        node.parent.left = null;
                    } else {
                        node.parent.right = null;
                    }
                }
            } else {
                Node<T> parent = null;
                if (node.left == null) {
                    parent = findMinRight(node);
                } else {
                    parent = findMaxLeft(node);
                }
                node.value = parent.value;
            }
            size--;
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean addAll = true;
        for (T t : c) {
            addAll &= this.add(t);
        }
        return addAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removeAll = true;
        for (Object obj : c) {
            removeAll &= this.remove(obj);
        }
        return removeAll;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private static class Node<T> {
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        T value;

        Node(Node<T> left, Node<T> right, Node<T> parent, T value) {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.value = value;
        }
    }

    private Node<T> findParentNode(T t) {
        Node<T> current = root;
        Node<T> parent = null;
        while (current != null) {
            int resOfCompare = t.compareTo(current.value);
            parent = current;
            if (resOfCompare < 0) {
                current = current.left;
            } else if (resOfCompare > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return parent;
    }

    private Node<T> findMaxLeft(Node<T> node) {
        Node<T> current = node.left;
        Node<T> parent = node;
        boolean isRight = false;
        while (current.right != null) {
            parent = current;
            current = current.right;
            isRight = true;
        }
        if (!isLeaf(current)) {
            if (isRight) {
                parent.right = current.left;
            } else {
                parent.left = current.left;
            }
            current.left.parent = parent;
        } else {
            if (isRight) {
                parent.right = null;
            } else {
                parent.left = null;
            }
        }
        return current;
    }

    private Node<T> findMinRight(Node<T> node) {
        Node<T> current = node.right;
        Node<T> parent = node;
        boolean isLeft = false;
        while (current.left != null) {
            parent = current;
            current = current.left;
            isLeft = true;
        }
        if (!isLeaf(current)) {
            if (isLeft) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
            current.right.parent = parent;
        } else {
            if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        return current;
    }

    private boolean isLeaf(Node<T> node) {
        return node.left == null && node.right == null;
    }

    @Override
    public String toString() {
        return getTreeNode(root, new StringBuilder());
    }

    private String getTreeNode(Node<T> node, StringBuilder tree) {
        if (node.left != null) {
            getTreeNode(node.left, tree);
        }
        tree.append(" ").append(node.value).append(" ");
        if (node.right != null) {
            getTreeNode(node.right, tree);
        }
        return tree.toString();
    }
}