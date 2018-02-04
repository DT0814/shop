package cn.xawl.web.controller;

import cn.xawl.manage.pojo.ItemDesc;
import cn.xawl.web.bean.Item;
import cn.xawl.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping( "/item" )
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping( "/{itemId}" )
    public ModelAndView showDetail(@PathVariable( "itemId" ) Long itemId) {
        ModelAndView md = new ModelAndView("item");

        //商品基本数据
        Item item = itemService.queryItemById(itemId);
        md.addObject("item", item);
        //商品描述数据
        ItemDesc itemDesc = itemService.queryItedDescByItemId(itemId);
        md.addObject("itemDesc", itemDesc);
        //规格参数数据
        String itemParam = itemService.queryItemParamByItemId(itemId);
        md.addObject("itemParam", itemParam);
        return md;
    }
}
