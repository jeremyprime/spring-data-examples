#!/bin/bash

# Simple migration script from Spring Data Redis to Spring Data Valkey
# Usage: ./migrate-to-valkey.sh [project-directory] [valkey-version]

PROJECT_DIR="${1:-.}"
SPRING_DATA_VALKEY_VERSION="${2:-3.5.1}"

echo "Migrating Spring Data Redis to Spring Data Valkey in: $PROJECT_DIR"

# Update pom.xml dependencies
find "$PROJECT_DIR" -name "pom.xml" -type f | while read pom; do
    echo "Updating $pom"
    sed -i.bak \
        -e 's|<groupId>org\.springframework\.data</groupId>|<groupId>io.valkey.springframework.data</groupId>|g' \
        -e 's|<artifactId>spring-data-redis</artifactId>|<artifactId>spring-data-valkey</artifactId>|g' \
        -e '/<artifactId>spring-data-valkey<\/artifactId>/,/<\/dependency>/ s|<version>[^<]*</version>|<version>'"$SPRING_DATA_VALKEY_VERSION"'</version>|' \
        -e 's|redis\.server\.version|valkey.server.version|g' \
        "$pom"
done

# Update Java imports
find "$PROJECT_DIR/src" -name "*.java" -type f | while read java_file; do
    echo "Updating $java_file"
    sed -i.bak \
        -e 's|org\.springframework\.data\.redis|io.valkey.springframework.data.valkey|g' \
        -e 's|AbstractRedisConnection|AbstractValkeyConnection|g' \
        -e 's|DecoratedRedisConnection|DecoratedValkeyConnection|g' \
        -e 's|DefaultStringRedisConnection|DefaultStringValkeyConnection|g' \
        -e 's|GenericJackson2JsonRedisSerializer|GenericJackson2JsonValkeySerializer|g' \
        -e 's|Jackson2JsonRedisSerializer|Jackson2JsonValkeySerializer|g' \
        -e 's|JdkSerializationRedisSerializer|JdkSerializationValkeySerializer|g' \
        -e 's|ReactiveRedisConnection|ReactiveValkeyConnection|g' \
        -e 's|ReactiveRedisConnectionFactory|ReactiveValkeyConnectionFactory|g' \
        -e 's|ReactiveRedisOperations|ReactiveValkeyOperations|g' \
        -e 's|ReactiveRedisTemplate|ReactiveValkeyTemplate|g' \
        -e 's|RedisCache|ValkeyCache|g' \
        -e 's|RedisCacheConfiguration|ValkeyCacheConfiguration|g' \
        -e 's|RedisCacheManager|ValkeyCacheManager|g' \
        -e 's|RedisCacheWriter|ValkeyCacheWriter|g' \
        -e 's|RedisCallback|ValkeyCallback|g' \
        -e 's|RedisClientInfo|ValkeyClientInfo|g' \
        -e 's|RedisClusterCommands|ValkeyClusterCommands|g' \
        -e 's|RedisClusterConfiguration|ValkeyClusterConfiguration|g' \
        -e 's|RedisClusterConnection|ValkeyClusterConnection|g' \
        -e 's|RedisClusterNode|ValkeyClusterNode|g' \
        -e 's|RedisCommands|ValkeyCommands|g' \
        -e 's|RedisConnection|ValkeyConnection|g' \
        -e 's|RedisConnectionCommands|ValkeyConnectionCommands|g' \
        -e 's|RedisConnectionFactory|ValkeyConnectionFactory|g' \
        -e 's|RedisConnectionFailureException|ValkeyConnectionFailureException|g' \
        -e 's|RedisConverter|ValkeyConverter|g' \
        -e 's|RedisCustomConversions|ValkeyCustomConversions|g' \
        -e 's|RedisGeoCommands|ValkeyGeoCommands|g' \
        -e 's|RedisHash|ValkeyHash|g' \
        -e 's|RedisHashCommands|ValkeyHashCommands|g' \
        -e 's|RedisHyperLogLogCommands|ValkeyHyperLogLogCommands|g' \
        -e 's|RedisKeyCommands|ValkeyKeyCommands|g' \
        -e 's|RedisKeyValueAdapter|ValkeyKeyValueAdapter|g' \
        -e 's|RedisKeyValueTemplate|ValkeyKeyValueTemplate|g' \
        -e 's|RedisListCommands|ValkeyListCommands|g' \
        -e 's|RedisMappingContext|ValkeyMappingContext|g' \
        -e 's|RedisMessageListener|ValkeyMessageListener|g' \
        -e 's|RedisMessageListenerContainer|ValkeyMessageListenerContainer|g' \
        -e 's|RedisNode|ValkeyNode|g' \
        -e 's|RedisOperations|ValkeyOperations|g' \
        -e 's|RedisPartTreeQuery|ValkeyPartTreeQuery|g' \
        -e 's|RedisPubSubCommands|ValkeyPubSubCommands|g' \
        -e 's|RedisQueryCreator|ValkeyQueryCreator|g' \
        -e 's|RedisRepository|ValkeyRepository|g' \
        -e 's|RedisRepositoryFactoryBean|ValkeyRepositoryFactoryBean|g' \
        -e 's|RedisScriptingCommands|ValkeyScriptingCommands|g' \
        -e 's|RedisSentinelCommands|ValkeySentinelCommands|g' \
        -e 's|RedisSentinelConfiguration|ValkeySentinelConfiguration|g' \
        -e 's|RedisSentinelConnection|ValkeySentinelConnection|g' \
        -e 's|RedisSerializationContext|ValkeySerializationContext|g' \
        -e 's|RedisSerializer|ValkeySerializer|g' \
        -e 's|RedisServerCommands|ValkeyServerCommands|g' \
        -e 's|RedisSetCommands|ValkeySetCommands|g' \
        -e 's|RedisStandaloneConfiguration|ValkeyStandaloneConfiguration|g' \
        -e 's|RedisStreamCommands|ValkeyStreamCommands|g' \
        -e 's|RedisStringCommands|ValkeyStringCommands|g' \
        -e 's|RedisSystemException|ValkeySystemException|g' \
        -e 's|RedisTemplate|ValkeyTemplate|g' \
        -e 's|RedisTxCommands|ValkeyTxCommands|g' \
        -e 's|RedisZSetCommands|ValkeyZSetCommands|g' \
        -e 's|StringRedisConnection|StringValkeyConnection|g' \
        -e 's|StringRedisSerializer|StringValkeySerializer|g' \
        -e 's|StringRedisTemplate|StringValkeyTemplate|g' \
        "$java_file"
done

# Update application.properties and application.yml
find "$PROJECT_DIR/src" -name "application*.properties" -o -name "application*.yml" -o -name "application*.yaml" | while read config_file; do
    echo "Updating $config_file"
    sed -i.bak \
        -e 's|spring\.redis\.|spring.valkey.|g' \
        -e 's|spring\.cache\.redis\.|spring.cache.valkey.|g' \
        -e 's|redis://|valkey://|g' \
        -e 's|rediss://|valkeys://|g' \
        "$config_file"
done

# Update XML schema namespaces
find "$PROJECT_DIR/src" -name "*.xml" -type f | while read xml_file; do
    echo "Updating $xml_file"
    sed -i.bak \
        -e 's|http://www\.springframework\.org/schema/redis|http://www.springframework.org/schema/valkey|g' \
        -e 's|spring-redis|spring-valkey|g' \
        -e 's|xmlns:redis=|xmlns:valkey=|g' \
        "$xml_file"
done

echo "Migration complete. Review changes and test thoroughly."
echo "Backup files created with .bak extension"
