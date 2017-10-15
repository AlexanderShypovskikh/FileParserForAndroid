import java.io.InputStream;
import java.util.*;

public class ExampleCollections {

    public static void main(String[] args) {
        // List
        List<String> list1 = new ArrayList<>();
        list1.add("blabla");
        list1.add("blabla");
        list1.add("trololo");

        System.out.println("list1:");
        list1.forEach(System.out::println);

        // Set
        Set<String> set1 = new HashSet<>();
        set1.add("blabla");
        set1.add("blabla");
        set1.add("trololo");

        System.out.println("set1:");
//        for(Iterator<String> it = set1.iterator(); it.hasNext(); ) {
//            System.out.println(it.next());
//        }
        set1.forEach(System.out::println);

        // Map
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "val1");
        map1.put("key2", "val2");
        map1.put("key3", "val3");
        map1.put("key2", "val5");

        System.out.println("map1:");
        map1.forEach((k, v) -> System.out.println("key: " + k + " -> value: " + v));
    }
}
