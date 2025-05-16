package ru.comfortsoft.task.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinFinderService {

    public Integer findNthMinimum(List<Integer> values, int N) {
        if (N > 0 && N <= values.size()) {
            return quickSelect(values, 0, values.size() - 1, N - 1);
        }
        throw new IllegalArgumentException("N должно быть положительным и меньше или равно числу элементов в столбце");
    }

    private Integer quickSelect(List<Integer> values, int left, int right, int n) {
        int pivotIndex = partition(values, left, right);

        if (n == pivotIndex) {
            return values.get(n);
        } else if (n < pivotIndex) {
            return quickSelect(values, left, pivotIndex - 1, n);
        } else {
            return quickSelect(values, pivotIndex + 1, right, n);
        }
    }

    private int partition(List<Integer> values, int left, int right) {
        Integer pivot = values.get(right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (values.get(i) < pivot) {
                swap(values, storeIndex, i);
                storeIndex++;
            }
        }

        swap(values, storeIndex, right);
        return storeIndex;
    }

    private void swap(List<Integer> values, int i, int j) {
        Integer temp = values.get(i);
        values.set(i, values.get(j));
        values.set(j, temp);
    }
}
