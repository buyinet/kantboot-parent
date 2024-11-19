package com.kantboot.util.sc.service.impl;

import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;
import com.kantboot.util.jpa.sql.easy.util.SqlEasyUtil;
import com.kantboot.util.rest.exception.BaseException;
import com.kantboot.util.sc.service.IBaseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class BaseServiceImpl<T,ID> implements IBaseService<T,ID> {

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
    private SimpleJpaRepository<T, ID> getJpaRepository(Class<T> tClass, EntityManager entityManager) {
        return new SimpleJpaRepository<T, ID>(tClass, entityManager);
    }


    @Override
    public List<T> getAllHasCondition(ConditionGlobeEntity operatorGlobe) {
        try {
            List<T> all = getJpaRepository(getTClass(), entityManager).findAll(new SqlEasyUtil<T>().getSpecification(operatorGlobe));
            return all;
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("getAllError", "获取失败");
        }
    }

    @Override
    public List<T> getAll() {
        try {
            List<T> all = getJpaRepository(getTClass(), entityManager).findAll();
            return all;
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("getAllError", "获取失败");
        }
    }

    @Override
    public PageResult getBodyData(PageParam<ConditionGlobeEntity> pageParam) {
        try {
            PageResult pageResult = PageResult.of(
                    getJpaRepository(getTClass(), entityManager).findAll(new SqlEasyUtil<T>().getSpecification(pageParam.getData()), pageParam.getPageable()));
            return pageResult;
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("getBodyDataError", "获取失败");
        }
    }

    @Override
    public T save(T entity) {
//        return jpaAdminRepository.save(entity);
        return getJpaRepository(getTClass(), entityManager).save(entity);
    }

    @Override
    public void saveBatch(List<T> entityList) {
        getJpaRepository(getTClass(), entityManager).saveAll(entityList);
    }

    public void remove(T entity) {
//        Optional<T> byId = jpaAdminRepository.findById((ID) getId(entity));
//        if (byId.isPresent()) {
//            jpaAdminRepository.deleteById((ID) getId(entity));
//            return;
//        }
//        throw BaseException.of("deleteError", "删除失败");
        try {
            getJpaRepository(getTClass(), entityManager).findById((ID) getId(entity)).ifPresent(getJpaRepository(getTClass(), entityManager)::delete);
        }catch (Exception e){
            e.printStackTrace();
            throw BaseException.of("deleteError", "删除失败");
        }
    }


    @Override
    public void removeBatch(List<T> entityList) {
        for (T entity : entityList) {
            remove(entity);
        }
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
