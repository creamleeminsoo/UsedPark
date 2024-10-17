package me.leeminsoo.usedpark.service.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.repository.item.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategories(){
        return categoryRepository.findAll();
    }
    public Category findCategory(Long categoryId){
       return categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("찾을수없는 카테고리입니다"));
    }
}
