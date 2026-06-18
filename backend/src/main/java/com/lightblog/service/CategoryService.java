package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.Category;
import com.lightblog.mapper.CategoryMapper;
import com.lightblog.model.request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<Category> listMine() {
        return categoryMapper.listByUser(AuthContext.userId());
    }

    public Category create(CategoryRequest request) {
        Category category = new Category();
        category.name = request.name.trim();
        category.userId = AuthContext.userId();
        categoryMapper.insert(category);
        return categoryMapper.selectById(category.id);
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = new Category();
        category.id = id;
        category.name = request.name.trim();
        category.userId = AuthContext.userId();
        if (categoryMapper.update(category) == 0) {
            throw new BusinessException(404, "分类不存在或无权修改", 404);
        }
        return categoryMapper.selectById(id);
    }

    public void delete(Long id) {
        if (categoryMapper.deleteById(id, AuthContext.userId()) == 0) {
            throw new BusinessException(404, "分类不存在或无权删除", 404);
        }
    }
}
