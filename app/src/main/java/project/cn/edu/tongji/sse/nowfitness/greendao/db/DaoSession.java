package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.UserInfoModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInfoModelDaoConfig;

    private final UserInfoModelDao userInfoModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInfoModelDaoConfig = daoConfigMap.get(UserInfoModelDao.class).clone();
        userInfoModelDaoConfig.initIdentityScope(type);

        userInfoModelDao = new UserInfoModelDao(userInfoModelDaoConfig, this);

        registerDao(UserInfoModel.class, userInfoModelDao);
    }
    
    public void clear() {
        userInfoModelDaoConfig.clearIdentityScope();
    }

    public UserInfoModelDao getUserInfoModelDao() {
        return userInfoModelDao;
    }

}