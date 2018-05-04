package structures;

import java.io.*;
import java.lang.Math;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked") 
public class MyHashMap<Key, Value> {
    private transient Node[] array;
    private int size = 0;
    private double ratio = 0.75;
    
    public MyHashMap() {
        array = new Node[20];
    }

    public MyHashMap(int initialCapacity, double ratio) {
        array = new Node[initialCapacity >= 10 ? initialCapacity : 10];
        this.ratio = (ratio < 1 && ratio > 0) ? ratio : 0.75;
    }

    public void put(Key key, Value value) {
        int index = getHashIndex(key);
        if (array[index] != null) {
            if (array[index].key.equals(key)) {
                array[index].value = value;
                return;
            } else {
                do {
                    index++;
                    index = (index + 1) % array.length;
                } while (array[index] != null);
            }
        } 
        array[index] = new Node<Key, Value>(key, value);
        size++;
        resize();
    }

    public Value get(Key key) {
        int index = getHashIndex(key);
        while(array[index] != null && array[index].key.equals(key)) {
            index = (index + 1) % array.length;
        }
        return array[index] == null ? null : ((Node<Key, Value>) array[index]).value;
    }

    public int size() {
        return size;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        int index = getHashIndex(key);
        while(array[index] != null && array[index].key.equals(key)) {
            index = (index + 1) % array.length;
        }
        array[index] = null;
        size--;
    }

    private int getHashIndex(Key key) {
        return Math.abs(key.hashCode() % array.length);
    }

    private void resize() {
        if (size < array.length * ratio) return;
        Node[] oldArray = array;
        array = new Node[array.length * 2];
        Arrays.stream(oldArray)
            .filter(Objects::nonNull)
            .map(node -> ((Node<Key, Value>) node))
            .forEach(node -> put(node.key, node.value));
    }

    @Override
    public String toString() {
        return Arrays.stream(array)
            .filter(Objects::nonNull)
            .map(Node::toString)
            .collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public int hashCode() {
        return Arrays.stream(array)
            .filter(Objects::nonNull)
            .map(Node::hashCode)
            .reduce(0, (a, b) -> a + b);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MyHashMap)) return false;
        MyHashMap<Key, Value> otherMap = (MyHashMap<Key, Value>) other;
        return otherMap.size() == size && Arrays.stream(array)
            .filter(Objects::nonNull)
            .map(node -> ((Node<Key, Value>) node))
            .map(node -> node.value.equals(otherMap.get(node.key)))
            .reduce(true, (a, b) -> a && b);
    }

    private class Node<Key, Value> {
        Key key;
        Value value;
    
        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("\"%s\": \"%s\"", key.toString(), value.toString());
        }

        @Override
        public int hashCode() {
            return key.hashCode() | value.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Node) {
                Node otherNode = (Node) other;
                if(key == otherNode.key || ((key != null) && key.equals(otherNode.key))) {
                    return value == otherNode.value || ((value != null) && value.equals(otherNode.value));
                }
            }
            return false;
        }
    }
}



