package com.soft2242.shop.common.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource("classpath:aliyun.properties")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunResource {
    private String accessKeyId = "LTAI5tFvNca29VH924Pvfbuv";
    private String accessKeySecret =
            "sBk9H1kpLan4lpJi1DBEgiawOCwMGW";
}
