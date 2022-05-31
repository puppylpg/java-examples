
mybatis首页：https://blog.mybatis.org/

# plain mybatis
https://mybatis.org/mybatis-3/zh/getting-started.html

1. 配置mybatis行为：db地址、超时时长等。xml支持引用.properties文件里的属性；
2. 配置mybatis mapper位置（去哪里找mapper）：https://mybatis.org/mybatis-3/zh/configuration.html#mappers
   1. mapper就是sql映射。把sql映射为行为，那结果映射为pojo类；
   2. 可以在xml里使用`<mappers>`配置；
   3. 也可以直接使用`@MapperScan`指定mapper的扫描路径；
   4. 还可以直接用`@Mapper`标记一个mapper类；
3. 配置具体的mapper行为（how to map）：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html
   1. 映射可能很简单，也可能会很复杂。但整体宗旨就是配置sql返回的字段和哪个类的那个field映射；
   2. 手动映射：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Result_Maps
      1. 适用于复杂的情况，使用`resultType`映射为java pojo；
      2. 更复杂的可以使用`<resultMap>`自定义一个映射，然后用`resultMap`引用这个映射。相当于使用了`resultType`；
      3. **包括更复杂的关联关系，association**；
   3. 如果很简单，自动映射就可以搞定：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#%E8%87%AA%E5%8A%A8%E6%98%A0%E5%B0%84
      1. 默认做field映射的时候忽略大小写；
      2. 如果数据库是下划线字段，可以设置mapUnderscoreToCamelCase=true自动和Java的驼峰field映射；
      3. mysql的sql语句select a as b，这样就能自动和pojo的field b映射上了；
      4. 当然也可以手动映射，优先级最高；

定义好怎么加载mapper之后，剩下的自然是怎么使用mapper：https://mybatis.org/mybatis-3/zh/java-api.html
1. 可以直接使用`SqlSession`，每一方法都接受语句的 ID 以及参数对象；
   1. spring的jdbctemplate，**接收的是sql语句**，因为没有mapper；
   2. mybatis定义了mapper，**所以这里传的是mapper的statement的id，这个id就是封装sql的xml块的id**。比如`SqlSession#selectOne("selectPerson",5)`，缺点是：它们并不符合类型安全，对你的 IDE 和单元测试也不是那么友好；
    ```
    <select id="selectPerson" parameterType="int" resultType="hashmap">
    SELECT * FROM PERSON WHERE ID = #{id}
    </select>
   ```
   3. 使用专门的Java mapper类。但是这也只不过是把xml的写sql方式转换为annotation写sql罢了。`@Sql`或者`@Select`，也挺不方便的。**所以mybatis plus出现了！**
2. mapper类：https://mybatis.org/mybatis-3/zh/java-api.html#%E4%BD%BF%E7%94%A8%E6%98%A0%E5%B0%84%E5%99%A8

> insert、update 以及 delete 方法返回的值表示受该语句影响的行数。

所以mybatis plus出现了！


# mybatis others
帮助生成sql：
- https://mybatis.org/mybatis-3/zh/statement-builders.html
