package cn.xawl.manage.service.utils;


public interface Function<T, E> {
    public T callback(E e);
}
