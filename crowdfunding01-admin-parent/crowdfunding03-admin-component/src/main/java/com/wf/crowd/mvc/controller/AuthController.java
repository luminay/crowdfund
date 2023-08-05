package com.wf.crowd.mvc.controller;

import com.wf.crowd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * ClassName: AuthController
 * Package: com.wf.crowd.mvc.controller
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/17 15:17
 * @Version 1.0
 */
@Controller
public class AuthController {
    @Autowired
    private AuthService authService;
}
