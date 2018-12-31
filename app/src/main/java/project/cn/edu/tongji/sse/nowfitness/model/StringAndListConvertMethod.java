package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class StringAndListConvertMethod implements PropertyConverter<List<String>,String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if(databaseValue == null){
            return new ArrayList<>();
        }else{
            return asList(",".split(databaseValue));
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
