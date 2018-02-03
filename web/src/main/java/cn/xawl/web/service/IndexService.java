package cn.xawl.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {
    @Autowired
    private ApiService apiService;

    @Value( "${SHOP_MANAGE_URL}" )
    private String SHOP_MANAGE_URL;

    @Value( "${INDEXAD1}" )
    private String INDEXAD1;
    @Value( "${INDEXAD2}" )
    private String INDEXAD2;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD2() {
        try {
            String url = SHOP_MANAGE_URL + INDEXAD2;
            String jsonData = apiService.doGet(url);
            if ( StringUtils.isEmpty(jsonData) ) {
                return null;
            }
            //解析json成前端所需数据
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for ( JsonNode row : rows ) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("width", 310);
                map.put("height", 70);
                map.put("src", row.get("pic").asText());
                map.put("href", row.get("url").asText());
                map.put("alt", row.get("title").asText());
                map.put("widthB", 210);
                map.put("heightB", 70);
                map.put("srcB", row.get("pic").asText());
                list.add(map);
            }
            return MAPPER.writeValueAsString(list);
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }

    }

    public String queryIndexAD1() {
        try {
            String url = SHOP_MANAGE_URL + INDEXAD1;
            String jsonData = apiService.doGet(url);
            if ( StringUtils.isEmpty(jsonData) ) {
                return null;
            }
            //解析json成前端所需数据
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for ( JsonNode row : rows ) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("srcB", row.get("pic").asText());
                map.put("height", 240);
                map.put("alt", row.get("title").asText());
                map.put("width", 670);
                map.put("src", row.get("pic").asText());
                map.put("widthB", 550);
                map.put("href", row.get("url").asText());
                map.put("heightB", 240);
                list.add(map);
            }
            return MAPPER.writeValueAsString(list);
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}
