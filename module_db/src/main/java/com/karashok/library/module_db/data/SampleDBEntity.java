package com.karashok.library.module_db.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SampleDBEntity
 * DESCRIPTION 数据库测试Entity
 * @date 2018/06/16/下午6:18
 */
@Entity
public class SampleDBEntity {

    @Id(autoincrement = true)
    private long id;

    @Unique
    private String entityId;

    private String entityJson;

    @Generated(hash = 1099150035)
    public SampleDBEntity(long id, String entityId, String entityJson) {
        this.id = id;
        this.entityId = entityId;
        this.entityJson = entityJson;
    }

    @Generated(hash = 1957180818)
    public SampleDBEntity() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityJson() {
        return this.entityJson;
    }

    public void setEntityJson(String entityJson) {
        this.entityJson = entityJson;
    }
}
