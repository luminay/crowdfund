package com.wf.crowd.service.impl;

import com.wf.crowd.entity.Menu;
import com.wf.crowd.entity.MenuExample;
import com.wf.crowd.mapper.MenuMapper;
import com.wf.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: MenuServiceImpl
 * Package: com.wf.crowd.service.impl
 * Description:
 *
 * @Author XiaoFan
 * @Create 2023/7/16 15:07
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {

        // 由于pid没有传入，一定要使用有选择的更新，保证“pid”字段不会被置空
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
