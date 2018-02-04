package cn.xawl.web.service;

import cn.xawl.common.service.ApiService;
import cn.xawl.common.service.RedisService;
import cn.xawl.manage.pojo.ItemDesc;
import cn.xawl.web.bean.Item;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemService {
    @Autowired
    private ApiService apiService;
    @Value( "${SHOP_MANAGE_URL}" )
    private String SHOP_MANAGE_URL;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private RedisService redisService;
    @Value( "${redis.item.keyName}" )
    private static String KEY_NAME;
    private static final Integer REDIS_TIME = 60 * 60 * 24;

    public Item queryItemById(Long itemId) {
        String key = KEY_NAME + itemId;
        try {
            String cacheData = redisService.get(key);
            if ( !StringUtils.isEmpty(cacheData) ) {
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        String jsonData = null;
        try {
            String url = SHOP_MANAGE_URL + "/rest/api/item/" + itemId;
            jsonData = apiService.doGet(url);
            try {
                redisService.set(key, jsonData, REDIS_TIME);
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return MAPPER.readValue(jsonData, Item.class);
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public ItemDesc queryItedDescByItemId(Long itemId) {
        try {
            String url = SHOP_MANAGE_URL + "/rest/item/desc/" + itemId;
            String jsonData = apiService.doGet(url);
            return MAPPER.readValue(jsonData, ItemDesc.class);
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public String queryItemParamByItemId(Long itemId) {
        try {
            String url = SHOP_MANAGE_URL + "/rest/item/param/item/" + itemId;
            String jsonData = apiService.doGet(url);
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            String paramData = jsonNode.get("paramData").asText();
            ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);
            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
            for ( JsonNode node : arrayNode ) {
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + node.get("group").asText() + "</th></tr>");
                ArrayNode params = (ArrayNode) node.get("params");
                for ( JsonNode param : params ) {
                    sb.append("<tr><td class=\"tdTitle\">" + param.get("k").asText() + "</td><td>" + param.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            return sb.toString();
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}
