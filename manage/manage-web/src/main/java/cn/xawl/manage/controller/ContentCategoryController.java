package cn.xawl.manage.controller;

import cn.xawl.manage.pojo.ContentCategory;
import cn.xawl.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;


    @GetMapping()
    public ResponseEntity<List<ContentCategory>> queryByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            ContentCategory record = new ContentCategory();
            record.setParentId(parentId);
            List<ContentCategory> contentCategories = contentCategoryService.queryListByWhere(record);
            if (null == contentCategories) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(contentCategories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
        try {
            contentCategory.setId(null);
            contentCategory.setIsParent(false);
            contentCategory.setSortOrder(1);
            contentCategory.setStatus(1);
            boolean b = contentCategoryService.saveT(contentCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<Void> updataName(ContentCategory contentCategory) {
        try {
            contentCategoryService.updateSelective(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(ContentCategory contentCategory) {
        try {

            List<Object> ids = new ArrayList<Object>();
            ids.add(contentCategory.getId());
            //递归找本节点下所有子节点
            findAllSubNode(contentCategory.getParentId(), ids);
            contentCategoryService.deleteByIds(ContentCategory.class, "id", ids);
            ContentCategory recode = new ContentCategory();
            recode.setParentId(contentCategory.getParentId());
            List<ContentCategory> categorys = contentCategoryService.queryListByWhere(recode);
            if (categorys == null || categorys.size() <= 0) {
                ContentCategory parent = new ContentCategory();
                parent.setId(contentCategory.getParentId());
                parent.setIsParent(false);
                contentCategoryService.updateSelective(parent);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void findAllSubNode(Long parentId, List<Object> ids) {
        ContentCategory recode = new ContentCategory();
        recode.setParentId(parentId);
        List<ContentCategory> list = contentCategoryService.queryListByWhere(recode);
        list.stream().forEach((category) -> {
            ids.add(category.getId());
            if (category.getIsParent()) {
                findAllSubNode(category.getId(), ids);
            }
        });
    }
}
