package com.wf.crowd.service;

import com.github.pagehelper.PageInfo;
import com.wf.crowd.entity.Admin;

import java.util.List;

/**
 * ClassName: AdminService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/8 9:51
 * @Version 1.0
 */

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize);

    void removeById(Integer id);

    Admin getAdminById(Integer id);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List roleIdList);

    Admin getAdminByLoginAcct(String username);
}
