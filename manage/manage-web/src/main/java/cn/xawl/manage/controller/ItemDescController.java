package cn.xawl.manage.controller;

import cn.xawl.manage.pojo.ItemDesc;
import cn.xawl.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDesc> queryBtItemId(@PathVariable("itemId") Long itemId) {
        try {
            ItemDesc itemDesc = itemDescService.queryById(itemId);

            if (null == itemDesc) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

            } else {
                return ResponseEntity.ok(itemDesc);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
