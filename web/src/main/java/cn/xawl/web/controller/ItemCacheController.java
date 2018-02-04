package cn.xawl.web.controller;

import cn.xawl.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/item/cache" )
public class ItemCacheController {
    @Value( "${redis.item.keyName}" )
    private static String KEY_NAME;
    @Autowired
    private RedisService redisService;

    @PostMapping( "/{itemId}" )
    public ResponseEntity<Void> deleteCache(@PathVariable( "itemId" ) Long itemId) {
        try {
            String key = KEY_NAME + itemId;
            redisService.del(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
