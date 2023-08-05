package com.wf.crowd.service;

import com.wf.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AuthService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/17 15:16
 * @Version 1.0
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
