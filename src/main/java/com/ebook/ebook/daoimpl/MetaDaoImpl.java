package com.ebook.ebook.daoimpl;

import com.ebook.ebook.dao.MetaDao;
import com.ebook.ebook.entity.Meta;
import com.ebook.ebook.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MetaDaoImpl implements MetaDao {
    @Autowired
    private MetaRepository metaRepository;

    @Override
    public Integer getMetaValueByMetaKey(String metaKey)
    {
        Meta meta=metaRepository.findByMetaKey(metaKey);
        return meta.getMetaValue();
    }

    @Override
    public void setMetaValueByMetaKey(String metaKey,Integer metaValue)
    {
        Meta meta=metaRepository.findByMetaKey(metaKey);
        meta.setMetaValue(metaValue);
        metaRepository.save(meta);
    }
}
