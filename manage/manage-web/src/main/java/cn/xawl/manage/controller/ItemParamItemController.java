package cn.xawl.manage.controller;

import cn.xawl.manage.pojo.ItemParamItem;
import cn.xawl.manage.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemParamItem> queryById(@PathVariable("itemId") Long itemId) {
        try {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(itemId);
            ItemParamItem itemParamItem1 = itemParamItemService.queryOne(itemParamItem);
            if (itemParamItem1 == null) {
                ResponseEntity.status(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(itemParamItem1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
