package com.wf.crowd;

import com.wf.crowd.entity.po.MemberPO;
import com.wf.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Crowdfunding10MemberMysqlProviderApplication.class)
public class Crowdfunding10MemberMysqlProviderApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Test
    public void testMapper(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String source="123123";
        memberPOMapper.insert(new MemberPO(null,"jack",passwordEncoder.encode(source),"杰 克","jack@qq.com",1,1,"杰克","123123",2));
    }

    @Test
    public void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}
