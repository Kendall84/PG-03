package model;

import java.util.ArrayList;
import java.util.List;

public class Greedy {

    private static int[] monedas = {500, 100, 50, 25, 10, 5, 1};

    public static List<String> coinChange(int monto) {
        List<String> usadas = new ArrayList<>();
        if (monto <= 0) {
            return usadas;  // Caso edge: monto cero o negativo, retornar lista vacía
        }
        for (int moneda : monedas) {
            while (monto >= moneda) {
                usadas.add(String.valueOf(moneda));
                monto -= moneda;  // Restar la moneda al monto
            }
        }
        return usadas;  // Lista de monedas usadas
    }


    public static class KnapsackResult{
        public final Item[] sortedItems;//lista
        public final List<Item> selected;
        private final double maxValue;
        private final int maxWeight;
        private final int capacity;
        public final long nanoTime;

        public KnapsackResult(Item[] sortedItems, List<Item> selected, double maxValue, int maxWeight, int capacity, long nanoTime) {
            this.sortedItems = sortedItems;
            this.selected = selected;
            this.maxValue = maxValue;
            this.maxWeight = maxWeight;
            this.capacity = capacity;
            this.nanoTime = nanoTime;
        }
    }

    public static KnapsackResult knapsackResult(Item[] items, int capacity){
        long t1= System.nanoTime();

        //copiar y ordenar el arreglo por ratio v/w descendente
        Item[] sortedItems = items.clone();
        //Arrays.sort(sortedItems, (a,b) -> Double.compare(a.getRadio(),b.getRadio()));

        bubbleSort(sortedItems);
        List<Item> selected = new ArrayList<>();
        double totalValue = 0;
        int totalWeight = 0;
        int remainingCapacity = capacity;


        for (Item item : sortedItems){
            if (remainingCapacity <= 0) break;
            if (item.getWeight() <=remainingCapacity){

                selected.add(new Item(item.getName(), item.getWeight(), item.getValue()));
                totalValue += item.getValue();
                totalWeight += item.getWeight();
                remainingCapacity -= item.getWeight();
            }
        }

        long nanos = System.nanoTime() - t1;
        return new KnapsackResult(sortedItems,selected,totalValue,totalWeight,capacity,nanos);
    }


        // Método para ordenar usando el algoritmo de burbuja
        public static void bubbleSort(Item[] arr) {
            int n = arr.length;
            boolean swapped;

            // Recorremos el arreglo varias veces
            for (int i = 0; i < n - 1; i++) {
                swapped = false;
                for (int j = 0; j < n - i - 1; j++) {
                    // Si el elemento actual es mayor que el siguiente, intercambiamos
                    if (arr[j].getRadio() < arr[j + 1].getRadio()) {
                        Item temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        swapped = true;
                    }
                }
                // Si no hubo intercambios, el arreglo ya está ordenado
                if (!swapped) break;
            }
        }








    // Método opcional para mostrar detalles (cuántas de cada moneda). Elimínalo si no lo necesitas.
    public static void coinChangeDetailed(int monto) {
        if (monto <= 0) {
            System.out.println("Monto inválido");
            return;
        }
        for (int moneda : monedas) {
            int count = 0;
            while (monto >= moneda) {
                count++;
                monto -= moneda;
            }
            if (count > 0) {
                System.out.println(count + " x " + moneda);
            }
        }
    }
}
