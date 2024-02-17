package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Scheme;

// INTERFACE 
public interface VillainDAO {

    Scheme[] getSchemes() throws IOException;

    Scheme[] findSchemes(String containsScheme) throws IOException;

    Scheme getScheme(int id) throws IOException;

    Scheme createScheme(Scheme scheme) throws IOException;

    Scheme updateScheme(Scheme scheme) throws IOException;

    Scheme[] findSchemesByTitle(String title) throws IOException;

    Scheme[] findSchemesByName(String name) throws IOException;

    boolean deleteScheme(int id) throws IOException;
}