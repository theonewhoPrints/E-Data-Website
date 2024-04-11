package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Scheme} class.
 * 
 * This class provides test cases for the constructor, getters, setters, and other methods of the {@link Scheme} class,
 * ensuring that each component functions as expected.
 * 
 * @author Jacky Chan
 */
public class SchemeTest {
    /**
     * Tests the constructor of the {@link Scheme} class.
     * 
     * Verifies that the constructed {@link Scheme} object has the expected name and title values.
     */
    @Test
    public void testConstructor() {
        Scheme scheme = new Scheme(100,"Faye Valentine", "Asteroid Blues", 36000);
        assertEquals("Faye Valentine", scheme.getName());
        assertEquals("Asteroid Blues", scheme.getTitle());
    }

    /**
     * Tests the getters and setters of the {@link Scheme} class.
     * 
     * Checks that the name and title properties can be modified and retrieved correctly.
     */
    @Test
    public void testGettersAndSetters() {
        Scheme scheme = new Scheme(100, "Placeholder", "Placeholder", 34000);
        scheme.setName("Jet Black");
        scheme.setTitle("Bounty Hunter");
        assertEquals("Jet Black", scheme.getName());
        assertEquals("Bounty Hunter", scheme.getTitle());
    }

    /**
     * Tests the {@link Scheme#toString()} method.
     * 
     * Ensures that the {@link Scheme#toString()} method returns a string representation of the {@link Scheme}
     * object that matches the expected format.
     */
    @Test
    public void testToString() {
        Scheme scheme = new Scheme(100,"Spike Spiegel", "See You Space Cowboy", 32000);
        String expectedString = "Scheme [id=100, name=Spike Spiegel, title=See You Space Cowboy, fundgoal= 32000]";
        assertEquals(expectedString, scheme.toString());
    }

    /**
     * Tests setting the fund goal in the {@link Scheme} class.
     * 
     * Verifies that the fund goal value can be set and retrieved correctly.
     */
    @Test
    public void testSetFundgoal() {
        Scheme scheme = new Scheme(101, "Samuel L Jackson", "Wheres my villain", 5000);
        scheme.setFundgoal(25000); 
        assertEquals(25000, scheme.getfundgoal(), "The fundgoal should be updated to 25000");
    }

    /**
     * Tests the default value of addedToCart in the {@link Scheme} class.
     * 
     * Verifies that a new {@link Scheme} object has 'addedToCart' defaulted to false.
     */
    @Test
    public void testIsAddedToCartDefault() {
        Scheme scheme = new Scheme(102, "Eugenius", "Just flew in", 0);
        assertFalse(scheme.isAddedToCart(), "By default, addedToCart should be false");
    }

    /**
     * Tests setting the addedToCart property of the {@link Scheme} class.
     * 
     * Checks that the addedToCart property can be set to true and then retrieved correctly.
     */
    @Test
    public void testSetAddedToCart() {
        Scheme scheme = new Scheme(103, "Omar", "Syndicate Leader", 100000);
        scheme.setAddedToCart(true); 
        assertTrue(scheme.isAddedToCart(), "After setting addedToCart to true, isAddedToCart should return true");
    }
}