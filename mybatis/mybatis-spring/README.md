
- https://mybatis.org/spring/zh/index.html

mybatis-spring的mybatis和spring都是provided，所以需要自己手动引入mybatis和spring。

也可以选择自己注册mapper，注册一个MapperFactoryBean：
```
<bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
  <property name="mapperInterface" value="org.mybatis.spring.sample.mapper.UserMapper" />
  <property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
```

# 自动扫描mapper
有了`@MapperScan`，就不需要手动把mapper配置成bean了：https://mybatis.org/spring/zh/sample.html

从`MapperScannerConfigurer`的doc看来，**`@Mapper`并不是scan的对象，扫的是所有有至少一个方法的接口**：
> BeanDefinitionRegistryPostProcessor that searches recursively starting from a base package for interfaces and registers them as MapperFactoryBean. Note that only interfaces with at least one method will be registered; concrete classes will be ignored.


# 所以`@Mapper`有什么卵用？？？
（剧透：它就是个marker，mybatis和mybatis-spring里没用它，但是mybatis-spring-boot-starter里用它了）

看起来的确没什么卵用：https://github.com/mybatis/spring-boot-starter/issues/46

> I have recoded this to be more aligned with Spring @configuration that is based on annotations. So I have added a @Mapper marker annotation to the core so other DI frameworks can use it (spring, boot, cdi)

关于@MapperScan的处理逻辑，参考{@link MapperScannerRegistrar#registerBeanDefinitions}

# mapper类和xml混搭
也可以在mapper类里写接口方法，在xml里写sql映射。使用FactoryBean#setMapperLocations指定xml文件位置即可。
