package cn.xawl.manage.controller;

import cn.xawl.common.EasyUIResult;
import cn.xawl.manage.pojo.ItemParam;
import cn.xawl.manage.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("id") Long ItemCatId) {
        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(ItemCatId);
            ItemParam itemParam = itemParamService.queryOne(record);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(itemParam);
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{ItemCatId}")
    public ResponseEntity<Void> saveItemParam(ItemParam itemParam, @PathVariable("ItemCatId") Long ItemCatId) {
        try {
            itemParam.setItemCatId(ItemCatId);
            Integer save = itemParamService.save(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<EasyUIResult> queryByItemParamList(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        try {
            EasyUIResult easyUIResult = itemParamService.queryByItemParamList(page, rows);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
