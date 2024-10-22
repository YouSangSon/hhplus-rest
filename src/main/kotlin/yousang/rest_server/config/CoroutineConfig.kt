package yousang.rest_server.config

import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Configuration
class CoroutineConfig {
    @Bean
    fun virtualThreadCoroutineDispatcher(): CoroutineContext {
        return Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
    }
}