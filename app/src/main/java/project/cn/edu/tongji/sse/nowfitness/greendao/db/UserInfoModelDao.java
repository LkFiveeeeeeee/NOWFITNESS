package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO_MODEL".
*/
public class UserInfoModelDao extends AbstractDao<UserInfoModel, Long> {

    public static final String TABLENAME = "USER_INFO_MODEL";

    /**
     * Properties of entity UserInfoModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property UserName = new Property(1, String.class, "userName", false, "USER_NAME");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property Height = new Property(4, double.class, "height", false, "HEIGHT");
        public final static Property Weight = new Property(5, double.class, "weight", false, "WEIGHT");
        public final static Property Sex = new Property(6, String.class, "sex", false, "SEX");
        public final static Property Age = new Property(7, int.class, "age", false, "AGE");
        public final static Property PictureUrl = new Property(8, String.class, "pictureUrl", false, "PICTURE_URL");
        public final static Property FollowingNum = new Property(9, int.class, "followingNum", false, "FOLLOWING_NUM");
        public final static Property FansNum = new Property(10, int.class, "fansNum", false, "FANS_NUM");
        public final static Property MomentsNum = new Property(11, int.class, "momentsNum", false, "MOMENTS_NUM");
        public final static Property DateCheckString = new Property(12, String.class, "dateCheckString", false, "DATE_CHECK_STRING");
        public final static Property Salt = new Property(13, String.class, "salt", false, "SALT");
    }


    public UserInfoModelDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"USER_NAME\" TEXT," + // 1: userName
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"PASSWORD\" TEXT," + // 3: password
                "\"HEIGHT\" REAL NOT NULL ," + // 4: height
                "\"WEIGHT\" REAL NOT NULL ," + // 5: weight
                "\"SEX\" TEXT," + // 6: sex
                "\"AGE\" INTEGER NOT NULL ," + // 7: age
                "\"PICTURE_URL\" TEXT," + // 8: pictureUrl
                "\"FOLLOWING_NUM\" INTEGER NOT NULL ," + // 9: followingNum
                "\"FANS_NUM\" INTEGER NOT NULL ," + // 10: fansNum
                "\"MOMENTS_NUM\" INTEGER NOT NULL ," + // 11: momentsNum
                "\"DATE_CHECK_STRING\" TEXT," + // 12: dateCheckString
                "\"SALT\" TEXT);"); // 13: salt
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindDouble(5, entity.getHeight());
        stmt.bindDouble(6, entity.getWeight());
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
        stmt.bindLong(8, entity.getAge());
 
        String pictureUrl = entity.getPictureUrl();
        if (pictureUrl != null) {
            stmt.bindString(9, pictureUrl);
        }
        stmt.bindLong(10, entity.getFollowingNum());
        stmt.bindLong(11, entity.getFansNum());
        stmt.bindLong(12, entity.getMomentsNum());
 
        String dateCheckString = entity.getDateCheckString();
        if (dateCheckString != null) {
            stmt.bindString(13, dateCheckString);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(14, salt);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindDouble(5, entity.getHeight());
        stmt.bindDouble(6, entity.getWeight());
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
        stmt.bindLong(8, entity.getAge());
 
        String pictureUrl = entity.getPictureUrl();
        if (pictureUrl != null) {
            stmt.bindString(9, pictureUrl);
        }
        stmt.bindLong(10, entity.getFollowingNum());
        stmt.bindLong(11, entity.getFansNum());
        stmt.bindLong(12, entity.getMomentsNum());
 
        String dateCheckString = entity.getDateCheckString();
        if (dateCheckString != null) {
            stmt.bindString(13, dateCheckString);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(14, salt);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfoModel readEntity(Cursor cursor, int offset) {
        UserInfoModel entity = new UserInfoModel( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.getDouble(offset + 4), // height
            cursor.getDouble(offset + 5), // weight
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sex
            cursor.getInt(offset + 7), // age
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // pictureUrl
            cursor.getInt(offset + 9), // followingNum
            cursor.getInt(offset + 10), // fansNum
            cursor.getInt(offset + 11), // momentsNum
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // dateCheckString
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // salt
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoModel entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHeight(cursor.getDouble(offset + 4));
        entity.setWeight(cursor.getDouble(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAge(cursor.getInt(offset + 7));
        entity.setPictureUrl(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setFollowingNum(cursor.getInt(offset + 9));
        entity.setFansNum(cursor.getInt(offset + 10));
        entity.setMomentsNum(cursor.getInt(offset + 11));
        entity.setDateCheckString(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setSalt(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfoModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfoModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoModel entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
