package nju.cgm.kafkaspring.dao;

import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/11/2 17:38
 * @description: BaseDao
 */
public interface BaseDao<T> {

    List<T> getLimitResultByHQL(String hql, int offset, int pageSize);
}
