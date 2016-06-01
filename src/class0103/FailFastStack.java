package class0103;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rlgs4.Stack;
/**
 * @Description 1.3.50
 *          快速检测失败迭代器
 *          通过记录可能修改节点的操作的操作数
 *          每次迭代时检查，来避免当前节点被弹出消耗或有新节点入栈
 * @author Leon
 * @date 2016-06-01 15:33:52
 */
public class FailFastStack<T> extends Stack<T>{
    private int N;                // size of the stack
    private Node<T> first;     // top of stack
    
    private int pop_num, push_num;

    // helper linked list class
    private static class Node<T> {
        private T item;
        private Node<T> next;
    }
    
    public FailFastStack() {
        first = null;
        N = 0;
        pop_num = 0;
        push_num = 0;
    }
    
    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }
    
    public void push(T item) {
        Node<T> oldfirst = first;
        first = new Node<T>();
        first.item = item;
        first.next = oldfirst;
        N++;
        push_num++;
    }
    
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        T item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        pop_num++;
        return item;                   // return the saved item
    }
    
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this)
            s.append(item + " ");
        return s.toString();
    }
    
    public Iterator<T> iterator() {
        return new FFIterator<T>(this.first, pop_num, push_num);
    }
    
    @SuppressWarnings("hiding")
    private class FFIterator<T> implements Iterator<T> {
        
        private Node<T> current;
        private int pop, push;
        
        public FFIterator(Node<T> t, int pop, int push) {
            this.current = t;
            this.pop = pop;
            this.push = push;
        }
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean hasNext() {
            if(pop != pop_num || push != push_num)
                throw new ConcurrentModificationException();
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T t = current.item;
            current = current.next; 
            return t;
        }
    
    }
    
    public static void main(String[] args) {
        FailFastStack<String> ffStack = new FailFastStack<String>();
        /*
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
        */
        for(char a: "Hello world".toCharArray())
            ffStack.push(a+"");
        
        try{
            int bPoint = 0;
            for(String s: ffStack) {
                System.out.println(s);
                if(bPoint == 5)
                    System.out.println("---"+ffStack.pop());
                bPoint++;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
