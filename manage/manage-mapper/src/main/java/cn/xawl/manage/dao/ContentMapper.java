package cn.xawl.manage.dao;


import cn.xawl.manage.pojo.Content;
import com.github.abel533.mapper.Mapper;

import java.util.List;

public interface ContentMapper extends Mapper<Content> {
    List<Content> queryListByCategoryId(Long categoryId);
}
