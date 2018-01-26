package cn.xawl.manage.controller;

import cn.xawl.manage.pojo.ItemCat;
import cn.xawl.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itenCatService;

    @GetMapping()
    public ResponseEntity<List<ItemCat>> queryItemListByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> list = itenCatService.queryListByWhere(itemCat);
            if (null == list) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
