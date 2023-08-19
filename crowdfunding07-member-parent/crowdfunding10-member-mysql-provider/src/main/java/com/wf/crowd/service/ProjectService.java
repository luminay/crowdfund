package com.wf.crowd.service;

import com.wf.crowd.entity.vo.DetailProjectVO;
import com.wf.crowd.entity.vo.PortalTypeVO;
import com.wf.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * ClassName: ProjectService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/5 21:48
 * @Version 1.0
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
    List<PortalTypeVO> getPortalTypeVO();
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
