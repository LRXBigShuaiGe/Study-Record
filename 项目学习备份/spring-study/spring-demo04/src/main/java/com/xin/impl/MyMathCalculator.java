package com.xin.impl;

import com.xin.inter.Calculator;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.impl
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:29
 * @ModificationHistory Who    When    What
 * @Description: 计算器实现类
 */
public class MyMathCalculator implements Calculator {
    public MyMathCalculator() {
    }

    public int add(int i, int j) {
        int result = i + j;
        return result;
    }


    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }


    public int mul(int i, int j) {
        int result = i * j;
        return result;
    }


    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}
