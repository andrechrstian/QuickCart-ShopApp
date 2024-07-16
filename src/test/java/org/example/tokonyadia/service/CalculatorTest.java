package org.example.tokonyadia.service;

import org.example.tokonyadia.service.impl.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {

    @Test
    @DisplayName("add two numbers")
    void sumTest() {
        //TODO : Scenario Test sum 5,7
        int actual = Calculator.sum(5, 7);
        int expected = 12;
        Assertions.assertEquals(expected,actual);

        //TODO : Scenario Test sum 2,3
        Assertions.assertEquals(6,Calculator.sum(4,2), "Scenario sum(4,2) should be equal to 6");

    }

    @DisplayName("test with CSV")
    @CsvSource({
            "0,1,1",
            "1,2,3",
            "-5,-5,-10"
    })
    @ParameterizedTest(name =  "{0} + {1} = {2}")
    void sumCSV(int a, int b, int expected){
        Assertions.assertEquals(expected,Calculator.sum(a, b), () -> a + "+" + b + "should be equals - " + expected);
    }

    @Test
    void mulTest(){

    }

}
