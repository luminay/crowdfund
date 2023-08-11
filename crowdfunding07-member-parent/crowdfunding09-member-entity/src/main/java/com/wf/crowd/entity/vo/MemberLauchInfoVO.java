package com.wf.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: MemberLauchInfoVO
 * Package: com.wf.crowd.entity.vo
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/6 13:05
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLauchInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;
    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phoneNum;
    // 客服电话
    private String serviceNum;
}