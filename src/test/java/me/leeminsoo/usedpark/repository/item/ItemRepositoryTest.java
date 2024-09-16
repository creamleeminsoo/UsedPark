package me.leeminsoo.usedpark.repository.item;

import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.item.Address;
import me.leeminsoo.usedpark.domain.item.Category;
import me.leeminsoo.usedpark.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private User user;
    private Category category;
    private Address address;

    @BeforeEach
    public void setUp() {
        // Set up test data
        user = new User("dlalstn6302@naver.com");
        category = new Category();
        address = new Address();

        // Save these entities (assuming repositories for them exist)
        // userRepository.save(user);
        // categoryRepository.save(category);
        // addressRepository.save(address);
    }

    @Test
    public void testSaveAndFindAll() {
        // Create an Item instance
        Item item = Item.builder()
                .title("Test Item")
                .brand("Test Brand")
                .price("100")
                .user(user)
                .content("Test Content")
                .category(category)
                .addresses(Collections.singletonList(address))
                .build();

        // Save the item to the database
        itemRepository.save(item);

        // Retrieve the items from the database
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Item> items = itemRepository.findAll(pageable);

        // Check that the result is not empty and the size is 1
        assertFalse(items.isEmpty());
        assertEquals(1, items.getContent().size());

        // Optionally, check that the saved item is the same as retrieved
        Item retrievedItem = items.getContent().get(0);
        assertEquals(item.getTitle(), retrievedItem.getTitle());
        assertEquals(item.getBrand(), retrievedItem.getBrand());
        assertEquals(item.getPrice(), retrievedItem.getPrice());
    }
}
