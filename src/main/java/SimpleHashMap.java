import java.util.*;

/**
 * Реализация интерфейса Map на основе хэш-таблицы с цепочками.
 * Использует массив бакетов и двусвязные списки для обработки коллизий.
 */
public class SimpleHashMap<K, V> implements Map<K, V> {

    private final float loadFactor = 0.75f;

    private int size = 0;
    private int capacity = 16;
    private int threshold = (int) (capacity * loadFactor);
    private Node<K, V>[] buckets = new Node[capacity];

    // ПУБЛИЧНЫЕ МЕТОДЫ ИНТЕРФЕЙСА Map

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int bucket = findBucket(key);
        return findNode(key, bucket) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            Node<K, V> node = buckets[i];
            while (node != null) {
                if (Objects.equals(node.value, value)) return true;
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int bucket = findBucket(key);
        Node<K, V> node = findNode(key, bucket);
        if (node == null) return null;
        return node.value;
    }

    @Override
    public V put(K key, V value) {
        if (size >= threshold) resize();

        int bucket = findBucket(key);
        Node<K, V> node = findNode(key, bucket);

        if (node != null) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node = new Node<>(key, value);
        node.next = buckets[bucket];     // вставляем в начало цепочки
        node.prev = null;

        if (buckets[bucket] != null) buckets[bucket].prev = node;

        buckets[bucket] = node;
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        int bucket = findBucket(key);
        Node<K, V> node = findNode(key, bucket);
        if (node == null) return null;

        V oldValue = node.value;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            buckets[bucket] = node.next;
        }
        if (node.next != null) node.next.prev = node.prev;

        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) buckets[i] = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        // Тут надо заодно имплементировать Set, думаю это выходит за рамки задания
        throw new UnsupportedOperationException("Метод keySet() не реализован");
    }

    @Override
    public Collection<V> values() {
        // Тут надо заодно имплементировать Collection, думаю это выходит за рамки задания
        throw new UnsupportedOperationException("Метод values() не реализован");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // Тут надо заодно имплементировать Set, думаю это выходит за рамки задания
        throw new UnsupportedOperationException("Метод entrySet() не реализован");
    }

    // ВНУТРЕННИЙ КЛАСС Node<K, V>

    private static class Node<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key) && Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

    }

    // ПРИВАТНЫЕ МЕТОДЫ

    private int findBucket(Object key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        int mixedHash = (hash ^ (hash >>> 16));      // смешивает биты для равномерного распределения по инту
        return mixedHash & (capacity - 1);           // остаток от деления на степень двойки
    }

    private Node<K, V> findNode(Object key, int bucket) {
        Node<K, V> node = buckets[bucket];
        while (node != null) {
            if (Objects.equals(node.key, key)) return node;
            node = node.next;
        }
        return null;
    }

    private void resize() {
        int newCapacity = capacity * 2;           // всегда степень двойки
        Node<K, V>[] newBuckets = new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> node = buckets[i];
            while (node != null) {
                Node<K, V> nextNode = node.next;
                int hash = node.key == null ? 0 : node.key.hashCode();
                int newBucket = (hash ^ (hash >>> 16)) & (newCapacity - 1);
                node.next = newBuckets[newBucket];
                node.prev = null;
                if (newBuckets[newBucket] != null) newBuckets[newBucket].prev = node;
                newBuckets[newBucket] = node;
                node = nextNode;
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
        threshold = (int) (newCapacity * loadFactor);
    }

}
