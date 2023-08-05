package com.wf.crowd.service.impl;

import com.wf.crowd.entity.po.MemberPO;
import com.wf.crowd.entity.po.MemberPOExample;
import com.wf.crowd.mapper.MemberPOMapper;
import com.wf.crowd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: MemberServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/22 16:38
 * @Version 1.0
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {
        MemberPOExample memberPOExample=new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginacctEqualTo(loginAcct);
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        return memberPOS.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
