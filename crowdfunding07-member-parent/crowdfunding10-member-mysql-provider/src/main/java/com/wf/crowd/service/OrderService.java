package com.wf.crowd.service;

import com.wf.crowd.entity.vo.AddressVO;
import com.wf.crowd.entity.vo.OrderProjectVO;
import com.wf.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * ClassName: OrderService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/13 10:59
 * @Version 1.0
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
