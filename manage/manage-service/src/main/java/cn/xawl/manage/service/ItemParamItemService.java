package cn.xawl.manage.service;

import cn.xawl.manage.dao.ItemParamItemMapper;
import cn.xawl.manage.pojo.ItemParamItem;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public Integer updateParamItem(Long id, String itemParams) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", id);
        return itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
    }
}
