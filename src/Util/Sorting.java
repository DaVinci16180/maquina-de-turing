package Util;

import java.util.ArrayList;
import java.util.List;

import Entity.Estado;
import Structure.Lista;

public class Sorting {


    public Lista<Estado> bubbleSort(Lista<Estado> array) {
        List<Estado> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.get(i));
        }
        Lista<Estado> lista = new Lista<>();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getNome().compareTo(list.get(j + 1).getNome()) > 0) {
                    Estado temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }

        for(int i = 0; i < list.size(); i++) {
            lista.add(list.get(i));
        }
        return lista;
    }

    

}
