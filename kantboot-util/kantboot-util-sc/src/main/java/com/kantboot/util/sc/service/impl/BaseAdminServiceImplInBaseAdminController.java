package com.kantboot.util.sc.service.impl;

import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;
import com.kantboot.util.jpa.sql.easy.repository.ZeusJpaRepository;
import com.kantboot.util.jpa.sql.easy.util.SqlEasyUtil;
import com.kantboot.util.rest.exception.BaseException;
import com.kantboot.util.sc.service.IBaseAdminServiceInBaseAdminController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class BaseAdminServiceImplInBaseAdminController<T extends Serializable, ID> implements IBaseAdminServiceInBaseAdminController<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 获取第一个泛型的类型
     */
    private Class<T> getTClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * 获取JpaRepository
     */
    private ZeusJpaRepository<T, ID> getJpaRepository(Class<T> tClass, EntityManager entityManager) {
        return new ZeusJpaRepository<>(
                JpaEntityInformationSupport.getEntityInformation(tClass, entityManager), entityManager);
    }

    @Override
    public List<T> getAll(ConditionGlobeEntity operatorGlobe, Class<T> tClass) {
        try {
            List<T> all = getJpaRepository(tClass, entityManager).findAll(new SqlEasyUtil<T>().getSpecification(operatorGlobe));
            return all;
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("getAllError", "获取失败");
        }
    }

    @Override
    public PageResult getBodyData(PageParam<ConditionGlobeEntity> pageParam, Class<T> tClass) {
        try {
            PageResult pageResult = PageResult.of(
                    getJpaRepository(tClass, entityManager).findAll(new SqlEasyUtil<T>().getSpecification(pageParam.getData()), pageParam.getPageable()));
            return pageResult;
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("getBodyDataError", "获取失败");
        }
    }

    @Transactional
    @Modifying
    @Override
    public T save(T entity, Class<T> tClass) {
        return getJpaRepository(tClass, entityManager).save(entity);
    }

    @Transactional
    @Modifying
    @Override
    public void saveBatch(List<T> entityList, Class<T> tClass) {
       getJpaRepository(tClass, entityManager).saveAll(entityList);
    }

    @Transactional
    @Modifying
    @Override
    public void remove(T entity, Class<T> tClass) {
        try {
            ID id = (ID) getId(entity);
            Optional<T> byId = getJpaRepository(tClass, entityManager).findById(id);
            if (byId.isPresent()) {
                getJpaRepository(tClass, entityManager).deleteById(id);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("deleteError", "删除失败");
        }
    }

    @Transactional
    @Modifying
    @Override
    public void removeBatch(List<T> entityList, Class<T> tClass) {
        for (T entity : entityList) {
            remove(entity, tClass);
        }
    }

    @Override
    public T getById(ID id, Class<T> tClass) {
        return getJpaRepository(tClass, entityManager).findById(id).orElse(null);
    }

    public Object getId(Object object) {
        // 从Class对象中获取Demo中声明方法对应的Method对象
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断方法是否被加上了@Autowired这个注解
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
