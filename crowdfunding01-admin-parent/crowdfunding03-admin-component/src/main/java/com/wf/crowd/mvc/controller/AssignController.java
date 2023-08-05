package com.wf.crowd.mvc.controller;

import com.wf.crowd.entity.Auth;
import com.wf.crowd.entity.Role;
import com.wf.crowd.service.AdminService;
import com.wf.crowd.service.AuthService;
import com.wf.crowd.service.RoleService;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName: AssignController
 * Package: com.wf.crowd.mvc.controller
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/16 19:01
 * @Version 1.0
 */
@Controller
public class AssignController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/admin/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody Map<String, List<Integer>> map) {

        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/admin/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/admin/assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/admin/user/assign/page.html")
    public String toAssignedRolePage(@RequestParam("id") Integer adminId,
                                     @RequestParam("pageNum") String pageNum,
                                     @RequestParam("keyword") String keyword,
                                     Model model){
        List<Role> assignedRoleList=roleService.getAssignedRole(adminId);
        List<Role> unAssignedRoleList=roleService.getUnAssignedRole(adminId);
        model.addAttribute("assignedRoleList",assignedRoleList);
        model.addAttribute("unAssignedRoleList",unAssignedRoleList);
        model.addAttribute("id",adminId);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("keyword",keyword);
        return "admin-assign-role";
    }

    @RequestMapping("/admin/do/role/assign")
    public String saveAdminRoleRelationship(@RequestParam("id") Integer adminId,
                                     @RequestParam("pageNum") String pageNum,
                                     @RequestParam("keyword") String keyword,
                                     @RequestParam(value = "roleIdList",required = false) List roleIdList){
        adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/do/main/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
