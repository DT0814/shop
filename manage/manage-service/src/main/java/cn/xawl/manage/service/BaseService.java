package cn.xawl.manage.service;

import cn.xawl.manage.pojo.BasePojo;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public abstract class BaseService<T extends BasePojo> {


    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据主键查询一条数据
     *
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查所有
     *
     * @return
     */
    public List<T> queryAll() {
        return mapper.select(null);
    }

    /**
     * 根据条件查询一条数据
     *
     * @param t
     * @return
     */
    public T queryOne(T t) {
        return mapper.selectOne(t);
    }

    /**
     * 根据条件查询多条数据
     *
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t) {
        return mapper.select(t);
    }

    /**
     * 分页查询
     *
     * @param t
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryPageListByWhere(T t, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> select = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<T>(select);
        return pageInfo;
    }

    /**
     * 添加一条数据
     *
     * @param t
     * @return
     */
    public Integer save(T t) {
        Date d = new Date();
        t.setCreated(d);
        t.setUpdated(d);
        return mapper.insert(t);
    }

    /**
     * 选择插入一条数据
     *
     * @param t
     * @return
     */
    public Integer saveSelective(T t) {
        return mapper.insertSelective(t);
    }

    /**
     * 更新数据
     *
     * @param t
     * @return
     */
    public Integer update(T t) {
        t.setUpdated(new Date());
        return mapper.updateByPrimaryKey(t);
    }

    /**
     * 根据主键部分更新数据
     *
     * @param t
     * @return
     */
    public Integer updateSelective(T t) {
        t.setUpdated(new Date());
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    public Integer deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param clazz
     * @param property
     * @param ids
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> ids) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, ids);
        return mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除
     *
     * @param t
     * @return
     */
    public Integer deleteByWhere(T t) {
        return mapper.delete(t);
    }


}
