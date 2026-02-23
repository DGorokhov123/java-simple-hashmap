import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashMapTest {

    @Test
    public void testPutAndGet() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        simpleHashMap.put("key1", "value1");
        simpleHashMap.put("key2", "value2");

        assertEquals(hashMap.get("key1"), simpleHashMap.get("key1"));
        assertEquals(hashMap.get("key2"), simpleHashMap.get("key2"));
        assertEquals(hashMap.get("key3"), simpleHashMap.get("key3"));
    }

    @Test
    public void testPutExistingKey() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put("key1", "value1");
        simpleHashMap.put("key1", "value1");

        String hashMapOldValue = hashMap.put("key1", "newValue");
        String simpleHashMapOldValue = simpleHashMap.put("key1", "newValue");

        assertEquals(hashMapOldValue, simpleHashMapOldValue);
    }

    @Test
    public void testContainsKey() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put("key1", "value1");
        simpleHashMap.put("key1", "value1");

        assertEquals(hashMap.containsKey("key1"), simpleHashMap.containsKey("key1"));
        assertEquals(hashMap.containsKey("key2"), simpleHashMap.containsKey("key2"));
    }

    @Test
    public void testContainsValue() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put("key1", "value1");
        simpleHashMap.put("key1", "value1");

        assertEquals(hashMap.containsValue("value1"), simpleHashMap.containsValue("value1"));
        assertEquals(hashMap.containsValue("value2"), simpleHashMap.containsValue("value2"));
    }

    @Test
    public void testRemove() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        simpleHashMap.put("key1", "value1");
        simpleHashMap.put("key2", "value2");

        String hashMapOldValue = hashMap.remove("key1");
        String simpleHashMapOldValue = simpleHashMap.remove("key1");

        assertEquals(hashMapOldValue, simpleHashMapOldValue);

        hashMapOldValue = hashMap.remove("key3");
        simpleHashMapOldValue = simpleHashMap.remove("key3");
        assertEquals(hashMapOldValue, simpleHashMapOldValue);
    }

    @Test
    public void testSizeAndIsEmpty() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        assertEquals(hashMap.size(), simpleHashMap.size());
        assertTrue(simpleHashMap.isEmpty());

        hashMap.put("key1", "value1");
        simpleHashMap.put("key1", "value1");
        assertEquals(hashMap.size(), simpleHashMap.size());
        assertFalse(simpleHashMap.isEmpty());

        hashMap.put("key2", "value2");
        simpleHashMap.put("key2", "value2");
        assertEquals(hashMap.size(), simpleHashMap.size());

        hashMap.remove("key1");
        simpleHashMap.remove("key1");
        assertEquals(hashMap.size(), simpleHashMap.size());
    }

    @Test
    public void testClear() {
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        simpleHashMap.put("key1", "value1");
        simpleHashMap.put("key2", "value2");
        assertFalse(simpleHashMap.isEmpty());

        simpleHashMap.clear();
        assertTrue(simpleHashMap.isEmpty());
    }

    @Test
    public void testPutAll() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("key1", "value1");
        sourceMap.put("key2", "value2");
        sourceMap.put("key3", "value3");

        hashMap.putAll(sourceMap);
        simpleHashMap.putAll(sourceMap);

        assertEquals(hashMap.get("key1"), simpleHashMap.get("key1"));
        assertEquals(hashMap.get("key2"), simpleHashMap.get("key2"));
        assertEquals(hashMap.get("key3"), simpleHashMap.get("key3"));
    }

    @Test
    public void testNullKeyAndValue() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        hashMap.put(null, "valueForNullKey");
        simpleHashMap.put(null, "valueForNullKey");
        assertEquals(hashMap.get(null), simpleHashMap.get(null));
        assertTrue(simpleHashMap.containsKey(null));

        hashMap.put("keyWithNullValue", null);
        simpleHashMap.put("keyWithNullValue", null);
        assertEquals(hashMap.get("keyWithNullValue"), simpleHashMap.get("keyWithNullValue"));
        assertTrue(simpleHashMap.containsKey("keyWithNullValue"));
    }

    @Test
    public void testManyElements() {
        Map<Integer, String> hashMap = new HashMap<>();
        Map<Integer, String> simpleHashMap = new SimpleHashMap<>();

        for (int i = 0; i < 10000; i++) {
            hashMap.put(i, "value" + i);
            simpleHashMap.put(i, "value" + i);
        }

        assertEquals(hashMap.size(), simpleHashMap.size());

        for (int i = 0; i < 10000; i++) {
            assertEquals(hashMap.get(i), simpleHashMap.get(i));
            assertTrue(simpleHashMap.containsKey(i));
        }
    }

    @Test
    public void testRandomOperations() {
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> simpleHashMap = new SimpleHashMap<>();

        String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] values = {"one", "two", "three", "four", "five", "six", "seven", "eight"};

        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], values[i]);
            simpleHashMap.put(keys[i], values[i]);
            assertEquals(hashMap.get(i), simpleHashMap.get(i));
        }

        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], values[i] + "_updated");
            simpleHashMap.put(keys[i], values[i] + "_updated");
            assertEquals(hashMap.get(i), simpleHashMap.get(i));
        }

        for (int i = 0; i < keys.length; i++) {
            if (i % 2 == 0) {
                hashMap.remove(keys[i]);
                simpleHashMap.remove(keys[i]);
                assertEquals(hashMap.get(i), simpleHashMap.get(i));
            }
        }

        for (int i = 0; i < 1000; i++) {
            hashMap.put("new_key_" + i, "new_value_" + i);
            simpleHashMap.put("new_key_" + i, "new_value_" + i);
            assertEquals(hashMap.get(i), simpleHashMap.get(i));
        }

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            assertTrue(simpleHashMap.containsKey(entry.getKey()));
            assertTrue(simpleHashMap.containsValue(entry.getValue()));
        }

        assertEquals(hashMap.size(), simpleHashMap.size());
        for (int i = 0; i < hashMap.size(); i++) {
            assertEquals(hashMap.get(i), simpleHashMap.get(i));
        }

        hashMap.clear();
        simpleHashMap.clear();
        assertTrue(simpleHashMap.isEmpty());
    }

}
