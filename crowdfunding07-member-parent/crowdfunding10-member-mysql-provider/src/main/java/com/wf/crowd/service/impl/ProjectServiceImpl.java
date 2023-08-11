package com.wf.crowd.service.impl;

import com.wf.crowd.entity.po.MemberConfirmInfoPO;
import com.wf.crowd.entity.po.MemberLaunchInfoPO;
import com.wf.crowd.entity.po.ProjectPO;
import com.wf.crowd.entity.po.ReturnPO;
import com.wf.crowd.entity.vo.MemberConfirmInfoVO;
import com.wf.crowd.entity.vo.MemberLauchInfoVO;
import com.wf.crowd.entity.vo.ProjectVO;
import com.wf.crowd.entity.vo.ReturnVO;
import com.wf.crowd.mapper.*;
import com.wf.crowd.service.ProjectService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ProjectServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/5 21:48
 * @Version 1.0
 */
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 1.保存projectPO
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO, projectPO);

        projectPO.setMemberid(memberId);
        String createDate = String.format(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        projectPO.setCreatedate(createDate);
        projectPO.setStatus(0);

        projectPOMapper.insertSelective(projectPO);
        Integer projectPOId = projectPO.getId();

        // 2.保存项目、分类的关联关系信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectPOId);
        // 3.保存项目、标签的关联关系信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectPOId);
        // 4.保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(detailPicturePathList, projectPOId);
        // 5.保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        // 6.保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList,projectPOId);
        // 7.保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }
}
