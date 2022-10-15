
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

> mybatis本身提供了一个标记接口`@Mapper`，没有实际用处。**在mybatis-spring-boot-starter里，所有用@Mapper标记的接口都直接生成mapper bean。但是一旦配置了@MapperScan它就失效了**，又没用了。为什么？**因为springboot的auto config（`AutoConfiguredMapperScannerRegistrar`）和@MapperScan一样也是构造出一个MapperScannerConfigurer，设置它的`annotationClass=Mapper.class`**。所以如果用了@MapperScan，就已经有一个MapperScannerConfigurer，springboot也就不再自动配置了。

所以如果想阻止自动注册mapper，只要有手动注册的MapperFactoryBean就行了。

另外spring boot配置mybatis也更方便了。
