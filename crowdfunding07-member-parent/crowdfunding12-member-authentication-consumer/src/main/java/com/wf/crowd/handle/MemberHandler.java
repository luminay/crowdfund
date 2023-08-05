package com.wf.crowd.handle;

import com.wf.crowd.api.MySQLRemoteService;
import com.wf.crowd.api.RedisRemoteService;
import com.wf.crowd.constant.CrowdConstant;
import com.wf.crowd.entity.po.MemberPO;
import com.wf.crowd.entity.vo.MemberLoginVO;
import com.wf.crowd.entity.vo.MemberVO;
import com.wf.crowd.service.EmailService;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: MemberHandler
 * Package: com.wf.crowd.handle
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/24 11:21
 * @Version 1.0
 */
@Controller
public class MemberHandler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/member/to/start")
    public String toCrowd(){
        return "member-start";
    }

    @RequestMapping("/member/do/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "member-login";
    }

    @RequestMapping("/member/to/center/page")
    public String toCenter(){
        return "member-center";
    }

    @RequestMapping("/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct,
                        @RequestParam("userpswd") String userpswd,
                        Model model,
                        HttpSession session){
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if (memberPO==null){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        String userpswdData = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(userpswd, userpswdData)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
        return "redirect:/member/to/center/page";
    }

    @RequestMapping("/member/do/reg")
    public String register(MemberVO memberVO, Model model) {
        String email = memberVO.getEmail();
        String key = CrowdConstant.REDIS_CODE_EMAIL + email;
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        if(ResultEntity.FAILED.equals(redisStringValueByKeyRemote.getResult())){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisStringValueByKeyRemote.getMessage());
            return "member-reg";
        }
        String redisCode = redisStringValueByKeyRemote.getData();
        if(redisCode==null){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }
        String formCode = memberVO.getCode();
        if(!Objects.equals(formCode, redisCode)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        redisRemoteService.removeRedisKeyRemote(key);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //加密后的
        String encode = passwordEncoder.encode(memberVO.getUserpswd());
        memberVO.setUserpswd(encode);

        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);
        if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        return "redirect:/member/to/login/page";
    }

    @ResponseBody
    @RequestMapping("/auth/member/send/authCode")
    public ResultEntity<String> sendEmailCode(@RequestParam("email") String email) {
        Random random = new Random();
        int code = 1000 + random.nextInt(8999);
        String message = "你好，验证码为" + code + "，如非本人操作，请忽视";
        if (emailService.sendEmail(email, "验证码", message)) {
            String key = CrowdConstant.REDIS_CODE_EMAIL + email;
            ResultEntity<String> saveResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, String.valueOf(code), 2, TimeUnit.MINUTES);
            return saveResultEntity;
        } else {
            return ResultEntity.failed("发送失败，请重试！");
        }
    }
}
