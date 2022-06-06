package Structure;

public class Fila<T> {
	private Lista<T> queue = new Lista<>();
	
	public void add(T item) {
        queue.add(item);
	}

	public T poll() {
        if(queue.size() == 0) {
	        throw new RuntimeException("Pilha vazia.");
	    }

        T item = queue.get(0);
        queue.remove(0);

	    return item;
	}

	public T peek() {
	    if(queue.size() == 0) {
	        throw new RuntimeException("Pilha vazia.");
	    }

	    return queue.get(0);
	}

    public boolean isEmpty() {
	    return queue.isEmpty();
	}
}
