package org.me.dibs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.me.dibs.Repository.ItemRepository;
import org.me.dibs.model.Item;
import org.me.dibs.service.ItemServiceImpl;
import org.me.dibs.service.UserService;
import org.me.dibs.service.ImageService;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {
    @Mock
    ItemRepository itemRepository;
    
    @Mock
    UserService userService;
    
    @Mock
    ImageService imageService;
    
    @InjectMocks
    ItemServiceImpl itemService;

    @Test
    void testGetItem(){
        Optional<Item> item=itemService.getItem(5);
        assertNotNull(item);
    }
    
    @Test
    void TestFoundItems(){
        List<Item> foundItems=itemService.getFoundItems();
        assertNotNull(foundItems);
    }
    
    @Test
    void TestLostItems(){
        List<Item> foundItems=itemService.getLostItems();
        assertNotNull(foundItems);
    }
    
    @Test
    void testRemoveItems(){
        Item item=itemService.getItem(5).orElse(null);
        itemService.removeItem(5);
        Item item1=itemService.getItem(5).orElse(null);
        assertThat(item).isEqualTo(null);
    }

    @Test
    void testGetItemsBytime() {
        Item item1 = new Item();
        item1.setIsLost(false);
        item1.setTime(LocalDate.now().minusDays(2).toString()); // 2 days ago

        Item item2 = new Item();
        item2.setIsLost(false);
        item2.setTime(LocalDate.now().minusDays(10).toString()); // 10 days ago

        Item item3 = new Item();
        item3.setIsLost(false);
        item3.setTime("invalid-date");

        Item item4 = new Item();
        item4.setIsLost(false);
        item4.setTime(null);

        Mockito.when(itemRepository.findByIsLostFalse()).thenReturn(List.of(item1, item2, item3, item4));

        List<Item> result = itemService.getItemsBytime(5);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTime()).isEqualTo(item1.getTime());
    }
}