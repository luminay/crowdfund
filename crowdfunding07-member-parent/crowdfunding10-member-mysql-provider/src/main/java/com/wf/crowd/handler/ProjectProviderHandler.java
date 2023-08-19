package com.wf.crowd.handler;

import com.wf.crowd.entity.vo.DetailProjectVO;
import com.wf.crowd.entity.vo.PortalTypeVO;
import com.wf.crowd.entity.vo.ProjectVO;
import com.wf.crowd.service.ProjectService;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ProjectProviderHandler
 * Package: com.wf.crowd.handler
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/5 21:47
 * @Version 1.0
 */
@RestController
public class ProjectProviderHandler {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId")
                                                                  Integer projectId) {
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {
        try {
            List<PortalTypeVO> portalTypeVO = projectService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
    ) {
        try {
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
