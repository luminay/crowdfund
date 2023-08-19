package com.wf.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: PortalTypeVO
 * Package: com.wf.crowd.entity.vo
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/11 16:19
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalTypeVO {
    private Integer id;
    private String name;
    private String remark;

    private List<PortalProjectVO> portalProjectVOList;
}
