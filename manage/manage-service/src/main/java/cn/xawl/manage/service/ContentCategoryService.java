package cn.xawl.manage.service;

import cn.xawl.manage.dao.ContentCategoryMapper;
import cn.xawl.manage.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    public boolean saveT(ContentCategory contentCategory) {
        try {
            this.save(contentCategory);
            ContentCategory parent = this.queryById(contentCategory.getParentId());
            if (!parent.getIsParent()) {
                parent.setIsParent(true);
                this.updateSelective(parent);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
