package com.wf.crowd.mvc.controller;

import com.wf.crowd.entity.Menu;
import com.wf.crowd.service.MenuService;
import com.wf.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuController
 * Package: com.wf.crowd.mvc.controller
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/16 15:08
 * @Version 1.0
 */
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    
    @RequestMapping("/admin/menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {

        menuService.removeMenu(id);

        return ResultEntity.successWithoutData();
    }

    
    @RequestMapping("/admin/menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {

        menuService.updateMenu(menu);

        return ResultEntity.successWithoutData();
    }

    
    @RequestMapping("/admin/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu) {

        // Thread.sleep(2000);

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }

    
    @RequestMapping("/admin/menu/get/tree.json")
    public ResultEntity<Menu> getWholeTreeNew() {
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }
        for(Menu menu:menuList){
            Integer pid = menu.getPid();
            if(pid==null){
                root=menu;
                continue;
            }
            Menu father = menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }
}
