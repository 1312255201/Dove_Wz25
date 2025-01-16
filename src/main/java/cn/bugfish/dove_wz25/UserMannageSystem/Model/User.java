package cn.bugfish.dove_wz25.UserMannageSystem.Model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String role;
    private String steam64id;
    private String qqnumber;
    private String email;
    private String password;
    private Timestamp created_at;
}
