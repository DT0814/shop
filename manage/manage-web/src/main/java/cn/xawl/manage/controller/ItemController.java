package cn.xawl.manage.controller;

import cn.xawl.common.EasyUIResult;
import cn.xawl.manage.pojo.Item;
import cn.xawl.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestParam("itemParams") String itemParams, Item item, @RequestParam("desc") String desc) {
        try {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("参数,Item={},desc{}", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean b = itemService.saveItem(item, desc, itemParams);

            if (b) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("新增商品成功ItemId={}", item.getId());
                }
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("新增商品失败");
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            LOGGER.error("程序出错");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            EasyUIResult easyUIResult = this.itemService.queryList(page, rows);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateItem(@RequestParam("itemParams") String itemParams, Item item, @RequestParam("desc") String desc) {
        try {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("修改商品,Item={},desc{}", item, desc);
            }

            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean b = itemService.updateItem(item, desc,itemParams);

            if (b) {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("修改商品成功ItemId={}", item.getId());
                }
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("修改商品失败");
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            LOGGER.error("程序出错");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
