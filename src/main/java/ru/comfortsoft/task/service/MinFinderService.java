package ru.comfortsoft.task.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MinFinderService {

    public Integer findNthMinimum(Set<Integer> uniqueValues, int N) {
        int NthMin = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int currentMin = Integer.MAX_VALUE;
            for (int num : uniqueValues) {
                if (num > NthMin && num < currentMin) {
                    currentMin = num;
                }
            }
            NthMin = currentMin;
        }
        return NthMin;
    }
}
