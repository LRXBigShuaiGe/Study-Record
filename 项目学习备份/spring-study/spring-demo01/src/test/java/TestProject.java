import com.xin.controller.BaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin
 * @Author: LI Renxin
 * @CreateTime: 2021-05-25 16:51
 * @ModificationHistory Who    When    What
 * @Description: 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestProject {

    @Autowired
    private BaseController baseController;
    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    @Test
    public void testCon(){
//        baseController = ac.getBean(BaseController.class);
        baseController.save();
    }
}
