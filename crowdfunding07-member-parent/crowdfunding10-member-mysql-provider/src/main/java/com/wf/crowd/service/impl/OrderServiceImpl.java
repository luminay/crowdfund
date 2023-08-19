package com.wf.crowd.service.impl;

import com.wf.crowd.entity.po.AddressPO;
import com.wf.crowd.entity.po.AddressPOExample;
import com.wf.crowd.entity.po.OrderPO;
import com.wf.crowd.entity.po.OrderProjectPO;
import com.wf.crowd.entity.vo.AddressVO;
import com.wf.crowd.entity.vo.OrderProjectVO;
import com.wf.crowd.entity.vo.OrderVO;
import com.wf.crowd.mapper.AddressPOMapper;
import com.wf.crowd.mapper.OrderPOMapper;
import com.wf.crowd.mapper.OrderProjectPOMapper;
import com.wf.crowd.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: OrderServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/13 10:59
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId,projectId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressPOExample addressPOExample=new AddressPOExample();
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        List<AddressVO> addressVOList=new ArrayList<>();
        for(AddressPO addressPO:addressPOList){
            AddressVO addressVO=new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveAddress(AddressVO addressVO) {
        AddressPO addressPO=new AddressPO();
        BeanUtils.copyProperties(addressVO, addressPO);
        addressPOMapper.insert(addressPO);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {
        OrderPO orderPO=new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);
        OrderProjectPO orderProjectPO=new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);
        orderPOMapper.insert(orderPO);

        Integer id = orderPO.getId();
        orderProjectPO.setOrderId(id);
        orderProjectPOMapper.insert(orderProjectPO);
    }
}
