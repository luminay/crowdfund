package com.wf.crowd.service;

import com.wf.crowd.entity.po.MemberPO;

/**
 * ClassName: MemberService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/22 16:38
 * @Version 1.0
 */
public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginAcct);

    void saveMember(MemberPO memberPO);
}
