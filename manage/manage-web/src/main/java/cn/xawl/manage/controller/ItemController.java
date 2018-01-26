package cn.xawl.manage.controller;

import cn.xawl.manage.pojo.Item;
import cn.xawl.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @PostMapping
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc) {
        try {
            if (item.getTitle() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean b = itemService.saveItem(item, desc);
            if (b) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
