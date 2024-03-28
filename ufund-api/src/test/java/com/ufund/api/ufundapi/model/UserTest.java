package com.ufund.api.ufundapi.model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testConstructor() {
        User spike = new User(1, "Spike Spiegel", "ROLE_HELPER", "", "", List.of("Bounty Hunter"));
        assertEquals(1, spike.getId());
        assertEquals("Spike Spiegel", spike.getName());
        assertEquals("ROLE_HELPER", spike.getRole());
        assertEquals("", spike.getImgUrl());    
        assertEquals("", spike.getDescription());
        assertEquals(List.of("Bounty Hunter"), spike.getAchievements());
    }

    @Test
    public void testGetters() {
        User spike = new User(1, "Faye Valentine", "ROLE_HELPER", "", "", List.of("Bounty Hunter"));
        assertEquals(1, spike.getId());
        assertEquals("Faye Valentine", spike.getName());
        assertEquals("ROLE_HELPER", spike.getRole());
        assertEquals("", spike.getImgUrl());
        assertEquals("", spike.getDescription());
        assertEquals(List.of("Bounty Hunter"), spike.getAchievements());
    }

    @Test
    public void testSetters() {
        User spike = new User(1, "PLACEHOLDER", "ROLE_HELPER", "", "", new ArrayList<>(List.of("Bounty Hunter")));
        spike.setName("Vicious");
        spike.setRole("ROLE_VILLAIN");
        spike.setImgUrl("imgUrl");
        spike.setDescription("description");
        spike.editAchievement("Villain", 0);
        spike.addAchievement("Killer");
        assertEquals("Vicious", spike.getName());
        assertEquals("ROLE_VILLAIN", spike.getRole());
        assertEquals("imgUrl", spike.getImgUrl());
        assertEquals("description", spike.getDescription());
        assertEquals(List.of("Villain", "Killer"), spike.getAchievements());

        spike.removeAchievement(1);
        assertEquals(List.of("Villain"), spike.getAchievements());
    }

    @Test
    public void testToString() {
        User spike = new User(1, "Jet Black", "ROLE_HELPER", "", "", List.of("Bounty Hunter"));
        String expected = "User [id=1, name=Jet Black, role=ROLE_HELPER, imgUrl=, description=, achievements=[Bounty Hunter]]";
        assertEquals(expected, spike.toString());
    }
}
