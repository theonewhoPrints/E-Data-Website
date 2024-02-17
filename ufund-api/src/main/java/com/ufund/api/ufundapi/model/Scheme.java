package com.ufund.api.ufundapi.model;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Scheme {
    private static final Logger LOG = Logger.getLogger(Scheme.class.getName());
    
    static final String STRING_FORMAT = "Scheme [id=%d, name=%s, title=%s]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("title") private String title;

    public Scheme(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("title") String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setName() {
        this.name = name;
    }

    public void setTitle() {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, title);
    }

}
