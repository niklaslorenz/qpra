package qpra.parser;

import java.util.Stack;

public class ParseStack<T> {

    public interface Handler<T> {
        void handle(T t);
    }

    private final Stack<T> valueStack;
    private final Stack<Handler<T>> handlerStack;

    public ParseStack() {
        valueStack = new Stack<>();
        handlerStack = new Stack<>();
    }

    public void clear() {
        valueStack.clear();
        handlerStack.clear();
    }

    public void push(T value) {
        valueStack.push(value);
    }

    public void push(Handler<T> handler) {
        handlerStack.push(handler);
    }

    public T value() {
        return valueStack.peek();
    }

    public Handler<T> handler() {
        return handlerStack.peek();
    }

    public T popValue() {
        return valueStack.pop();
    }

    public Handler<T> popHandler() {
        return handlerStack.pop();
    }

    public void handle(T t) {
        handlerStack.peek().handle(t);
    }

    public void handle() {
        handlerStack.peek().handle(valueStack.pop());
    }

}
