package com.ebook.ebook.dao;

public interface MetaDao {
    Integer getMetaValueByMetaKey(String metaKey);
    void setMetaValueByMetaKey(String metaKey,Integer metaValue);
}
