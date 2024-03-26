package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SchemeTest {
    @Test
    public void testConstructor() {
        Scheme scheme = new Scheme(100,"Faye Valentine", "Asteroid Blues", 36000);
        assertEquals("Faye Valentine", scheme.getName());
        assertEquals("Asteroid Blues", scheme.getTitle());
    }

    @Test
    public void testGettersAndSetters() {
        Scheme scheme = new Scheme(100, "Placeholder", "Placeholder", 34000);
        scheme.setName("Jet Black");
        scheme.setTitle("Bounty Hunter");
        assertEquals("Jet Black", scheme.getName());
        assertEquals("Bounty Hunter", scheme.getTitle());
    }

    @Test
    public void testToString() {
        Scheme scheme = new Scheme(100,"Spike Spiegel", "See You Space Cowboy", 32000);
        String expectedString = "Scheme [id=100, name=Spike Spiegel, title=See You Space Cowboy, fundgoal= 32000]";
        assertEquals(expectedString, scheme.toString());
    }
}