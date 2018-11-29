package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import project.cn.edu.tongji.sse.nowfitness.model.IndiRelationModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INDI_RELATION_MODEL".
*/
public class IndiRelationModelDao extends AbstractDao<IndiRelationModel, Long> {

    public static final String TABLENAME = "INDI_RELATION_MODEL";

    /**
     * Properties of entity IndiRelationModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property StarId = new Property(1, long.class, "starId", false, "STAR_ID");
        public final static Property FollowId = new Property(2, long.class, "followId", false, "FOLLOW_ID");
    }


    public IndiRelationModelDao(DaoConfig config) {
        super(config);
    }
    
    public IndiRelationModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INDI_RELATION_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"STAR_ID\" INTEGER NOT NULL ," + // 1: starId
                "\"FOLLOW_ID\" INTEGER NOT NULL );"); // 2: followId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INDI_RELATION_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IndiRelationModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getStarId());
        stmt.bindLong(3, entity.getFollowId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IndiRelationModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getStarId());
        stmt.bindLong(3, entity.getFollowId());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public IndiRelationModel readEntity(Cursor cursor, int offset) {
        IndiRelationModel entity = new IndiRelationModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // starId
            cursor.getLong(offset + 2) // followId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IndiRelationModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStarId(cursor.getLong(offset + 1));
        entity.setFollowId(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(IndiRelationModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(IndiRelationModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(IndiRelationModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}