package com.example.complexdemo;

import com.example.complexdemo.config.RedisConfig;
import com.example.complexdemo.model.User;
import com.example.complexdemo.repository.UserRepository;
import com.example.complexdemo.service.CacheService;
import com.example.complexdemo.service.RedisService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ComplexDemoApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
        testRepositoryOperations(context);
        testTemplateOperations(context);
        testAdvancedOperations(context);
        testCacheOperations(context);
        context.close();
    }

    private static void testRepositoryOperations(AnnotationConfigApplicationContext context) {
        System.out.println("1. Testing Repository Operations (CRUD + Custom Finders):");
        UserRepository userRepository = context.getBean(UserRepository.class);

        // Create users
        User user1 = new User("1", "John Doe", "john@example.com", 30);
        User user2 = new User("2", "Jane Smith", "jane@example.com", 25);
        User user3 = new User("3", "Bob Johnson", "bob@example.com", 35);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        System.out.println("✓ Saved 3 users");

        // Read operations
        System.out.println("✓ All users: " + userRepository.count());
        userRepository.findAll().forEach(System.out::println);

        // Custom finders
        List<User> johnUsers = userRepository.findByName("John Doe");
        System.out.println("✓ Users named John: " + johnUsers);

        List<User> janeUsers = userRepository.findByEmail("jane@example.com");
        System.out.println("✓ Users with email jane@example.com: " + janeUsers);

        // Update
        user1.setAge(31);
        userRepository.save(user1);
        System.out.println("✓ Updated user age");

        // Delete
        userRepository.deleteById("3");
        System.out.println("✓ Deleted user with ID 3");
        System.out.println();
    }

    private static void testTemplateOperations(AnnotationConfigApplicationContext context) {
        System.out.println("2. Testing Template Operations (All Data Types):");
        RedisService redisService = context.getBean(RedisService.class);

        // String operations
        redisService.setString("string:key", "Hello Redis!");
        System.out.println("✓ String value: " + redisService.getString("string:key"));

        // Hash operations
        redisService.setHash("hash:user", "name", "Alice");
        redisService.setHash("hash:user", "age", 28);
        System.out.println("✓ Hash field 'name': " + redisService.getHashField("hash:user", "name"));
        System.out.println("✓ Full hash: " + redisService.getHash("hash:user"));

        // List operations
        redisService.pushToList("list:items", "item1");
        redisService.pushToList("list:items", "item2");
        redisService.pushToList("list:items", "item3");
        System.out.println("✓ List items: " + redisService.getList("list:items"));
        System.out.println("✓ Popped item: " + redisService.popFromList("list:items"));

        // Set operations
        redisService.addToSet("set:tags", "redis");
        redisService.addToSet("set:tags", "spring");
        redisService.addToSet("set:tags", "java");
        System.out.println("✓ Set members: " + redisService.getSet("set:tags"));

        // Sorted Set operations
        redisService.addToSortedSet("zset:scores", "player1", 100.0);
        redisService.addToSortedSet("zset:scores", "player2", 85.0);
        redisService.addToSortedSet("zset:scores", "player3", 95.0);
        System.out.println("✓ Sorted set (by score): " + redisService.getSortedSet("zset:scores"));

        // Pipeline operations
        redisService.pipelineOperations();
        System.out.println("✓ Pipeline operations completed");

        // Transaction operations
        redisService.transactionOperations();
        System.out.println("✓ Transaction operations completed");
        System.out.println();
    }

    private static void testAdvancedOperations(AnnotationConfigApplicationContext context) {
        System.out.println("3. Testing Advanced Operations:");
        RedisService redisService = context.getBean(RedisService.class);

        // Atomic counters
        Long count1 = redisService.incrementCounter("counter:visits");
        Long count2 = redisService.incrementCounter("counter:visits");
        Long count3 = redisService.incrementCounterBy("counter:visits", 5);
        System.out.println("✓ Counter increments: " + count1 + ", " + count2 + ", " + count3);

        Long decCount = redisService.decrementCounter("counter:visits");
        System.out.println("✓ Counter after decrement: " + decCount);

        // TTL operations
        redisService.setWithExpiration("temp:key", "expires in 5 seconds", 5);
        Long ttl = redisService.getTimeToLive("temp:key");
        System.out.println("✓ TTL for temp:key: " + ttl + " seconds");

        // Bound operations
        redisService.boundListOperations("bound:list");
        System.out.println("✓ Bound list items: " + redisService.getBoundList("bound:list"));
        
        redisService.boundHashOperations("bound:hash");
        System.out.println("✓ Bound hash entries: " + redisService.getBoundHash("bound:hash"));

        // Custom XML serialization
        User xmlUser = new User("xml1", "XML User", "xml@example.com", 40);
        redisService.setWithXmlSerialization("xml:user", xmlUser);
        String xmlData = redisService.getXmlSerialized("xml:user");
        System.out.println("✓ XML serialized user: " + xmlData);
        System.out.println();
    }

    private static void testCacheOperations(AnnotationConfigApplicationContext context) {
        System.out.println("4. Testing Cache Operations:");
        CacheService cacheService = context.getBean(CacheService.class);

        // First call - should take time
        long start = System.currentTimeMillis();
        String result1 = cacheService.getUserData("user123");
        long duration1 = System.currentTimeMillis() - start;
        System.out.println("✓ First call (uncached): " + result1 + " - took " + duration1 + "ms");

        // Second call - should be fast (cached)
        start = System.currentTimeMillis();
        String result2 = cacheService.getUserData("user123");
        long duration2 = System.currentTimeMillis() - start;
        System.out.println("✓ Second call (cached): " + result2 + " - took " + duration2 + "ms");

        // Evict cache
        cacheService.evictUserCache("user123");

        // Manual cache operations
        cacheService.manualCacheOperations();
        System.out.println();
    }

}
