package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.VillainSchemeNeed;

// INTERFACE 
public interface VillainDAO {

    VillainSchemeNeed[] getSchemes() throws IOException;

    VillainSchemeNeed[] findSchemes(String containsScheme) throws IOException;

    VillainSchemeNeed getScheme(int id) throws IOException;

    VillainSchemeNeed createScheme(VillainSchemeNeed scheme) throws IOException;

    VillainSchemeNeed updateScheme(VillainSchemeNeed scheme) throws IOException;

    VillainSchemeNeed[] findSchemesByTitle(String title) throws IOException;

    VillainSchemeNeed[] findSchemesByName(String name) throws IOException;

    boolean deleteScheme(int id) throws IOException;
}