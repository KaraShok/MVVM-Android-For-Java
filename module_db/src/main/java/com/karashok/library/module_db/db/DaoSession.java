package com.karashok.library.module_db.db;


import com.karashok.library.module_db.data.SampleDBEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sampleDBEntityDaoConfig;

    private final SampleDBEntityDao sampleDBEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sampleDBEntityDaoConfig = daoConfigMap.get(SampleDBEntityDao.class).clone();
        sampleDBEntityDaoConfig.initIdentityScope(type);

        sampleDBEntityDao = new SampleDBEntityDao(sampleDBEntityDaoConfig, this);

        registerDao(SampleDBEntity.class, sampleDBEntityDao);
    }
    
    public void clear() {
        sampleDBEntityDaoConfig.clearIdentityScope();
    }

    public SampleDBEntityDao getSampleDBEntityDao() {
        return sampleDBEntityDao;
    }

}
