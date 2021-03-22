package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack {

    private LinkedList<String> stackElements = new LinkedList<>();
    private int boundValue = 5;

    public TqsStack(){
        this.stackElements = new LinkedList<String>();
    }

    public boolean isEmpty(){
        return stackElements.size() == 0;
    }

    public LinkedList<String> push(String element){
        if(stackElements.size() < boundValue) {
            stackElements.addFirst(element);
            return stackElements;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public LinkedList<String> pop(){
        if(stackElements.size()>0){
            stackElements.removeFirst();
            return stackElements;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public String peek(){
        if(stackElements.size()>0){
            String element = stackElements.getFirst();
            return element;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public int stackSize(){
        int size = stackElements.size();
        return size;
    }
}
