import com.xin.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: PACKAGE_NAME
 * @Author: LI Renxin
 * @CreateTime: 2021-06-06 17:11
 * @ModificationHistory Who    When    What
 * @Description: 用户测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserTest {
    @Autowired
    BookService service;

    @Test
    public void testService(){
        service.daoUser("iamnr",7);
    }
}
