package Structure;

public class Pilha<T> {
    private int size = 5;
	private int top = -1;
	private Object[] stack = new Object[size];
	
	public void push(T item) {
        handleArraySize();

        top++;
        stack[top] = item;
	}

	public T pop() {
        if(top == -1) {
	        throw new RuntimeException("Pilha vazia.");
	    }

        T item = (T) stack[top];
        stack[top] = null;
        top--;

	    return item;
	}

	public T peek() {
	    if(top == -1) {
	        throw new RuntimeException("Pilha vazia.");
	    }
	    
	    return (T) stack[top];
	}

    public boolean isEmpty() {
	    return top == -1;
	}

    private void handleArraySize() {
        if (top == size - 1) {
            size *= 2;
            Object[] newArray = new Object[size];

            for (int i = 0; i <= top; i++) {
                newArray[i] = stack[i];
            }

            stack = newArray;
        }
    }
}
