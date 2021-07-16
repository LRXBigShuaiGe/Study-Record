package com.xin.component;

import com.xin.bean.Car;
import org.springframework.core.convert.converter.Converter;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.component
 * @Author: LI Renxin
 * @CreateTime: 2021-07-12 15:32
 * @ModificationHistory Who    When    What
 * @Description: 自定义数据类型转换器
 */
public class MyStringToCarConverter implements Converter<String, Car> {

    /**
     * 自定义类型转换方法
     * @param s
     * @return
     */
    @Override
    public Car convert(String s) {
        System.out.println("需要转换的字符串："+s);
        Car car = new Car();
        if (s.contains("-")){
            String[] split = s.split("-");
            car.setCarName(split[0]);
            car.setType(split[1]);
            car.setName(split[2]);
        }
        return car;
    }
}
