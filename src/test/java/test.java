import com.github.pagehelper.PageInfo;
import com.wlp.bean.Maintain;
import com.wlp.bean.Subscribe;
import com.wlp.dao.MaintainDao;
import com.wlp.dao.PartsRepertoryDao;
import com.wlp.dao.SubscribeDao;
import com.wlp.dao.UserInfoDao;
import com.wlp.service.AccendantService;
import com.wlp.service.MaintainService;
import com.wlp.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 功能：
 *
 * @author 武乐萍
 * @modifier 武乐萍
 * @date 2021-04-17 19:32
 * @Version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {

    @Autowired
    UserInfoService userInfo;

    @Autowired
    UserInfoDao ma;

    @Autowired
    PartsRepertoryDao rp;

    @Autowired
    AccendantService acc;

    @Autowired
    MaintainService main;

    @Autowired
    MaintainDao maintainDao;

    @Autowired
    SubscribeDao subscribeDao;

    @Test
    public void test8(){
        List<Maintain> mainAll = maintainDao.getMainAll();
        System.out.println();
    }

    @Test
    public void test7(){
        List<Subscribe> subscribeAll = subscribeDao.getSubscribeAll();
        System.out.println();
    }

    @Test
    public void test6(){
        PageInfo<Maintain> mainAll = main.getFinish(1, 5);
//        List<Maintain> mainAll1 = maintainDao.getFinish();
        for (Maintain maintain : mainAll.getList()) {
            System.out.println(maintain);
        }

    }

    @Test
    public void test1(){
//        rp.uppartsPrice(3,2000);
//        userInfo.selectAll(null,null);
        rp.selectPartsAll();
//        ma.selectAll();
//        acc.selectAll(1,2);
//        partsRepertory partsrepertory = new partsRepertory();
//        partsrepertory.setCount(10);
//        partsrepertory.setPartsId(3);
//        rp.upCount(partsrepertory);

//        rp.inPartsInfo(partsrepertory);

//        Integer integer = rp.selectPartsExist(2);
//        System.out.println(integer);
    }


    @Test
    public void  tesxt(){

        userInfo.getRoleByUserId(4);
    }

    @Test
    public void test(){
        userInfo.getRouter(4);
        userInfo.getRole(4);
    }
}
