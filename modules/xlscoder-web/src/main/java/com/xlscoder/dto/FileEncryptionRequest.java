package com.xlscoder.dto;

import com.xlscoder.model.Key;

public class FileEncryptionRequest {
    private Key key;
    private String str;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
