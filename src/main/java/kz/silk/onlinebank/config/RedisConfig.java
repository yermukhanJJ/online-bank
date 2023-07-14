package kz.silk.onlinebank.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * Redis config class
 *
 * @author YermukhanJJ
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClientFactory(LettuceConnectionFactory lettuceConnectionFactory) {
        Config config = new Config();
        String address = "redis://" + lettuceConnectionFactory.getHostName() + ":"
                + lettuceConnectionFactory.getPort();
        config.useSingleServer()
                .setAddress(address)
                .setPassword(lettuceConnectionFactory.getPassword());
        return Redisson.create(config);
    }
}
