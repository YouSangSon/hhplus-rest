package yousang.rest_server.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisLockService(
    private val redisTemplate: StringRedisTemplate
) {
    fun acquireLock(lockKey: String, timeout: Long): Boolean {
        val success = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, TimeUnit.MILLISECONDS)
        return success ?: false
    }

    fun releaseLock(lockKey: String) {
        redisTemplate.delete(lockKey)
    }
}