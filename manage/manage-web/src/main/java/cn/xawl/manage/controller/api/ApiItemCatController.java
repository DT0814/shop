package cn.xawl.manage.controller.api;

import cn.xawl.common.ItemCatResult;
import cn.xawl.manage.service.ItemCatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GetMapping
    public ResponseEntity<String> quertitemCat(@RequestParam("callback") String callback) {
        try {
            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
            String result = callback + "(" + MAPPER.writeValueAsString(itemCatResult) + ");";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
