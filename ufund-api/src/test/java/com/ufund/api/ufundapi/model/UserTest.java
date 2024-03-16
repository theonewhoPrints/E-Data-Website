package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testConstructor() {
        User spike = new User(1, "Spike Spiegel", "ROLE_HELPER");
        assertEquals(1, spike.getId());
        assertEquals("Spike Spiegel", spike.getName());
        assertEquals("ROLE_HELPER", spike.getRole());
    }

    @Test
    public void testGetters() {
        User spike = new User(1, "Faye Valentine", "ROLE_HELPER");
        assertEquals(1, spike.getId());
        assertEquals("Faye Valentine", spike.getName());
        assertEquals("ROLE_HELPER", spike.getRole());
    }

    @Test
    public void testSetters() {
        User spike = new User(1, "PLACEHOLDER", "ROLE_HELPER");
        spike.setName("Vicious");
        spike.setRole("ROLE_VILLAIN");
        assertEquals("Vicious", spike.getName());
        assertEquals("ROLE_VILLAIN", spike.getRole());
    }

    @Test
    public void testToString() {
        User spike = new User(1, "Jet Black", "ROLE_HELPER");
        String expected = "User [id=1, name=Jet Black, role=ROLE_HELPER]";
        assertEquals(expected, spike.toString());
    }
}
