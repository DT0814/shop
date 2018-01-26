package cn.xawl.manage.service;

import cn.xawl.manage.pojo.Item;
import cn.xawl.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemDescService itemDescService;

    public Boolean saveItem(Item item, String desc) {
        item.setStatus(1);
        item.setId(null);
        Integer itemInt = this.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer itemDescInt = itemDescService.save(itemDesc);
        return itemInt == 1 && itemDescInt == 1 ? true : false;
    }
}
