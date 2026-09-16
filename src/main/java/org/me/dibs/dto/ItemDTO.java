package org.me.dibs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Integer id;
    private String name;
    private String description;
    private String location;
    private String contact;
    private String time;
    private Boolean isLost;
    private Boolean isClaimed;
    private Integer userId;
    private Integer claimedById;
}
