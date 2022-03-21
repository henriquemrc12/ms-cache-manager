package com.open.mscachemanager.adpters.presentation.validation;

import com.open.mscachemanager.adpters.presentation.request.CacheCreateRequest;
import com.open.mscachemanager.adpters.presentation.request.CacheDeleteRequest;
import com.open.mscachemanager.adpters.presentation.request.CacheUpdateRequest;
import com.open.mscachemanager.domain.exception.CException;

import java.util.ArrayList;
import java.util.List;

public final  class CacheValidation {

    public static void validate(CacheCreateRequest request) {
        try {

            List<String> errorList = new ArrayList<>();

            if(request.getContent() == null) {
                String error = "'content' can not be null";
                errorList.add(error);
            }

            if(errorList.size() > 0)
                throw new CException(errorList);

            return;
        } catch (CException e) {
            throw e;
        }
    }

    public static void validate(CacheUpdateRequest request) {
        try {

            List<String> errorList = new ArrayList<>();

            if(request.getContent() == null) {
                String error = "'content' can not be null";
                errorList.add(error);
            }

            if(request.getKey() == null) {
                String error = "'key' can not be null";
                errorList.add(error);
            }

            if(errorList.size() > 0)
                throw new CException(errorList);

            return;
        } catch (CException e) {
            throw e;
        }
    }


    public static void validate(CacheDeleteRequest request) {
        try {

            List<String> errorList = new ArrayList<>();

            if(request.getKey() == null) {
                String error = "'key' can not be null";
                errorList.add(error);
            }

            if(errorList.size() > 0)
                throw new CException(errorList);

            return;
        } catch (CException e) {
            throw e;
        }
    }

    public static boolean isCacheField(String field) {
        switch (field) {
            case "key":
                return true;
            case "content":
                return true;
            case "ttl":
                return true;
            case "current_ttl":
                return true;
            default:
                return false;
        }
    }
}
