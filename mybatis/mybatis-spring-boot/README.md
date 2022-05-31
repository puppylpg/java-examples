
- https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

`@Mapper`在spring boot里可以自动注册mapper：
> The MyBatis-Spring-Boot-Starter will search, by default, for mappers marked with the @Mapper annotation.

**在MybatisAutoConfiguration#registerBeanDefinitions里用到它了。找到所有的mapper，并注册为MapperFactoryBean**。

所以如果想阻止自动注册mapper，只要有手动注册的MapperFactoryBean就行了。

另外spring boot配置mybatis也更方便了。
