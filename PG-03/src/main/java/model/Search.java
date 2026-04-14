package model;

import java.util.ArrayList;
import java.util.List;

public class Search {


    public static List<String> steps = new ArrayList<>();

    /**
     * int value = 10:
     * int pos = binarySearch(sortedArray, value, 0, sortedArray.length - 1);
     * @param sortedArray
     * @param value
     * @param low
     * @param high
     * @return
     */

    public static int binarySearch(int[] sortedArray, int value, int low, int high){

        if (low>high){
            steps.add("Valor no encontrado: ");
            return -1;
        }
        int mid = low + (high - low) / 2;

        steps.add("Rango ["+ low + ", " + high + "], -->mid= " + mid
                +"(sortedArray[mid] =" + sortedArray[mid] + "]");

        if (value == sortedArray[mid]){
            steps.add("Valor encontrado en la posición: " + mid);
            return mid;

        } else if (value < sortedArray[mid]){
            return binarySearch(sortedArray, value, low, mid - 1);
        } else {
            return binarySearch(sortedArray, value, mid + 1, high);
        }
    }

    public static class MinMax{
        public int min;
        public int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin(){return min;}
        public int getMax(){return max;}

    }
    public static MinMax findMinMax(int[] arr, int low, int high){
        //Caso bae: solo hay un elemento
        if(low == high){
            steps.add("caso base un elemento: min==" + arr[low] + ", max==" + arr[high]);
            return new MinMax(arr[low], arr[high]);
        }
        //Caso base: dos elementos
        if(high == low +1){
            steps.add("caso base 2 elementos: min==" + Math.min(arr[low], arr[high]) + ", max==" + Math.max(arr[low], arr[high]));
            return new MinMax(Math.min(arr[low], arr[high]),Math.max(arr[low], arr[high]));
        }

        //En otro caso, debemos dividir el arreglo en dos mitades
        int mid = (low + high) / 2;
        steps.add("Rango {"+low+", "+high+"]  --> mid= "+mid);
        steps.add("leftResult: Rango {"+low+", "+high+"]");
        MinMax leftResult = findMinMax(arr, low, mid);
        steps.add("rightResult: Rango {"+mid +1+", "+high+"]");
        MinMax rightResult = findMinMax(arr, mid + 1, high);

        //Podemos combinar los resultados
        int min = Math.min(leftResult.getMin(), rightResult.getMin());
        steps.add("valor de la variable min == " + min);
        int max = Math.max(leftResult.getMax(), rightResult.getMax());
        steps.add("valor de la variable max == "+ max);

        return new MinMax(min, max);
    }

}
