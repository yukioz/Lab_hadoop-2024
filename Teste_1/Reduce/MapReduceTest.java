import java.io.*;
import java.util.*;

public class MapReduceTest {
    public static void main(String[] args) throws Exception {
        // Caminho para o arquivo de entrada
        String filePath = "input.txt";

        // Instanciar Mapper e Reducer
        MapperClass mapper = new MapperClass();
        ReducerClass reducer = new ReducerClass();

        // Simular a execução do Mapper
        List<Pair<String, Integer>> intermediateData = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            mapper.map(line, intermediateData);
        }
        reader.close();

        // Simular a execução do Reducer
        Map<String, Integer> result = new HashMap<>();
        reducer.reduce(intermediateData, result);

        // Exibir resultado
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class MapperClass {
    public void map(String line, List<Pair<String, Integer>> output) {
        String[] words = line.split("\\s+");
        for (String word : words) {
            output.add(new Pair<>(word, 1));
        }
    }
}

class ReducerClass {
    public void reduce(List<Pair<String, Integer>> input, Map<String, Integer> output) {
        for (Pair<String, Integer> pair : input) {
            String key = pair.getKey();
            int value = pair.getValue();
            output.put(key, output.getOrDefault(key, 0) + value);
        }
    }
}

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
