package com.open.mscachemanager.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashMap;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cache {

    private String key;

    private HashMap<?, ?> content;

    private Long ttl;

    @JsonIgnore
    private Boolean isAsync;
}
