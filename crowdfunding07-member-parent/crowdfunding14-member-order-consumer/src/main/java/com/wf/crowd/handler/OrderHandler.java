package com.wf.crowd.handler;

import com.wf.crowd.api.MySQLRemoteService;
import com.wf.crowd.constant.CrowdConstant;
import com.wf.crowd.entity.vo.AddressVO;
import com.wf.crowd.entity.vo.MemberLoginVO;
import com.wf.crowd.entity.vo.OrderProjectVO;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName: OrderHandler
 * Package: com.wf.crowd.handler
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/8/13 10:49
 * @Version 1.0
 */
@Controller
public class OrderHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){
        ResultEntity<String> resultEntity=mySQLRemoteService.saveAddressRemote(addressVO);
        OrderProjectVO orderProjectVO= (OrderProjectVO) session.getAttribute("orderProjectVO");
        Integer returnCount = orderProjectVO.getReturnCount();
        return "redirect:http://192.168.71.104/order/confirm/order/"+returnCount;
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,
                                       HttpSession session){
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        ResultEntity<List<AddressVO>> resultEntity=mySQLRemoteService.getAddressVORemote(memberId);
        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList", list);
        }
        return "confirm-order";
    }

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session){
        ResultEntity<OrderProjectVO> orderProjectVOResultEntity=mySQLRemoteService.getOrderProjectVORemote(projectId,returnId);
        String result = orderProjectVOResultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            OrderProjectVO orderProjectVO = orderProjectVOResultEntity.getData();
            session.setAttribute("orderProjectVO",orderProjectVO);
        }
        return "confirm-return";
    }
}
