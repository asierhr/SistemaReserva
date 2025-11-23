package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.servicies.DistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistributedLockServiceImpl implements DistributedLockService {
    private final RedissonClient redissonClient;

    private static final long LOCK_WAIT_TIME = 10;
    private static final long LOCK_LEASE_TIME = 30;

    @Override
    public <T> T executeWithMultiLock(List<String> lockKeys, Supplier<T> action) {
        RLock[] locks = lockKeys.stream().map(redissonClient::getLock).toArray(RLock[]::new);

        RLock multilock = redissonClient.getMultiLock(locks);
        boolean acquired = false;

        try{
            acquired = multilock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
            if (!acquired) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Some seats are being reserved by another user");
            }
            return action.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Lock acquisition interrupted");
        }finally {
            if (acquired) {
                multilock.unlock();
            }
        }
    }
}
