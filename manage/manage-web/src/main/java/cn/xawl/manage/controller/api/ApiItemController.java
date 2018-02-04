package cn.xawl.manage.controller.api;

import cn.xawl.manage.pojo.Item;
import cn.xawl.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/api/item" )
public class ApiItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping( "/{itemId}" )
    public ResponseEntity<Item> queryById(@PathVariable( "itemId" ) Long itemId) {
        try {
            Item item = itemService.queryById(itemId);
            if ( item == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            System.out.println(item);
            return ResponseEntity.ok(item);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
