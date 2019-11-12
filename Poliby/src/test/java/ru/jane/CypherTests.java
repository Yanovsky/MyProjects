package ru.jane;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CypherTests {
    private Cypher cypher = new Cypher();

    @Test
    public void testAlgorithm1() {
        String input = "Яновская Евгения Александровна";
        String output = cypher.encrypt1(input);
        Assertions.assertEquals("ДУФЗЧПЕД ЛЗИЛУОД ЕРЛПЧЕУКЧФЗУЕ", output);
    }

    @Test
    public void testAlgorithm2() {
        String input = "Яновская Евгения Александровна";
        String output = cypher.encrypt2(input);
        Assertions.assertEquals("ПОЧЧПВИЧ ЛГУКАМШ АЧАЫРНАЗТЛРВВ", output);
    }
}
