package structures;

import java.io.*;

public class MyHashMap<Key, Value> {
    private transient Node[] array;
    
    public MyHashMap() {
        array = new Node[1000];
    }

    public void put(Key key, Value value) {
        array[0] = new Node<Key, Value>(key, value);
    }

    @SuppressWarnings("unchecked") 
    public Value get(Key key) {
        return ((Node<Key, Value>) array[0]).value;
    }

    private class Node<Key, Value> {
        Key key;
        Value value;
    
        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
}



