package org.me.dibs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.me.dibs.constants.UserRoleConstant;
import org.me.dibs.model.Item;
import org.me.dibs.model.UserDetail;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private  Integer id;
    private  String username;
    private  String password;
    private List<Item> items;
    private List<Item>  claimedItems;
    private UserDetail userDetail;
    private String role;

}
