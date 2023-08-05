package com.wf.crowd.mvc.controller;

import com.wf.crowd.entity.Admin;
import com.wf.crowd.mapper.AdminMapper;
import com.wf.crowd.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: TestController
 * Package: com.wf.crowd.mvc.controller
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/8 17:14
 * @Version 1.0
 */
@Controller
public class TestController {
    private Logger logger=LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AdminService adminService;


    @RequestMapping("/test")
    public String test(Model model){
        List<Admin> adminList=adminService.getAll();
        model.addAttribute("adminList",adminList);
        throw new NullPointerException("hhhhh");
    }
}
