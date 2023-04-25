package com.commons.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yyh
 * @date 2023/4/8 22:24
 * @description
 */

@Data
@Component
@ConfigurationProperties(prefix = "cloud.safe")
public class CloudSecurityProperty {
    private boolean onlyAccessByGateway = true;

}
