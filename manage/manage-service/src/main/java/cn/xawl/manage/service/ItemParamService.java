package cn.xawl.manage.service;

import cn.xawl.common.EasyUIResult;
import cn.xawl.manage.dao.ItemParamMapper;
import cn.xawl.manage.pojo.ItemParam;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamService extends BaseService<ItemParam> {
    @Autowired
    private ItemParamMapper itemParamMapper;

    public EasyUIResult queryByItemParamList(Integer page, Integer rows) {


        Example example = new Example(ItemParam.class);
        example.setOrderByClause("updated DESC");
        PageHelper.startPage(page, rows);
        List<ItemParam> list = this.itemParamMapper.selectByExample(example);
        PageInfo<ItemParam> pageInfo = new PageInfo<ItemParam>(list);
        EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
        return easyUIResult;
    }
}
