package kz.silk.onlinebank.service;

import org.redisson.api.RList;
import org.springframework.stereotype.Service;

/**
 * Redis data service
 *
 * @author YermukhanJJ
 */
@Service
public interface RedisService {

    /**
     * Returns a bank account number
     *
     * @return {@link RList} account number
     */
    RList<String> getAccountNumbers();
}
