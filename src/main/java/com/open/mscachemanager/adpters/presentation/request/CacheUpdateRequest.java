package com.open.mscachemanager.adpters.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheUpdateRequest {

    private String key;

    private HashMap<?, ?> content;

    private Long ttl;

    private Boolean isAsync = Boolean.FALSE;
}
