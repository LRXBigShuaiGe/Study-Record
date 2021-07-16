import com.xin.impl.MyMathCalculator;
import com.xin.inter.Calculator;
import com.xin.proxy.CalculatorProxy;
import org.junit.Test;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: PACKAGE_NAME
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:31
 * @ModificationHistory Who    When    What
 * @Description: aop测试类
 */
public class AOPTest {

    @Test
    public void test01(){
        Calculator proxy = CalculatorProxy.getProxy(new MyMathCalculator());
        proxy.add(1,2);

//        proxy.div(3,2);
//
//        proxy.mul(3,2);
//
//        proxy.sub(3,2);
    }
}
