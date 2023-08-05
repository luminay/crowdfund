package com.wf.crowd.api;

import com.wf.crowd.entity.po.MemberPO;
import com.wf.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName: MySQLRemoteService
 * Package: com.wf.crowd.api
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/22 16:34
 * @Version 1.0
 */
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/member/save/remote")
    ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/get/memberpo/by/loginAcct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct);
}
