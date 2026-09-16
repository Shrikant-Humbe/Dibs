package org.me.dibs.matching.service;

import org.me.dibs.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class matchingService {
    public List<Integer> getScores(){
        List<Integer> scores = new ArrayList<>();

        return scores;
    }
    // get Filtered Items
    public List<Item>  getItems(List<Integer> scores){
       List<Item> itemsClose=new ArrayList<>();
       return itemsClose;
    }
}
