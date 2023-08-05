package com.wf.crowd.test;

import com.wf.crowd.entity.Admin;
import com.wf.crowd.entity.Role;
import com.wf.crowd.mapper.AdminMapper;
import com.wf.crowd.mapper.RoleMapper;
import com.wf.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * ClassName: CrowdTest
 * Package: com.wf.crowd
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/7 17:25
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
    private Logger logger=LoggerFactory.getLogger(CrowdTest.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testSave(){
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null,"roleName"+i));
        }
    }

    @Test
    public void testSaveAdminMulti() {
        for(int i = 0; i < 352; i++) {
            adminMapper.insert(new Admin(null, "loginAcct" + i, "userPswd" + i, "userName" + i, "email" + i + "@qq.com", null));
        }
    }
            @Test
    public void testTx(){
        adminService.saveAdmin(new Admin(null,"jerry","123456","杰瑞","jerry@qq.com",null));

    }

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.info("hhhhhhhhhhhhhhhh");
    }

    @Test
    public void insertTest(){
        Admin admin = new Admin(null, "汤姆", "123456", "tom", "tom@qq.com", null);
        int insert = adminMapper.insert(admin);
        System.out.println("受影响的行数："+insert);
    }

    @Test
    public void datasourceTest() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
