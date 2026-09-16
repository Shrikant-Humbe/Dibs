package org.me.dibs.matching.service;

import org.me.dibs.Repository.ItemRepository;
import org.me.dibs.model.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
public class FilterItems {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    Coordinates_to_Distance coordinates_to_Distance;
    public List<Item> getItemsByDistance(Item item,Integer distance){
        List<Item> items=itemRepository.findByIsLostFalse();
        List<Item>items1=new ArrayList<>();
        for(Item i:items){
            double d1=coordinates_to_Distance.calculateDistance(
                    (double) item.getLatitude(),
                    (double)item.getLatitude(),
                    (double)i.getLatitude(),
                    (double)i.getLongitude(),
                    false
            );
            if(d1<distance){
                items1.add(i);
            }
        }
        return items1;
    }
}
