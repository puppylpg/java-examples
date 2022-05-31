

- https://baomidou.com/pages/226c21/#%E5%88%9D%E5%A7%8B%E5%8C%96%E5%B7%A5%E7%A8%8B


# 配置

- https://baomidou.com/pages/3b5af0/#spring-%E5%B7%A5%E7%A8%8B

配置上和mybatis最大的区别，就是使用了mybatis plus自己的MybatisSqlSessionFactoryBean（而不是mybatis的SqlSessionFactoryBean）去创建SqlSession。
（所以mybatisplus天生就要和spring结合）

另一个区别就是mapper继承mp的`BaseMapper`。

继承了之后，就可以使用BaseMapper里的各种通用方法了。而在mybatis里，即使selectAll也要自己定义。

另外就是可以使用pojo写查询了，而不是单纯地使用mybatis的sql语句映射。
