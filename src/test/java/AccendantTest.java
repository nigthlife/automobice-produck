import com.wlp.bean.Accendant;
import com.wlp.service.AccendantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class AccendantTest {

  // 创建service层对象
  @Autowired private AccendantService service;

  @Test
  public void deleteByPrimaryKeyTest() {
    service.deleteByPrimaryKey(1);
  }

  @Test
  public void insert() {
    service.insert(new Accendant("李四"));
  }

  @Test
  public void insertSelective() {
    service.insertSelective(new Accendant("77"));
  }

  @Test
  public void updateByPrimaryKeySelective() {
    Accendant a = new Accendant(1, "三");
    System.out.println(a.getPhone());
    service.updateByPrimaryKeySelective(a);
  }

  @Test
  public void selectAll() {

  }

  @Test
  public void selectByPrimaryKey() {
    System.out.println(service.selectByPrimaryKey(1));
  }

  @Test
  public void updateByPrimaryKey() {
    service.updateByPrimaryKey(new Accendant(1, "张三"));
  }
}
