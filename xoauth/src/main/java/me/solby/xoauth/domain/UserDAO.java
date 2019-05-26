package me.solby.xoauth.domain;

import me.solby.xoauth.domain.entity.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    private List<UserDO> users = List.of(
            new UserDO("001", "user001", "001"),
            new UserDO("002", "user002", "002"),
            new UserDO("003", "user003", "003")
    );

    public List<UserDO> all() {
        return users;
    }

    public UserDO findByUserName(String userName) {
        return users.stream()
                .filter(userDO -> userDO.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }

}
