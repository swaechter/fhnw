package ch.fhnw.swa.list;

public class SimpleList<E> {

    private Node<E> first = null;

    public void add(E elem) {
        if (elem == null) throw new IllegalArgumentException();
        first = new Node<>(elem, first);
    }

    public boolean contains(E elem) {
        Node<E> n = first;
        while (n != null && !n.getElem().equals(elem)) n = n.getNext();
        return n != null;
    }

    static class Node<E> {

        private E elem;

        private Node<E> next;

        public Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }

        public E getElem() {
            return elem;
        }

        public Node<E> getNext() {
            return next;
        }
    }

    public static void main(String[] args) {
        SimpleList<Integer> list = new SimpleList<>();
        list.add(6);
        list.add(1);
        list.add(3);
        list.contains(1);
    }
}
