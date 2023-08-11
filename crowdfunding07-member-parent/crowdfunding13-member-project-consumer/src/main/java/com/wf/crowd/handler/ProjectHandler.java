package com.wf.crowd.handler;

import com.wf.crowd.api.MySQLRemoteService;
import com.wf.crowd.config.OSSProperties;
import com.wf.crowd.constant.CrowdConstant;
import com.wf.crowd.entity.vo.MemberConfirmInfoVO;
import com.wf.crowd.entity.vo.MemberLoginVO;
import com.wf.crowd.entity.vo.ProjectVO;
import com.wf.crowd.entity.vo.ReturnVO;
import com.wf.crowd.util.CrowdUtil;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ProjectHandler
 * Package: com.wf.crowd.handler
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/6 16:27
 * @Version 1.0
 */
@Controller
public class ProjectHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @Autowired
    private OSSProperties ossProperties;

    @RequestMapping("/create/confirm")
    public String saveConfirm(Model model, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if(projectVO==null){
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        ResultEntity<String> resultEntity= mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
        String result = resultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "project-confirm";
        }

        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        return "redirect:http://localhost/project/create/success";
    }

    @ResponseBody
    @RequestMapping("/create/save/return")
    public ResultEntity<String> saveReturn(ReturnVO returnVO,HttpSession session){
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if (projectVO==null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            if(returnVOList==null||returnVOList.size()==0){
                returnVOList=new ArrayList<>();
            }
            returnVOList.add(returnVO);
            projectVO.setReturnVOList(returnVOList);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), returnPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), returnPicture.getOriginalFilename());
        return uploadReturnPicResultEntity;
    }

    @PostMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList, HttpSession session, Model model) throws IOException {

        //头图
        boolean headerPictureEmpty = headerPicture.isEmpty();
        if (headerPictureEmpty) {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
        }
        //上传头图去OSS
        ResultEntity<String> uploadFileToOssResult = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), headerPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), headerPicture.getOriginalFilename());
        String result = uploadFileToOssResult.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            String headPicturePath = uploadFileToOssResult.getData();
            projectVO.setHeaderPicturePath(headPicturePath);
        } else {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }

        //详情图片
        List<String> detailPicturePathList = new ArrayList<>();
        if (detailPictureList == null || detailPictureList.size() == 0) {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        for (MultipartFile detailPicture : detailPictureList) {
            if (detailPicture.isEmpty()){
                model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), detailPicture.getOriginalFilename());
            String detailUploadResult = detailUploadResultEntity.getResult();
            if (ResultEntity.SUCCESS.equals(detailUploadResult)){
                String detailPicturePath = detailUploadResultEntity.getData();
                detailPicturePathList.add(detailPicturePath);
            }else {
                model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
        }
        projectVO.setDetailPicturePathList(detailPicturePathList);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return "redirect:http://localhost/project/return/info/page";
    }
}
