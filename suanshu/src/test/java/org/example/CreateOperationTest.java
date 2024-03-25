package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.example.CreateOperation.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateOperationTest {

    @Test
    void create() {
        for (int i = 0; i < 10; i++) {
            int r =15;
            System.out.println("test"+(i)+":");
            Random random = new Random();
            char []operater=Operator(r);
            System.out.println(operater);
            int []decide=Decide(operater);
            System.out.println(Arrays.toString(decide));
            int []calNumber=CalNumber(decide,r);
            System.out.println(Arrays.toString(calNumber));
            char[]e = Create(decide,operater,calNumber);
            System.out.print(e);
            System.out.println(" = ");
            System.out.println();
        }
    }

}