package cn.xawl.manage.service;

import cn.xawl.common.ItemCatData;
import cn.xawl.common.ItemCatResult;
import cn.xawl.common.service.RedisService;
import cn.xawl.manage.pojo.ItemCat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCatService extends BaseService<ItemCat> {
    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String KEY_NAME = "MANAGE_ITEM_CAT_QUERY_ALL";

    private final Integer KEY_TIME = 60 * 60 * 24 * 30 * 3;

    /**
     * 全部查询，并且生成树状结构
     *
     * @return
     */
    public ItemCatResult queryAllToTree() {
        try {
            String cacheData = redisService.get(KEY_NAME);
            if ( !StringUtils.isEmpty(cacheData) ) {
                System.out.println("读取缓存");
                return MAPPER.readValue(cacheData, ItemCatResult.class);
            }

        } catch ( Exception e ) {
            System.out.println(KEY_NAME + "缓存查询失败");
            e.printStackTrace();
        }
        ItemCatResult result = new ItemCatResult();
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();
        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        cats.stream().forEach((itemCat) -> {
            if ( !itemCatMap.containsKey(itemCat.getParentId()) ) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);

        });

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for ( ItemCat itemCat : itemCatList1 ) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setNname("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if ( !itemCat.getIsParent() ) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for ( ItemCat itemCat2 : itemCatList2 ) {
                ItemCatData id2 = new ItemCatData();
                id2.setNname(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if ( itemCat2.getIsParent() ) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for ( ItemCat itemCat3 : itemCatList3 ) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if ( result.getItemCats().size() >= 14 ) {
                break;
            }
        }
        try {
            redisService.set(KEY_NAME, MAPPER.writeValueAsString(result), KEY_TIME);
            System.out.println("写入缓存");
        } catch ( Exception e ) {
            System.out.println(KEY_NAME + "写入缓存失败");
        }
        return result;
    }

}
