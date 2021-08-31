### 源码分析
```java
public class RedisAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
  // 默认的RedisTemplate没有过多的设置，redis对象都是需要序列化
  // 两个泛型都是Object类型  ，我们一般会强制转换为 <String,Object>
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

}
```

步骤：  
1. 导入依赖
```xml
<!-- 操作redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
2. 配置连接
```yml
# 配置redis
spring:
  redis:
    host: 123.57.8.252
    port: 6379
```
3. 测试
```java
@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        // redisTemplate  操作不同的数据类型
        // opsForValue  操作字符串  类似String
        // opsForList   操作字符串  类似List
        // opsForXxx    类似Xxx
        redisTemplate.opsForValue().set("name","千珏");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);


        // 除了基本操作，常用的方法都可以直接通过redisTemplate操作，比如事务
        // redisTemplate.multi();
//        redisTemplate.multi();

        //获取redis连接对象，操作数据库，比如flushDb
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();

    }

}
```


## 自定义RedisTemplate
```java
//编写我们自己的redisTemplate
@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //Json序列化配置
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectJackson2JsonRedisSerializer.setObjectMapper(om);
        //String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();



        // 配置具体的序列化方式
        // key采用string的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash也采用string的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        // hashd的value序列化方式采用jackson
        template.setHashKeySerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();


        return template;
    }
}
```
