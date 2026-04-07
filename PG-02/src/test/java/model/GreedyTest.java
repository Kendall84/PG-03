package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GreedyTest {

    @Test void testCoinChanged(){
        List<String> list = new ArrayList<>();
        list = Greedy.coinChange(787);
        for (String valor : list){
            System.out.println(valor);
        }
    }

}