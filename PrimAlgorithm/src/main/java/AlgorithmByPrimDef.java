import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AlgorithmByPrimDef {

    public static List<Edge> primMST(int[][] graph) {
        int V = graph.length; // кол-во вершин в графе
        boolean[] visited = new boolean[V]; // массив посещенных вершин, index - номер вершины.  true - посещена/ false - нет
        int[] dist = new int[V]; // dist - расстояние от текущей вершины до каждой вершины в графе (на каждом шаге стремимся к его минимизации)
        int[] parent = new int[V]; // массив вершин, из которых кратчайшим путем можно попасть в вершину соотвествующую индексу. (назовем такие вершины - родительскими)
        Arrays.fill(dist, Integer.MAX_VALUE); // заполнение массива максимальными числами
        Arrays.fill(parent, -1); // заполнение длинами равными -1

        // Создание приоритетное очереди на основе длин из node
        PriorityQueue<Node> pq = new PriorityQueue<>(V, Comparator.comparingInt(node -> node.distance));

        dist[0] = 0; // начальное известное расстояние
        pq.offer(new Node(0, 0)); // начальная вершина в очереди

        // Пока очередь не пуста
        while (!pq.isEmpty()) {
            // Берем вершину с минимальным ключом из приоритетной очереди
            Node node = pq.poll();
            int u = node.vertex;
            visited[u] = true; // отмечаем вершину как посещенную

            // проходимся по всем вершинам смежным с текущей
            for (int v = 0; v < V; v++) {
                int weight = graph[u][v]; // расстояние между вешиной u и v (значение на пересечение столбцов, т.к матрица смежности)
                // если расстояние не равно нулю, вершина не посещена и расстояние меньше, чем найденное на предыдущих шагах
                if (weight != 0 && !visited[v] && weight < dist[v]) {
                    dist[v] = weight; // обновляем значение минимального расстояния
                    parent[v] = u; // обновляем родительскую вершину
                    pq.offer(new Node(v, dist[v])); // (pq.add()) добавляем вершину в очередь
                }
            }
        }
        // записываем получившиеся EDGE в граф
        List<Edge> mst = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            mst.add(new Edge(i, parent[i], graph[i][parent[i]])); // элементы: 1 - вершина, 2-родительская вершина, 3-расстояние
        }

        return mst;
    }

    private static class Node {
        int vertex;
        int distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    private static class Edge {
        int u;
        int v;
        int weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        for (int k = 10; k <= 110; k++) {
            Path path = Path.of("src/main/java/data/file" + (k-9));
            List<String> lines = Files.readAllLines(path);
            int[][] m = new int[lines.size()][lines.size()];
            int i = -1, j = -1;
            for(String line : lines){
                i += 1;
                for(String el: line.split(" ")){
                    j+=1;
                    m[i][j] = Integer.parseInt(el);
                }
                j = -1;
            }
            System.out.print(m.length + "- ");
            long start = System.nanoTime();
            primMST(m);
            long finish = System.nanoTime();
            System.out.println((finish - start) / 1000);
        }
    }
}
