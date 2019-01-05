package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

/**
 * Created by LK on 2018/11/28.
 */

public class DaoMethod {

    private DaoMethod(){}

    /**
     * 向Token表中插入token
     * @param token token变量
     */
    public static void insertToken(Token token){
        token.setId(Token.TOKEN_ID);
        DaoManager.getDaoInstance().getDaoSession().getTokenDao()
                .insertOrReplace(token);
    }

    /**
     * 表中只存一个Token,所以当用户退出登录时,直接删除表中全部数据
     */
    public static void deleteToken(){
        DaoManager.getDaoInstance().getDaoSession().getTokenDao()
                .deleteAll();
    }

    /**
     * 获取用户信息
     * @return 只包含一个用户信息的UserInfoModel List
     */
    public static List<UserInfoModel> queryForUserInfo(){
        return DaoManager.getDaoInstance().getDaoSession().getUserInfoModelDao()
                .queryBuilder().list();
    }

    /**
     * 从数据库获取Token
     * @return  token
     */
    public static List<Token> queryForToken(){
        return DaoManager.getDaoInstance().getDaoSession().getTokenDao()
                .queryBuilder().list();
    }

    /**
     * 从本地数据库获取步数
     * @param date  日期
     * @param userID  用户id
     * @return 步数的List,但只包含一个数据
     */
    public static List<StepModel> queryByDate(String date,long userID){
        QueryBuilder<StepModel> queryBuilder =
                DaoManager.getDaoInstance().getDaoSession().getStepModelDao().queryBuilder();
        queryBuilder.where(queryBuilder.
                and(StepModelDao.Properties.Today.eq(date),StepModelDao.Properties.UserId.eq(userID)));
        return queryBuilder.list();
    }

}
