package com.wf.crowd.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wf.crowd.entity.Role;
import com.wf.crowd.entity.RoleExample;
import com.wf.crowd.mapper.RoleMapper;
import com.wf.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: RoleServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/12 17:04
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        return new PageInfo<>(roleList,5);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void removeRole(List roleIdList) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer id) {
        return roleMapper.selectAssignedRole(id);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer id) {
        return roleMapper.selectUnAssignedRole(id);
    }
}
