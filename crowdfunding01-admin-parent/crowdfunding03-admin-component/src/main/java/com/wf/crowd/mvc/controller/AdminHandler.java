package com.wf.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.wf.crowd.constant.CrowdConstant;
import com.wf.crowd.entity.Admin;
import com.wf.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ClassName: AdminHandler
 * Package: com.wf.crowd.mvc.controller
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/10 12:17
 * @Version 1.0
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/error")
    public String adminError(HttpServletRequest request) {
        request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
        return "system-error";
    }

    @RequestMapping("/admin/do/user/update")
    public String update(Admin admin,
                         @RequestParam("pageNum") String pageNum,
                         @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/do/main/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/to/main/user/edit/page.html")
    public String getAdminById(
            @RequestParam("id") Integer id,
            @RequestParam("pageNum") String pageNum,
            @RequestParam("keyword") String keyword,
            Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("id",id);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("keyword",keyword);
        return "admin-user-edit";
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/do/user/save.html")
    public String saveAdmin(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/do/main/user.html?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/do/main/user/remove/{id}/{pageNum}/{keyword}.html")
    public String removeUser(
            @PathVariable("id") Integer id,
            @PathVariable("pageNum") String pageNum,
            @PathVariable("keyword") String keyword) {
        adminService.removeById(id);
        return "redirect:/admin/do/main/user.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/do/main/user.html")
    public String getUser(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        PageInfo<Admin> pageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        model.addAttribute("keyword", keyword);
        return "admin-user";
    }

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session) {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/do/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/do/login/page.html";
    }
}
