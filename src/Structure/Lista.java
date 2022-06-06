package Structure;

public class Lista<T> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    class Node {
        T data;    
        Node next = null;
                
        public Node(T data) {
            this.data = data;
        }    
    }
	
    public void add(T item) {
        Node node = new Node(item);

	    if(head == null) { 
	        head = node;
	        tail = node;
	    } else {
	    	tail.next = node;
	        tail = node;
	    }
	    
	    size++;
    }

    private Node getNode(int index) {
	    Node iterator = head;

        for (int i = 0; i < index; i++) {
            iterator = iterator.next;
        }
	    
	    return iterator;
	}

	public T get(int index) {
		return getNode(index).data;
	}

    public void remove(int index) {
        Node node = getNode(index);
        Node prevNode = getNode(index - 1);

        if (index == 0) {
            head = node.next;
            node.next = null;
        } else {
            prevNode.next = node.next;
        }

        if (index == size - 1) {
            tail = prevNode;
        }

        size--;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
