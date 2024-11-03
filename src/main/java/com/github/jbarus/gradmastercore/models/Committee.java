package com.github.jbarus.gradmastercore.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Committee {
    private UUID uuid;
    private String name;

    public Committee(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }
}