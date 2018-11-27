package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

public class StrinrgAndListConvertMethod implements PropertyConverter<List<String>,String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if(databaseValue == null){
            return null;
        }else{
            List<String> list = Arrays.asList(databaseValue.split(","));
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty ==null){
            return null;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for(String string:entityProperty){
                stringBuilder.append(string);
                stringBuilder.append(",");
            }
            return stringBuilder.toString();
        }
    }
}
