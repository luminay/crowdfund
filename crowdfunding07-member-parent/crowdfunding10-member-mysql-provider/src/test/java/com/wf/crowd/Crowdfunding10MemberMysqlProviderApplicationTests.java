package com.wf.crowd;

import com.wf.crowd.entity.po.MemberPO;
import com.wf.crowd.entity.vo.DetailProjectVO;
import com.wf.crowd.entity.vo.PortalProjectVO;
import com.wf.crowd.entity.vo.PortalTypeVO;
import com.wf.crowd.mapper.MemberPOMapper;
import com.wf.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Crowdfunding10MemberMysqlProviderApplication.class)
public class Crowdfunding10MemberMysqlProviderApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    private static Logger logger=LoggerFactory.getLogger(Crowdfunding10MemberMysqlProviderApplicationTests.class);

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

    @Test
    public void testLoadPortalType(){
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
        for(PortalTypeVO portalTypeVO: portalTypeVOS){
            logger.debug("name="+portalTypeVO.getName()+" remark="+portalTypeVO.getRemark());
            List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
            for (PortalProjectVO projectVO: portalProjectVOList){
                if (projectVO==null){
                    continue;
                }
                logger.debug(projectVO.toString());
            }
        }
    }

    @Test
    public void testLoadProjectDetail(){
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(15);
        logger.debug(detailProjectVO.toString());
    }

}
