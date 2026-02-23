## Домашнее задание №1
Необходимо написать собственную реализацию HashMap. Обязательные методы: get, put, remove.

## Реализованы методы:
- size()
- isEmpty()
- containsKey(Object key)
- containsValue(Object value)
- get(Object key)
- put(K key, V value)
- remove(Object key)
- putAll(Map<? extends K, ? extends V> m)
- clear()

## Не реализованы методы
- keySet()
- values()
- entrySet()
В задании реализация этих методов необязательна, но требует имплементации Set и Collection (либо использование затычек в виде существующих коллекций, что нонсенс).
Поставлены заглушки в виде throw new UnsupportedOperationException();

## Написаны тесты
Тесты проверяют корректность всех операций в сравнении с эталонной реализацией HashMap из Collections Framework
