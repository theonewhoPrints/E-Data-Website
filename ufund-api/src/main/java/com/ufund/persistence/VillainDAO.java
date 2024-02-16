package com.ufund.persistence;

import java.io.IOException;

//where this come from ? import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import com.ufund.model.VillainSchemeNeed;

// INTERFACE 
public interface VillainDAO {

    VillainSchemeNeed[] getSchemes() throws IOException;

    VillainSchemeNeed[] findSchemes(String containsScheme) throws IOException;

    VillainSchemeNeed getScheme(int id) throws IOException;

    VillainSchemeNeed createScheme(VillainSchemeNeed scheme) throws IOException;

    VillainSchemeNeed updateScheme(VillainSchemeNeed scheme) throws IOException;

    VillainSchemeNeed[] findSchemesByTitle(String title) throws IOException;

    boolean deleteScheme(int id) throws IOException;
}