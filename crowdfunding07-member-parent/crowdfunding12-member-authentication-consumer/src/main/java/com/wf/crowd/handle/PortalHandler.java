package com.wf.crowd.handle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName: PortalHandler
 * Package: com.wf.crowd.handle
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/23 9:59
 * @Version 1.0
 */
@Controller
public class PortalHandler {

    @RequestMapping("/")
    public String showPortalPage() {

        return "portal";
    }
}
