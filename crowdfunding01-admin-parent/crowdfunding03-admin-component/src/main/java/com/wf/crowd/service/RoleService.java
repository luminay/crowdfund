package com.wf.crowd.service;

import com.github.pagehelper.PageInfo;
import com.wf.crowd.entity.Role;

import java.util.List;

/**
 * ClassName: RoleService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/12 17:04
 * @Version 1.0
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List roleIdList);

    List<Role> getAssignedRole(Integer id);

    List<Role> getUnAssignedRole(Integer id);
}
