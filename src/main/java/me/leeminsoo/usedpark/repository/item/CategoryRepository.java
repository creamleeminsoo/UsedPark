package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
