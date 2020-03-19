import com.sun.jmx.snmp.SnmpSecurityException;

import java.util.*;

public class ArrayMap<K, V> extends AbstractMap<K, V> {
    class ArrayMapEntry<K, V> implements Map.Entry<K, V> {
        public K key;
        public V value;

        public ArrayMapEntry(K key, V value) {  //constructor
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {    // schimba valoarea
            V old = this.value;
            this.value = value;
            return old;
        }

        public String toString() {  //printeaza
            String result = "\nValue: " + this.value + "\nKey: " + this.key;
            return result;
        }

        public int hashCode() {     //returneaza hashcode-ul
            return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^
                    (this.getValue() == null ? 0 : this.getValue().hashCode());
        }

        @SuppressWarnings("unchecked")
        public boolean equals(Object other) {
            if (!(other instanceof ArrayMapEntry))
                return false;
            ArrayMapEntry<K, V> o = (ArrayMapEntry<K, V>) other;
            return (this.getKey() == null ?
                    o.getKey() == null : this.getKey().equals(o.getKey()) &&
                    (this.getValue() == null ?
                            o.getValue() == null : this.getValue().equals(o.getValue())));
        }
    }
    public Set<Map.Entry<K, V>> entries = null;
    public ArrayList<ArrayMapEntry<K, V>> list = null;

    public ArrayMap() {
        list = new ArrayList<>();
    }
    public ArrayMap(Map map) {
        list = new ArrayList<>();
        putAll(map);
    }

    public int size() {
        return list.size();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public V put(K key, V value)
    {
        int size = list.size();
        Map.Entry<K, V> entry = null;
        int i;
        if (key == null) {
            for (i = 0; i < size; i++) {
                entry = list.get(i);
                if (entry.getKey() == null) {
                    break;
                }
            }
        }
        else
        {
            for (i = 0; i < size; i++) {
                entry = list.get(i);
                if (key.equals(entry.getKey())) {
                    break;
                }
            }
        }
        V oldValue = null;
        if (i < size) {
            oldValue = entry.getValue();
            entry.setValue((V) value);
        } else {
            list.add(new ArrayMapEntry(key, value));
        }
        return oldValue;
    }

    public boolean containsKey(Object key) {
        Map.Entry<K, V> entry = null;
        int i;
            for (i = 0; i < list.size(); i++) {
                entry = list.get(i);
                if (key.equals(entry.getKey()))
                    return true;
            }
        return false;
    }

    public V get(Object key) {
        Map.Entry<K, V> entry = null;
        int i;
        for (i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<K, V>> entrySet() {    // returneaza entries care contine aceleasi element ca list
        if (entries == null) {
            entries = new AbstractSet() {
                public void clear() {
                    list.clear();
                }

                @SuppressWarnings("rawtypes")
                public Iterator iterator() {
                    return list.iterator();
                }

                public int size() {
                    return list.size();
                }
            };
        }
        return entries;
    }
}
