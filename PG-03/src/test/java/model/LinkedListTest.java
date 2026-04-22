package model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test
    void linkedListTest() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(20);
        linkedList.add(10);

        for (int i = 0; i < 20; i++) {
            linkedList.add(new Random().nextInt(50));
        }

        System.out.println(linkedList);

        //quiero ver la data del primer y último nodo de la lista
        System.out.println("getHead: " + linkedList.getHead().data);
        System.out.println("getTail: " + linkedList.getTail().data);

        System.out.println("addFirst(100)");
        linkedList.addFirst(100);
        System.out.println(linkedList);

        try {
            System.out.println("LinkedList size: " + linkedList.size());

            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(50);
                System.out.println(
                        linkedList.contains(value)
                                ? "value [" + value + "] exists"
                                : "value [" + value + "] does not exist"
                );
            }
            //probamos get
            for(int i = 0; i < 20; i++){
                System.out.println("get(" + i + "): " + linkedList.get(i));
            }

        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }
}