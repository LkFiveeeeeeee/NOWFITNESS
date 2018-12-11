package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndiInfoModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndiRelationModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.UserInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.IndiInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.IndiRelationModelDao;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.StepModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInfoModelDaoConfig;
    private final DaoConfig indiInfoModelDaoConfig;
    private final DaoConfig indiRelationModelDaoConfig;
    private final DaoConfig stepModelDaoConfig;

    private final UserInfoModelDao userInfoModelDao;
    private final IndiInfoModelDao indiInfoModelDao;
    private final IndiRelationModelDao indiRelationModelDao;
    private final StepModelDao stepModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInfoModelDaoConfig = daoConfigMap.get(UserInfoModelDao.class).clone();
        userInfoModelDaoConfig.initIdentityScope(type);

        indiInfoModelDaoConfig = daoConfigMap.get(IndiInfoModelDao.class).clone();
        indiInfoModelDaoConfig.initIdentityScope(type);

        indiRelationModelDaoConfig = daoConfigMap.get(IndiRelationModelDao.class).clone();
        indiRelationModelDaoConfig.initIdentityScope(type);

        stepModelDaoConfig = daoConfigMap.get(StepModelDao.class).clone();
        stepModelDaoConfig.initIdentityScope(type);

        userInfoModelDao = new UserInfoModelDao(userInfoModelDaoConfig, this);
        indiInfoModelDao = new IndiInfoModelDao(indiInfoModelDaoConfig, this);
        indiRelationModelDao = new IndiRelationModelDao(indiRelationModelDaoConfig, this);
        stepModelDao = new StepModelDao(stepModelDaoConfig, this);

        registerDao(UserInfoModel.class, userInfoModelDao);
        registerDao(IndiInfoModel.class, indiInfoModelDao);
        registerDao(IndiRelationModel.class, indiRelationModelDao);
        registerDao(StepModel.class, stepModelDao);
    }
    
    public void clear() {
        userInfoModelDaoConfig.clearIdentityScope();
        indiInfoModelDaoConfig.clearIdentityScope();
        indiRelationModelDaoConfig.clearIdentityScope();
        stepModelDaoConfig.clearIdentityScope();
    }

    public UserInfoModelDao getUserInfoModelDao() {
        return userInfoModelDao;
    }

    public IndiInfoModelDao getIndiInfoModelDao() {
        return indiInfoModelDao;
    }

    public IndiRelationModelDao getIndiRelationModelDao() {
        return indiRelationModelDao;
    }

    public StepModelDao getStepModelDao() {
        return stepModelDao;
    }

}
