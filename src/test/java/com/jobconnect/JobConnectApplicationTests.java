package com.jobconnect;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobConnectApplicationTests {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Disabled("Disabled until Redis is properly configured for testing")
	@Test
	void testRedisConnection() {
		redisTemplate.opsForValue().set("test-key", "Hello Redis");
		String value = redisTemplate.opsForValue().get("test-key");
		assertThat(value).isEqualTo("Hello Redis");
	}

}
