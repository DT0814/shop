package cn.xawl.manage.service;

import cn.xawl.manage.dao.ItemCatMapper;
import cn.xawl.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    public List<ItemCat> queryItemListByParentId(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        List<ItemCat> select = itemCatMapper.select(itemCat);
        return select;
    }
}
