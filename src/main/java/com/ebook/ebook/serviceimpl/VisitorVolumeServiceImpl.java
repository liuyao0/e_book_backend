package com.ebook.ebook.serviceimpl;

import com.ebook.ebook.dao.MetaDao;
import com.ebook.ebook.service.VisitorVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorVolumeServiceImpl implements VisitorVolumeService {
    @Autowired
    public MetaDao metaDao;

    private final Object lock=new Object();


    @Override
    public Integer visit(){
        Integer visitorVolume;
        synchronized (lock)
        {
            visitorVolume=metaDao.getMetaValueByMetaKey("visitorVolume")+1;
            metaDao.setMetaValueByMetaKey("visitorVolume",visitorVolume);
        }
        return visitorVolume;
    }

    @Override
    public Integer getVisitorVolume() {
        Integer visitorVolume;
        synchronized (lock)
        {
            visitorVolume=metaDao.getMetaValueByMetaKey("visitorVolume");
        }
        return visitorVolume;
    }
}
