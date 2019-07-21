package me.solby.xboot.controller.vo;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * me.solby.xboot.controller.vo
 *
 * @author majhdk
 * @date 2019-07-10
 */
@Data
public class JsonDemoVO {

    private String item;
    private String name;
    private String parent;
    private Instant createdTime;
    private LocalDate today;
    private LocalDateTime now;
}
