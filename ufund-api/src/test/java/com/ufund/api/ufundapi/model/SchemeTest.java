package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SchemeTest {
    @Test
    public void testConstructor() {
        Scheme scheme = new Scheme(100,"Faye Valentine", "Asteroid Blues");
        assertEquals("Faye Valentine", scheme.getName());
        assertEquals("Asteroid Blues", scheme.getTitle());
    }

    @Test
    public void testGettersAndSetters() {
        Scheme scheme = new Scheme(100, "Placeholder", "Placeholder");
        scheme.setName("Jet Black");
        scheme.setTitle("Bounty Hunter");
        assertEquals("Jet Black", scheme.getName());
        assertEquals("Bounty Hunter", scheme.getTitle());
    }

    @Test
    public void testToString() {
        Scheme scheme = new Scheme(100,"Spike Spiegel", "See You Space Cowboy");
        String expectedString = "Scheme [id=100, name=Spike Spiegel, title=See You Space Cowboy]";
        assertEquals(expectedString, scheme.toString());
    }
}