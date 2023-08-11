package com.wf.crowd.handler;

import com.wf.crowd.entity.vo.ProjectVO;
import com.wf.crowd.service.ProjectService;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
            ){
        try {
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
