package kz.silk.onlinebank.service.impl;

import kz.silk.onlinebank.service.RedisService;
import lombok.Getter;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Implements {@link RedisService} service
 *
 * @author YermukhanJJ
 */
@Getter
@Primary
@Service(value = RedisServiceImpl.SERVICE_VALUE)
public class RedisServiceImpl implements RedisService {

    public static final String SERVICE_VALUE = "RedisServiceImpl";

    private final RList<String> accountNumbers;

    public RedisServiceImpl(RedissonClient redissonClient) {
        this.accountNumbers = redissonClient.getList("account_numbers");
    }
}
