package com.karashok.library.module_db.util;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.collection.SimpleArrayMap;

import com.karashok.library.module_db.db.DaoMaster;
import com.karashok.library.module_db.db.DaoSession;

import org.greenrobot.greendao.AbstractDao;

import java.util.Collection;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name GreenDBUtils @see GreenDaoUtils
 * DESCRIPTION 数据库管理类
 * @date 2018/06/16/下午6:18
 */
@Deprecated
public class GreenDBUtils {

    private static final String DEFAULT_DATABASE_TABLE = "iCourtDatabase";
    private static SimpleArrayMap<String, GreenDBUtils> DB_UTILS_MAP = new SimpleArrayMap<>();
    private DaoSession mDaoSession;
    private AbstractDao mAbstractDao;

    private GreenDBUtils(String tabName, Application application) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(application, tabName);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }

    public static GreenDBUtils getDefaultInstance(Application application) {
        return getInstance("", application);
    }

    public static GreenDBUtils getInstance(String tabName, Application application) {
        if (tabName != null && tabName.isEmpty()) {
            tabName = DEFAULT_DATABASE_TABLE;
        }
        GreenDBUtils greenDBUtils = DB_UTILS_MAP.get(tabName);
        if (greenDBUtils == null) {
            greenDBUtils = new GreenDBUtils(tabName, application);
            DB_UTILS_MAP.put(tabName, greenDBUtils);
        }
        return greenDBUtils;
    }

    private void isDaoNull() {
        if (mAbstractDao == null) {
            new NullPointerException("Please init GreenDao first !");
        }
    }

    /**
     * 获取数据库Item的数量
     *
     * @return
     */
    public Long count() {
        isDaoNull();
        return mAbstractDao.count();
    }

    /**
     * 插入一条数据
     *
     * @param dbEntity
     */
    public <T> void inster(T dbEntity) {
        isDaoNull();
        mAbstractDao.insert(dbEntity);
    }

    /**
     * 插入一条数据
     *
     * @param dbEntity
     */
    public <T> void insertOrReplace(T dbEntity) {
        isDaoNull();
        mAbstractDao.insertOrReplace(dbEntity);
    }

    /**
     * 插入多条数据
     *
     * @param entities
     */
    public <T> void inster(List<T> entities) {
        isDaoNull();
        mAbstractDao.insertInTx(entities);
    }

    /**
     * 删除单条数据
     *
     * @param entity
     */
    public <T> void delete(T entity) {
        isDaoNull();
        mAbstractDao.delete(entity);
    }

    /**
     * 删除特定ID的数据
     *
     * @param id
     */
    public void deleteById(long id) {
        isDaoNull();
        mAbstractDao.deleteByKey(id);
    }

    /**
     * 删除多条数据
     *
     * @param entities
     */
    public <T> void deleteList(List<T> entities) {
        isDaoNull();
        mAbstractDao.deleteInTx(entities);
    }

    /**
     * 全部删除
     */
    public void deleteAll() {
        isDaoNull();
        mAbstractDao.deleteAll();
    }

    /**
     * 更新单条数据
     *
     * @param entity
     */
    public <T> void uploadData(final T entity) {
        isDaoNull();
        mAbstractDao.update(entity);
    }

    /**
     * 更新多条数据
     *
     * @param entities
     */
    public <T> void uploadListData(Collection<T> entities) {
        isDaoNull();
        mAbstractDao.updateInTx(entities);
    }

    /**
     * 查询特定ID的数据
     *
     * @param id
     * @return
     */
    public <T> T query(long id) {
        isDaoNull();
        return (T) mAbstractDao.load(id);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public <T> List<T> queryAll() {
        isDaoNull();
        return mAbstractDao.loadAll();
    }

    /**
     * 查询特定Entity的数据
     *
     * @param entityId
     * @return
     */
//    public <T> T queryWhere(String entityId) {
//        QueryBuilder<T> queryBuilder = mAbstractDao.queryBuilder();
//        T dbEntity = queryBuilder.where(ContractDBEntityDao.Properties.EntityId.gt(entityId)).build().unique();
//        return dbEntity;
//    }

}
