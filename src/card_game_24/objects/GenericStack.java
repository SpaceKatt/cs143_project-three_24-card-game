package card_game_24.objects;

import java.util.ArrayList;

public class GenericStack<E> {
    private ArrayList<E> list = new ArrayList<>();
    
    public GenericStack() {
        //list = null;
    }
    
    public int getSize() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public void push(E element) throws FullStackException {
        if (this.getSize() > 10000) {
            throw new FullStackException();
        }
        list.add(element);
    }
    
    public E peek() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(getSize() - 1);
    }
    
    public E pop() throws EmptyStackException {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(getSize() - 1);
    }
    
    @Override
    public String toString() {
        return "Stack: " + list.toString();
    }
}
