import com.xin.inter.Calculator;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: PACKAGE_NAME
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:31
 * @ModificationHistory Who    When    What
 * @Description: aop测试类
 */

public class AOPTest {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


    @Test
    public void test02(){
        Calculator c = ioc.getBean(Calculator.class);
        int result = c.add(1, 2);
        System.out.println("test02 结果："+result);
        c.div(1,0);
    }
}
