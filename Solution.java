import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public static void main(String[] args) {
        List<Pair<String,String>> data = new ArrayList<>();
        data.add(new Pair("Donald", "Joe's"));
        data.add(new Pair("Donald", "Chai Chanak"));
        data.add(new Pair("Donald", "Joe's"));
        data.add(new Pair("Donald", "Pedro's"));

        data.add(new Pair("Bugs", "Joe's"));
        data.add(new Pair("Bugs", "Pedro's"));
        data.add(new Pair("Bugs", "MickyD's"));

        data.add(new Pair("Goofy", "MickyD's"));
        data.add(new Pair("Goofy", "MickyD's"));
        MapReduceImpl impl = new MapReduceImpl();
        System.out.println(impl.RunMapReduce(Collections.singletonList(data)));

        // in above data we can see Bugs and Donald both went to Joe's and Pedro's all the other pairs only have one customer visiting multiple times
    }
}

class MapReduceImpl extends MapReduce<List<Pair<String, String>>, String, String, List<Pair<String,String>>>{

    public List<Pair<String, String>> Map(List<Pair<String,String>> input) {
        //assuming that input is never empty
        // remove duplicate rows from the list
        List<Pair<String,String>> uniques = input.stream().distinct().collect(Collectors.toList());
        List<Pair<String,String>> result = new ArrayList<>();
        String key = uniques.get(0).getKey();
        for (int i=0; i < uniques.size(); i++) {
            for(int j = i+1; j< uniques.size(); j++) {
                if (key.equals(uniques.get(j).getKey())) {
                    Pair addThis = new Pair("("+uniques.get(i).getValue() + ","+ uniques.get(j).getValue()+")", "("+uniques.get(i).getValue() + ","+ uniques.get(j).getValue()+")");
                    result.add(addThis);
                } else {
                    break;
                }
            }
            if(i+1 < uniques.size()) {
                key = uniques.get(i + 1).getKey();
            }
        }

        return result;
    }

    @Override
    public List<Pair<String, String>> Reduce(Pair<String, List<String>> input) {
        List<Pair<String,String>> result = new ArrayList<>();
        result.add(new Pair("A" + input.key,input.getValue().size()));
        return result;
    }
}

class Pair<K, V> {
    public K key;
    public V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void set(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        //return "key: " + key + "  value: " + value;
        return  "(" +key + "," + value + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Pair) {
            @SuppressWarnings("unchecked")
            Pair<K, V> pair1 = (Pair<K, V>) object;
            return this.key.equals(pair1.key) && this.value.equals(pair1.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result  = 17;
        result = 31 * result + key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}


abstract class MapReduce<I, K, V, O> {

    public abstract List<Pair<K, V>> Map(I input);

    public abstract O Reduce(Pair<K, List<V>> input);

    public Map<K, List<V>> Shuffle(List<Pair<K, V>> mapOutList) {
        Map<K, List<V>> reduceInMap = new HashMap<K, List<V>>();
        for (Pair<K, V> output : mapOutList) {
            List<V> valList;
            if (reduceInMap.containsKey(output.getKey())) {
                valList = reduceInMap.get(output.getKey());
            } else {
                valList = new ArrayList<V>();
                reduceInMap.put(output.getKey(), valList);
            }
            valList.add(output.getValue());
        }
        return reduceInMap;
    }

    public List<O> RunMapReduce(List<I> mapInList) {
        List<Pair<K, V>> mapOutList = new ArrayList<Pair<K, V>>();
        for (I input : mapInList) {
            mapOutList.addAll(Map(input));
        }

        Map<K, List<V>> reduceInMap = Shuffle(mapOutList);

        List<O> reduceOutList = new ArrayList<O>();
        for (Map.Entry<K, List<V>> input : reduceInMap.entrySet()) {
            reduceOutList.add(Reduce(new Pair<K, List<V>>(input.getKey(),
                    input.getValue())));
        }
        return reduceOutList;
    }
}

