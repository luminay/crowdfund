package com.wf.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: AccessPassResources
 * Package: com.wf.crowd.constant
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/29 16:02
 * @Version 1.0
 */
public class AccessPassResources {
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/member/to/login/page");
        PASS_RES_SET.add("/member/to/reg/page");
        PASS_RES_SET.add("/member/do/logout");
        PASS_RES_SET.add("/member/do/reg");
        PASS_RES_SET.add("/auth/member/send/authCode");
        PASS_RES_SET.add("/member/do/login");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    public static boolean judgeCurrentServletPathWhetherStaticResource(String servletPath) {
        //排除路径字符串无效的情况
        if (servletPath == null && servletPath.length() == 0) {
            return false;
        }

        String[] split = servletPath.split("/");
        String firstLevelPath=split[1];

        //判断是否在静态路径集合中
        return STATIC_RES_SET.contains(firstLevelPath);
    }
}
