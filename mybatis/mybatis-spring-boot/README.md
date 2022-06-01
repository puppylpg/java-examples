
- https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

`@Mapper`在spring boot里可以自动注册mapper：
> The MyBatis-Spring-Boot-Starter will search, by default, for mappers marked with the @Mapper annotation.

**在MybatisAutoConfiguration#registerBeanDefinitions里用到它了。找到所有的mapper，并注册为MapperFactoryBean**。

**哪里的mapper会被自动注册**？MybatisAutoConfiguration里设置了basePackage：`builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));`,
如果开debug，会发现用的package：Using auto-configuration base package 'boot'，其实就是spring boot默认扫描的package。

它里面还有这么一段注释：
```
  /**
   * This will just scan the same base package as Spring Boot does. If you want more power, you can explicitly use
   * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed mappers working correctly, out-of-the-box,
   * similar to using Spring Data JPA repositories.
   */
```
说明和springboot默认扫描的包一致。显式使用MapperScan可以改变这一策略。事实上，发现如果使用了MapperScan，@Mapper的自动注册就不生效了（log里没有上述debug语句了）。

所以如果想阻止自动注册mapper，只要有手动注册的MapperFactoryBean就行了。

另外spring boot配置mybatis也更方便了。
