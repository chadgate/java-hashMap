import structures.*;

public class Something {
    public static void main(String[] args) {
        MyHashMap<String, String> something = new MyHashMap<String, String>();
        something.put("one", "two");
        System.out.println(something.get("Something"));
    }
}