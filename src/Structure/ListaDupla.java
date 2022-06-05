package Structure;

public class ListaDupla<T> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    class Node {
        T data;    
        Node next = null;
        Node prev = null;
                
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
	    	node.prev = tail;
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

    public void addAt(T item, int index) {
	    Node node = getNode(index);
        Node newNode = new Node(item);

        if(node.prev == null) {
            head = newNode;
        }
        
        newNode.next = node;
        newNode.prev = node.prev;
        
        if(node.prev != null) {
            node.prev.next = newNode;
        }

        node.prev = newNode;
        
        size++;
	}

    public void replace(int index, T data)  {
        Node node = getNode(index);
        node.data = data;
    }

    public int size() {
        return size;
    }
}
