package main.service;

import java.util.List;

public class DetailsFunctions {
    static public <T> Long findInRepository(List<T> repository, T element) {
        for (Integer i = 0; i < repository.size(); i++) {
            if (repository.get(i).equals(element)) {
                Long index = i.longValue();
                return index;
            }
        }
        return null;
    }
}
