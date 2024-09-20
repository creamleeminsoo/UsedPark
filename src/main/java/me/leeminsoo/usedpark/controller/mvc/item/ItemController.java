package me.leeminsoo.usedpark.controller.mvc.item;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.domain.item.Item;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.dto.item.ItemListResponseDTO;
import me.leeminsoo.usedpark.dto.item.ItemResponseDTO;
import me.leeminsoo.usedpark.service.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    public String getItems(@RequestParam(name = "order",defaultValue = "desc") String order,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "10") int size,
                           @RequestParam(name = "keyword",required = false) String keyword,
                           @RequestParam(name = "address",required = false) Long addressId,
                           @RequestParam(name = "category",required = false) Long categoryId,
                           Model model){
        Page<ItemListResponseDTO> items;
        if(keyword != null){
            items = itemService.searchItems(order,page,size,keyword);
        }else {
            items = itemService.getItems(order,page,size,addressId,categoryId);
        }

        model.addAttribute("items",items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("totalItems", items.getTotalElements());
        model.addAttribute("order", order);
        model.addAttribute("size", size);
        return "item/itemList";
    }
    @GetMapping("/new-item")
    public String newItem(@RequestParam(name = "itemId",required = false)Long itemId ,Model model) {
        if (itemId == null){
            model.addAttribute("item",new ItemResponseDTO());
        }else {
            Item item = itemService.findById(itemId);
            model.addAttribute("item",item);
        }
        return "item/new-item";
    }
    @GetMapping("/items/{itemId}")
    public String getItem(@PathVariable(name = "itemId") Long itemId, Model model) {
        ItemResponseDTO item = itemService.getItem(itemId);
        model.addAttribute("item",item);
        return "item/item";
    }

}
