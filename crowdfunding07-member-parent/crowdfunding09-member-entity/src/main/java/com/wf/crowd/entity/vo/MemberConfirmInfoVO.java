package com.wf.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: MemberConfirmInfoVO
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
public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 易付宝账号
    private String paynum;
    // 法人身份证号
    private String cardnum;
}
