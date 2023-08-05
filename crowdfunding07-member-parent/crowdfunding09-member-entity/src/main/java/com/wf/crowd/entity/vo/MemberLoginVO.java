package com.wf.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * ClassName: MemberVO
 * Package: com.wf.crowd.entity.vo
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/24 17:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberLoginVO implements Serializable {
    private Integer id;

    private String username;

    private String email;

}
