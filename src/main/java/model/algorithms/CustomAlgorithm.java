package model.algorithms;

import javafx.util.Pair;
import model.algorithms.commitments.AlgorithmCommitments;
import model.Cell;
import model.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class CustomAlgorithm implements AlgorithmCommitments {
    private class Vertex {
        private final ArrayList<Vertex> children;
        private final int row;
        private final int column;
        private final int stepSize;
        private boolean isThereWay;

        private Vertex(int row, int column, int stepSize) {
            this.row = row;
            this.column = column;
            this.stepSize = stepSize;
            children = new ArrayList<>();
        }
    }

    private Field field;

    // Мапа с ключем в качестве индексов ячейки и значением в качестве вершины, соответствующей ячейке
    private HashMap<Pair<Integer, Integer>, Vertex> vertices = new HashMap<>();

    private Vertex startVertex;
    private ArrayList<ArrayList<Cell>> ways;

    private int height;
    private int width;

    @Override
    public ArrayList<ArrayList<Cell>> getWays(Field field) {
        this.field = field;

        height = field.getHeight();
        width = field.getWidth();

        if(height == 0 || width == 0)
            return new ArrayList();

        // Вершина, из которой считаем пути
        startVertex = new Vertex(0, 0, field.getCell(0, 0).getNumber());

        // Рекурсивно ищем потомков, которые ведут к финальной вершине, для заданной в качестве аргумента вершины
        findChildren(startVertex);

        ways = new ArrayList<ArrayList<Cell>>();
        createWaysOnBaseOfVertices(startVertex, new ArrayList<Cell>());

        vertices.clear();

        return ways.isEmpty() ? null : ways;
    }

    // Время - O(N)
    // Память - O(N)
    // N - Количество ячеек, в которые мы можем попасть
    private void findChildren(Vertex vertex) {
        int row = vertex.row;
        int column = vertex.column;
        int step = vertex.stepSize;

        // Если текущая вершина является конечной
        if(row == height - 1 && column == width - 1) {

            // Указываем в мапе вершин, что данная вершина рассмотрена
            // O(1) в среднем
            vertices.put(new Pair<>(row, column), vertex);

            // Указываем, что данная вершина является частью как минимум одного из путей
            vertex.isThereWay = true;

            return;
        }


        // Проверка правых и нижних вершин, которые мы можем посетить из текущей:

        BiConsumer<Integer, Integer> biConsumer = (rowOfNextVertex, columnOfNextVertex) -> {

            // Если новая вершина выходит за пределы поля
            if(rowOfNextVertex >= height|| columnOfNextVertex >= width) {
                return;
            }

            // Берём элемент из мапы вершин по индексам следующей вершины
            // O(1) в среднем
            Vertex nextVertex = vertices.get(new Pair<>(rowOfNextVertex, columnOfNextVertex));

            // Если следующая вершина != null, то информация о возможных путях из неё уже высчитана
            if (nextVertex != null) {

                // Если из следующей вершины есть путь
                if (nextVertex.isThereWay) {

                    // Добавляем к текущей вершине в качестве потомка следующую вершину
                    vertex.children.add(nextVertex);

                    // Указываем, что из текущей вершины есть путь
                    vertex.isThereWay = true;
                }
            }
            else {
                nextVertex = new Vertex(
                        rowOfNextVertex,
                        columnOfNextVertex,
                        field.getCell(rowOfNextVertex, columnOfNextVertex).getNumber()
                );

                // Ищем потомков для новой вершины, ведущих к финальной вершине
                findChildren(nextVertex);

                /// Если из следующей вершины есть путь
                if(nextVertex.isThereWay) {

                    // Добавляем к текущей вершине в качестве потомка следующую вершину
                    vertex.children.add(nextVertex);

                    // Указываем, что из текущей вершины есть путь
                    vertex.isThereWay = true;
                }
            }
        };

        // Проверка правой вершины
        biConsumer.accept(row, column + step);

        // Проверка нижней вершины
        biConsumer.accept(row + step, column);

        // Указываем, что текущая вершина уже высчитана
        // O(1) в среднем
        vertices.put(new Pair<>(row, column), vertex);
    }

    // Время - O(M)
    // Память - O(M)
    // M - Суммарная длина всех путей
    private void createWaysOnBaseOfVertices(Vertex vertex, ArrayList<Cell> way) {
        int row = vertex.row;
        int column = vertex.column;

        // Добавляем в список ячеек для данного пути ячейку, соответствущую текущей вершине
        way.add(field.getCell(row, column));

        // Если текущая вершина является конечной
        if(row == height - 1 && column == width - 1) {
            ways.add(way);
            return;
        }

        for(int i = 0; i < vertex.children.size(); i++) {
            createWaysOnBaseOfVertices(vertex.children.get(i), (ArrayList<Cell>) way.clone());
            // Из-за way.clone() на каждой ячейке каждого пути в памяти создаётся ArrayList.
            // Таким образом, на промежуточных ячейках путей генерируется много мусора, но,
            // так как ссылки на эти ArrayList'ы пропадают из памяти после завершения
            // рекурсивного выполнения createWaysOnBaseOfVertices()
            // (исключения составляют лишь ссылки, которые соответствую финальной ячейке),
            // то можно этим мусором пренебречь.
            // Тогда можно считать, что в памяти остаются только такие ArrayList'ы, что
            // каждый из них представляет собой последовательность ячеек для конкретного пути.
            // В таком случае затраты по памяти можно оценить как O(M),
            // где M - суммарная длина всех путей
        }
    }
}
