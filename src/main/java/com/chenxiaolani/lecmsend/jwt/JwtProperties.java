package com.chenxiaolani.lecmsend.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Data
//@ConfigurationProperties(prefix = "le.jwt")
@Repository // 加上这个，解决依赖注入的红色警告
public class JwtProperties {
    private String secretKey = "le.key";
    private long validityInMs = 3600_000;
}
