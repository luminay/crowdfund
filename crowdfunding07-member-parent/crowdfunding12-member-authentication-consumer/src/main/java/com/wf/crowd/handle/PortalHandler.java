package com.wf.crowd.handle;

import com.wf.crowd.api.MySQLRemoteService;
import com.wf.crowd.constant.CrowdConstant;
import com.wf.crowd.entity.vo.PortalProjectVO;
import com.wf.crowd.entity.vo.PortalTypeVO;
import com.wf.crowd.util.ResultEntity;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * ClassName: PortalHandler
 * Package: com.wf.crowd.handle
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/23 9:59
 * @Version 1.0
 */
@Controller
public class PortalHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Model model) {
        ResultEntity<List<PortalTypeVO>> portalTypeProjectDataRemote = mySQLRemoteService.getPortalTypeProjectDataRemote();
        String result = portalTypeProjectDataRemote.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            List<PortalTypeVO> list = portalTypeProjectDataRemote.getData();
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, list);
        }
        return "portal";
    }
}
