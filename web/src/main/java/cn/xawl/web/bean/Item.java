package cn.xawl.web.bean;

import org.springframework.util.StringUtils;

public class Item extends cn.xawl.manage.pojo.Item {
    public String[] getImages() {
        return StringUtils.split(super.getImage(), ",");
    }
}
