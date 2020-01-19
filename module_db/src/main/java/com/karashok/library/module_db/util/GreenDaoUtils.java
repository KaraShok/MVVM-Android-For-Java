package com.karashok.library.module_db.util;

import android.app.Application;

import com.karashok.library.module_db.db.DaoMaster;
import com.karashok.library.module_db.db.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GreenDaoUtils {


    private static final String DEFAULT_DATABASE_NAME = "iCourtDatabase";


    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private AbstractDao mAbstractDao;
    private static GreenDaoUtils mDbUtils;
    private DaoMaster.DevOpenHelper mHelper;
    private GreenDaoCallBack mCallBack;

    private GreenDaoUtils(Application application, String dbName, String passWord) {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(application, dbName);
        }
        if (passWord == null || passWord.isEmpty()) {
            mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        } else {
            mDaoMaster = new DaoMaster(mHelper.getEncryptedReadableDb(passWord));
        }
        mDaoSession = mDaoMaster.newSession();

    }

    public static GreenDaoUtils getInstance(Application application) {
        return getInstance(application, DEFAULT_DATABASE_NAME);
    }

    public static GreenDaoUtils getInstance(Application application, String dbName) {

        return getInstance(application, dbName, "");
    }

    public static GreenDaoUtils getInstance(Application application, String dbName, String passWord) {

        if (mDbUtils == null) {
            synchronized (GreenDaoUtils.class) {
                if (mDbUtils == null) {
                    mDbUtils = new GreenDaoUtils(application, dbName, passWord);
                }
            }
        }

        return mDbUtils;
    }


    /**
     * 获取 QueryBuilder，构建查询条件
     *
     * @param claz
     * @param <T>
     * @return
     */
    public <T> QueryBuilder<T> queryBuilder(Class<T> claz) {
        return mDaoSession.queryBuilder(claz);
    }

    /**
     * 获取数据库Item的数量
     *
     * @return
     */
    public <T> Long count(Class<T> entityClaz) {
        setCurrentDao(entityClaz);
        return mAbstractDao.count();
    }

    /**
     * 插入一条数据
     *
     * @param dbEntity
     */
    public <T> void insert(T dbEntity) {
        setCurrentDao(dbEntity.getClass());
        mAbstractDao.insert(dbEntity);
    }

    /**
     * 插入一条数据
     *
     * @param dbEntity
     */
    public <T> void insertOrReplace(T dbEntity) {
        setCurrentDao(dbEntity.getClass());
        mAbstractDao.insertOrReplace(dbEntity);
    }

    /**
     * 插入多条数据
     *
     * @param entities
     */
    public <T> void insertTx(List<T> entities) {

        if (entities == null || entities.size() < 1) {
            return;
        }
        setCurrentDaoOfList(entities);
        mAbstractDao.insertInTx(entities);
    }

    public <T> void insertOrReplaceInTx(List<T> entities) {
        if (entities == null || entities.size() < 1) {
            return;
        }
        setCurrentDaoOfList(entities);
        mAbstractDao.insertOrReplaceInTx(entities);
    }

    /**
     * 删除单条数据
     *
     * @param entity
     */
    public <T> void delete(T entity) {
        setCurrentDao(entity.getClass());
        mAbstractDao.delete(entity);
    }

    /**
     * 删除特定ID的数据
     *
     * @param id
     */
    public <T> void deleteById(Class<T> entityClaz, long id) {
        setCurrentDao(entityClaz);
        mAbstractDao.deleteByKey(id);
    }

    /**
     * 删除多条数据
     *
     * @param entities
     */
    public <T> void deleteList(List<T> entities) {
        setCurrentDaoOfList(entities);
        mAbstractDao.deleteInTx(entities);
    }

    /**
     * 查询所有的同一类型的实体类，并全部删除
     *
     * @param entityClaz 实体类
     * @param <T>
     */
    public <T> void deleteList(Class<T> entityClaz) {
        List<T> queryAll = queryAll(entityClaz);
        deleteList(queryAll);
    }

    /**
     * 全部删除
     */
    public <T> void deleteAll() {
        mAbstractDao.deleteAll();
    }

    /**
     * 更新单条数据
     *
     * @param entity
     */
    public <T> void updateData(final T entity) {
        setCurrentDao(entity.getClass());
        mAbstractDao.update(entity);
    }

    /**
     * 更新多条数据
     *
     * @param entities
     */
    public <T> void updateListData(Collection<T> entities) {
        setCurrentDaoOfList(entities);
        mAbstractDao.updateInTx(entities);
    }

    /**
     * 查询特定ID的数据
     *
     * @param id
     * @return
     */
    public <T> T queryById(Class<T> claz, long id) {
        setCurrentDao(claz);
        return (T) mAbstractDao.load(id);
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public<T> List<T> queryAll(Class<T> claz) {
        setCurrentDao(claz);
        return mAbstractDao.loadAll();
    }

    public <T> List<T> queryAll(Class<T> claz, WhereCondition whereCondition) {
        setCurrentDao(claz);
        return mDaoSession.queryBuilder(claz)
                .where(whereCondition)
                .list();
    }

    public <T> List<T> queryAll(Class<T> claz, QueryBuilder<T> queryBuilder) {
        setCurrentDao(claz);
        return mDaoSession.queryBuilder(claz).list();
    }

    public <T> List<T> queryAll(Class<T> claz, Query<T> query) {
        setCurrentDao(claz);
        return query.list();
    }


    /**
     * 原生查询
     *
     * @param claz
     * @param whereString
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> queryRaw(Class<T> claz, String whereString, String[] params) {
        setCurrentDao(claz);
        return mAbstractDao.queryRaw(whereString, params);
    }

    /**
     * 异步操作的回调设置
     *
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> GreenDaoUtils setDbCallBack(GreenDaoCallBack<T> callBack) {
        mCallBack = callBack;
        return this;
    }

    /**
     * 条件查询数据
     *
     * @param cls
     * @return
     */
    public <T> void queryAsync(Class<T> cls, WhereCondition whereCondition) {
        setCurrentDao(cls);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    List<T> list = new ArrayList<>();
                    list.add(((T) operation.getResult()));
                    mCallBack.onSuccess(list);
                } else if (operation.isFailed()) {
                    mCallBack.onFailed();
                }
            }
        });
        Query query = mDaoSession.queryBuilder(cls).where(whereCondition).build();
        asyncSession.queryUnique(query);
    }

    /**
     * 异步条件查询，通过使用 QueryBuilder 构造 Query
     *
     * @param claz
     * @param builder
     * @param <T>
     */
    public <T> void queryAsyncAll(Class<T> claz, QueryBuilder<T> builder) {
        setCurrentDao(claz);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompleted() && mCallBack != null) {
                    List<T> result = (List<T>) operation.getResult();
                    mCallBack.onSuccess(result);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onFailed();
                }
            }
        });
        if (builder == null || builder.build() == null) {
            asyncSession.loadAll(claz);
        } else {
            asyncSession.queryList(builder.build());
        }
    }

    /**
     * 删除
     */
    public <T> void deleteAsyncSingle(T entry) {
        setCurrentDao(entry.getClass());
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.delete(entry);
    }

    /**
     * 批量删除
     */
    public <T> void deleteAsyncBatch(Class<T> cls, final List<T> list) {
        setCurrentDao(cls);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.deleteInTx(cls, list);
    }


    /**
     * 根据Id批量删除
     */
    public <T> void deleteByIdBatch(Class<T> claz, List<Long> longList) {
        setCurrentDao(claz);
        mAbstractDao.deleteByKeyInTx(longList);
    }

    /**
     * 删除所有数据
     */
    public <T> void deleteAsyncAll(Class<T> cls) {
        setCurrentDao(cls);
        final AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.deleteAll(cls);
    }

    /**
     * 插入一条数据
     */
    public <T> void insertAsyncSingle(final T entity) {
        setCurrentDao(entity.getClass());
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.runInTx(new Runnable() {
            @Override
            public void run() {
                mDaoSession.insert(entity);
            }
        });
    }

    /**
     * 批量插入
     */
    public <T> void insertAsyncBatch(final Class<T> cls, final List<T> userList) {
        setCurrentDao(cls);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.runInTx(new Runnable() {
            @Override
            public void run() {
                for (T object : userList) {
                    mDaoSession.insertOrReplace(object);
                }
            }
        });
    }

    /**
     * 更新一个数据
     */
    public <T> void updateAsyncSingle(Class<T> cls, T entry) {
        setCurrentDao(cls);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.update(entry);
    }

    /**
     * 批量更新数据
     */
    public <T> void updateAsyncBatch(final Class<T> cls, final List<T> tList) {
        setCurrentDao(cls);
        AsyncSession asyncSession = mDaoSession.startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully() && mCallBack != null) {
                    mCallBack.onNotification(true);
                } else if (operation.isFailed() && mCallBack != null) {
                    mCallBack.onNotification(false);
                }
            }
        });
        asyncSession.updateInTx(cls, tList);
    }

    /**
     * 关闭DaoSession
     */
    private void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    /**
     * 关闭Helper
     */
    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    /**
     * 关闭所有的操作
     */
    public void closeConnection() {
        closeDaoSession();
        closeHelper();
    }

    private <T> void setCurrentDao(Class<T> entityClass) {
        if (mHelper == null) {
            throw new NullPointerException("You need to init mHelper first!");
        }
        mAbstractDao = mDaoSession.getDao(entityClass);
    }

    private <T> void setCurrentDaoOfList(Collection<T> entities) {
        if (entities != null && entities.size() > 0) {
            Iterator<T> iterator = entities.iterator();
            T next = iterator.next();
            setCurrentDao(next.getClass());
        }
    }

}
