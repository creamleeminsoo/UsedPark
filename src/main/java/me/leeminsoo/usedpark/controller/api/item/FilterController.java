package me.leeminsoo.usedpark.controller.api.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.service.item.AddressService;
import me.leeminsoo.usedpark.service.item.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilterController {

    private final CategoryService categoryService;
    private final AddressService addressService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/api/addresses")
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(addresses);

    }
}
