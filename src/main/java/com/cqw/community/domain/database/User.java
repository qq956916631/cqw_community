package com.cqw.community.domain.database;

import lombok.Data;

@Data
public class User {
    private String account_id;
    private String name;
    private String token;
    private long gmt_create;
    private long gmt_modified;
}
