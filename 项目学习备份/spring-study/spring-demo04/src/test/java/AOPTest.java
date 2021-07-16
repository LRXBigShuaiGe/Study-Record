 import com.xin.impl.MyMathCalculator;
 import com.xin.inter.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 import org.springframework.jdbc.core.JdbcTemplate;

 import javax.sql.DataSource;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.Arrays;

 /**
 * @BelongsProject: spring-study
 * @BelongsPackage: PACKAGE_NAME
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:31
 * @ModificationHistory Who    When    What
 * @Description: aop测试类
 */

public class AOPTest {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    JdbcTemplate template = ioc.getBean(JdbcTemplate.class);

    @Test
    public void test02(){
        Calculator c = (Calculator) ioc.getBean("myMathCalculator");
        int result = c.add(1, 2);
        System.out.println("test02 结果："+result);
        //c.div(1,0);
    }

    @Test
     public void test03() throws SQLException {
        DataSource bean = ioc.getBean(DataSource.class);
        Connection connection = bean.getConnection();
        System.out.println(connection);
        connection.close();
    }

     @Test
     public void test04() throws SQLException {
        String sql = "UPDATE user SET password=? WHERE id=?";
         int update = template.update(sql, "789456", 2);
         System.out.println("更新用户："+update);
     }

     @Test
     public void test05() throws SQLException {
         String sql = "INSERT INTO user (username, password)values (?,?);";
         ArrayList<Object[]> list = new ArrayList<>();
         list.add(new Object[]{"root01","2345kkk"});
         list.add(new Object[]{"root02","2345kkk"});
         list.add(new Object[]{"root03","2345kkk"});
         list.add(new Object[]{"root04","2345kkk"});
         int[] ints = template.batchUpdate(sql, list);
         System.out.println(Arrays.asList(ints));

     }


 }
