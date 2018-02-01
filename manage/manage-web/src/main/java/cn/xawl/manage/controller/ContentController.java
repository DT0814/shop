package cn.xawl.manage.controller;

import cn.xawl.common.EasyUIResult;
import cn.xawl.manage.pojo.Content;
import cn.xawl.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping( "/content" )
public class ContentController {
    @Autowired
    private ContentService contentService;

    @PostMapping
    public ResponseEntity<Void> save(Content content) {
        try {
            content.setId(null);
            contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    public ResponseEntity<EasyUIResult> queryByCategoryId(@RequestParam( "categoryId" ) Long categoryId,
                                                          @RequestParam( value = "page", defaultValue = "1" ) Integer page,
                                                          @RequestParam( value = "rows", defaultValue = "10" ) Integer rows) {
        try {
            EasyUIResult easyUIResultp = contentService.queryListByCategoryId(categoryId, page, rows);

            return ResponseEntity.ok(easyUIResultp);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
