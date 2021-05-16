import com.wlp.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class ResourceServiceImplTest {

  //  创建业务层对象
  @Autowired private ResourceService service;

  @Test
  public void insert() {}

  @Test
  public void insertSelective() {}

  @Test
  public void deleteByPrimaryKey() {}

  @Test
  public void updateByPrimaryKeySelective() {}

  @Test
  public void selectAll() {}
}
