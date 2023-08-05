package com.wf.crowd.service;

import com.wf.crowd.entity.Menu;

import java.util.List;

/**
 * ClassName: MenuService
 * Package: com.wf.crowd.service
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/16 15:06
 * @Version 1.0
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
