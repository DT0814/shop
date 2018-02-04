package cn.xawl.manage.service;

import cn.xawl.common.EasyUIResult;
import cn.xawl.common.service.ApiService;
import cn.xawl.manage.dao.ItemMapper;
import cn.xawl.manage.pojo.Item;
import cn.xawl.manage.pojo.ItemDesc;
import cn.xawl.manage.pojo.ItemParamItem;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ApiService apiService;

    @Value( "${SHOP_WEB_URL}" )
    private String SHOP_WEB_URL;

    public Boolean saveItem(Item item, String desc, String itemParams) {
        item.setStatus(1);
        item.setId(null);
        Integer itemInt = this.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer itemDescInt = itemDescService.save(itemDesc);

        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        Integer itemParamItemInt = itemParamItemService.save(itemParamItem);
        return itemInt == 1 && itemDescInt == 1 && itemParamItemInt == 1 ? true : false;
    }

    public EasyUIResult queryList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        PageHelper.startPage(page, rows);
        List<Item> list = this.itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<Item>(list);
        EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
        return easyUIResult;
    }


    public Boolean updateItem(Item item, String desc, String itemParams) {
        item.setStatus(null);
        item.setCreated(null);
        Integer itemInt = super.updateSelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(item.getId());
        Integer itemDescInt = itemDescService.updateSelective(itemDesc);

        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", item.getId());

        Integer itemParamItemInt = itemParamItemService.updateParamItem(item.getId(), itemParams);
        try {
            String url = SHOP_WEB_URL + "/item/cache/" + item.getId();
            apiService.doPost(url);
        } catch ( Exception e ) {
        }

        return itemInt == 1 && itemDescInt == 1 && itemParamItemInt == 1 ? true : false;
    }
}
