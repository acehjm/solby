# Mybatis 学习

Mybatis架构图：

![Mybatis架构图](https://github.com/acehjm/solby/blob/master/docs/images/Mybatis%20structure.png)

#### 基础支持模块

1. *解析器模块*

   - XML解析：DOM（Document Object Model）、SAX（Simple API for XML）、StAX（Streaming API for XML）
   - DOM：基于树形结构的XML解析方式，它会将整个XML文档读入内存并构建一个DOM树，基于该树对各个节点（Node）进行操作。
   - SAX：基于事件模型的XML解析方式，它将XML文档的一部分加载到内存中，即可开始解析，在处理过程中并不会在内存中记录XML中的数据，所以占用资源比较少。当程序处理过程中满足条件时，也可以立即停止解析过程。SAX没有写XML文档的能力，是一种推模式。
   - StAX：JDK提供，可以很好支持DOM和SAX解析方式，与SAX类似，也是把XML文档作为一个事件流进行处理，不同之处在于StAX使用的是拉模式（即应用程序通过调用解析器推进解析的进程）。
   - XPath：一种为查询XML文档设计的语言，它使用路径表达式选取XML文档中的节点或节点集合，常结合DOM解析方式一起使用。

   > MyBatis 中使用了DOM加XPath的解析方式。

2. *反射工具箱*
   - Reflector：是Mybatis中反射模块的基础，每一个Reflector对象都代表对应的一个类，在Reflector中缓存了反射操作需要使用的类的元信息。
   - ReflectorFactory：该接口主要实现了对Reflector对象的创建和缓存，`isClassCacheEnbale()`是检测该ReflectoryFactory对象是否会缓存Reflector对象，`setClassCacheEnabled(boolean classCacheEnabled)`设值是否缓存Reflector对象，`findForClass(Class<?> type)`创建指定Class对应的Reflector对象。使用ConcurrentMap集合实现对Reflector对象的缓存。
   - TypeParameterResolver：静态解析类，提供一系列方法解析指定类中的字段、方法返回值或方法参数的类型。`resolveFieldType()`解析字段类型，`resolveReturnType()`解析返回值类型，`resolveParamType()`解析方法参数列表中各个参数类型。
   - ObjectFactory：通过重载的多个`create()`方法创建指定类型的对象，是一个反射工厂，其`create()`方法通过调用`instantiateClass()`方法实现。
   - Property工具集：PropertyTokenizer、PropertyNamer、PropertyCopier，`orders[0].name`这种由`.`和`[]`组成的表达式由PropertyTokenizer解析，方法名到属性名的转换以及多种检测操作由PropertyNamer解析，相同类型的两个对象之间的属性值拷贝由PropertyCopier解析，其核心方法是`copyBeanProperties()`。
   - MetaClass：通过Reflector和PropertyTokenizer组合使用，实现对复杂的属性表达式的解析，并实现了获取指定属性描述信息的功能。MetaClass中比较重要的方法是`findProperty()`方法，它是通过调用`MetaClass.buildProperty()`方法实现的，而`build Property()`方法会通过PropertyTokenizer解析复杂的属性表达式。
   - ObjectWrapper：是Mybatis对类级别元信息的封装和处理，该接口是对对象的包装，抽象了对象的属性信息，它定义了一系列查询和更新对象属性信息的方法。
   - MetaObject：是对属性表达式的解析，MetaObject的构造方法会根据传入的原始对象的类型以及ObjectFactory工厂的实现，创建相应的ObjectWrapper对象。

3. *类型转换*
   - TypeHandler：所有类型转换器都继承了TypeHandler接口，TypeHandler接口中有四个方法，分为两类，`setParameter()`方法负责将数据由JdbcType转换成JavaType，`getResult()`方法及其重载方法负责将数据由JavaType转换成JdbcType。
   - TypeHandlerRegistry：在Mybatis初始化过程中，会为所有已知的TypeHandler创建对象，并实现注册到TypeHanlderRegistry中，由TypeHandlerRegistry负责管理这些TypeHandler。
   - TypeAliasRegistry：通过该类完成别名的注册和管理工作，它通过`TYPE_ALIASES`字段（`Map<String, Class<?>>`类型）管理别名与JavaType之间的对应关系，通过`TypeAliasRegistry.registerAlias()`方法完成注册别名。

4. *日志模块*
   - 日志模块使用适配器模式，定义了一套统一的日志接口供上层使用。
   - 提供了`trace` 、`debug`、`warn`、`error`四个级别的日志。
   - 通过JDK动态代理的方式，将JDBC操作通过日志框架打印出来，一般在开发阶段使用。

5. *资源加载*
   - 类加载器：JVM中类加载器（ClassLoader）负责加载来自文件系统、网络或其他来源的类文件，JVM中类加载器默认使用双亲委派模式（双亲委派模式可以保证：一、子加载器可以使用父加载器已加载的类，而父类加载器无法使用子加载器已加载的类；二、父加载器已加载过的类无法被子加载器再次加载。），即子加载器委托父加载器加载。
   - ClassLoaderWrapper：是ClassLoader的包装器，其中包含了多个ClassLoader对象，通过调整多个类加载器的使用顺序，ClassLoaderWrapper可以确保返回给系统使用的是正确的类加载器。
   - ResolverUtil：可以根据指定条件查找指定包下的类，ResolverUtil中使用classLoader字段记录当前现场上下文绑定的ClassLoader，可以修改使用类加载。
   - 虚拟文件系统（VFS）：VFS是一个抽象类，用来查找指定路径下的资源。

6. *数据源模块*

   - DataSourceFactory：有UnpooledDataSourceFactory和PooledDataSourceFactory类。

   - UnpooledDataSource：未池化，频繁使用会耗资源。

   - PooledDataSource：实现数据库连接的重用、提高响应速度、防止数据库连接过多造成数据库假死、避免数据库连接泄漏等。数据库连接池会控制连接总数的上限以及空闲连接的上限，如果连接池创建的总连接数已达上限，且已都被占用，则后续请求连接的线程会进入阻塞队列等待，知道有线程释放出可用的连接。如果连接池中空闲连接数较多，达到其上限，则后续返回的空闲连接不会放入池中，而是直接关闭，可以减少系统维护多余数据库连接的开销。

     > 在配置时，空闲连接上限不能设置过大（浪费系统资源来维护空闲连接），也不能设置过小（出现瞬间峰值请求时，系统响应能力较弱），需根据性能测试、权衡以及一些经验配置。

7. *事物管理模块*
   - 对数据库事务进行了抽象。
   - 五个接口：`getConnection()`获取对应的数据库连接，`commit()`提交事物，`rollback()`回滚事物，`close()`关闭数据库连接，`getTimeOut()`获取事物超时时间。

8. *绑定模块*
   - Mapper接口类中方法和XML文件中key的映射处理操作。
   - MapperRegistry：是Mapper接口及其对应的代理对象工厂的注册中心。
   - MapperProxyFactory：主要负责创建代理对象。
   - MapperProxy：实现了InvocationHandler接口。
   - MapperMethod：封装了Mapper接口中对应的方法的信息，以及对应SQL语句的信息。

9. *缓存模块*
   - 分为两层结构：一级缓存和二级缓存，本质上一样，都是Cache接口的实现。
   - Cache：定义了所有缓存的基本行为。
   - CacheKey：可以添加多个对象，由这些对象共同确定两个CacheKey对象是否相同，CacheKey重写了`equals()`方法和`hashCode()`方法。

#### 核心处理模块

> Mybatis初始化的主要工作是加载并解析mybatis-config.xml配置文件、映射配置文件以及相关的注解信息。

- Mybatis的初始化入口是`SqlSessionFactoryBuilder.build()`。首先是读取配置文件，然后解析文件得到Configuration对象，创建DefaultSqlSessionFactory对象。
- Configuration是Mybatis初始化过程中的核心对象，几乎全部配置信息都会保存到Configuration对象中，它是在Mybatis初始化过程中创建且是全局唯一。
- XMLConfigBuilder是BaseBuilder子类之一，主要负责解析mybatis-config.xml配置文件，它的`parse()`方法是解析配置文件的入口。
- XMLMapperBuilder负责解析映射配置文件，也是继承BaseBuilder抽象类，`parse()`方法是解析配置文件的入口。
- XMLStatementBuilder用来解析SQL节点，Mybatis使用SqlSource接口表示映射文件或注解中定义的SQL语句，但它表示的SQL语句不能直接被数据库执行，因为其中可能含有动态SQL语句相关的节点或是占位符等需要解析的元素。
- SqlNode用来解析SQL语句中定义的动态SQL节点、文本节点等。
- SqlSource接口中`getBoundSql(Object parameterObject)`方法通过解析得到BoundSql对象，其中封装了包含`"?"`占位符的SQL语句，以及绑定的实参。
- DynamicContext主要用来记录解析动态SQL语句之后产生的SQL语句片段，可以认为它是一个用于记录动态SQL语句解析结果的容器。
- SqlSourceBuilder主要完成两方面的操作，一方面是解析SQL语句中的`#{}`占位符中定义的属性，格式类似于`#{_frc-item_0,javaType=int,jdbcType=NUERIC,typeHandler=MyTypeHandler}`，另一方面是将SQL语句中的`#{}`占位符替换成`?`占位符。
- DynamicSqlSource负责解析动态SQL语句，也是最常用的SqlSource实现之一。
- RowSqlSource是SqlSource的另一个实现，其逻辑与DynamicSqlSource类似，但是执行的时机不一样，处理的SQL语句类型也不一样。RowSqlSource在构造方法中首先会调用`getSql()`方法，其中通过调用`SqlNode.apply()`方法完成SQL语句的拼接和初步处理，之后会使用`SqlSourcebuilder`完成占位符的替换和ParameterMapping集合的创建，并返回StaticSqlSource对象。
- ResultSetHandler负责映射select语句查询得到的结果集，同时还会处理存储过程执行后的输出参数。
- KeyGenerator接口提供业务逻辑需要获取插入记录时产生的自增主键。
- StatementHandler是Mybatis的核心接口之一，它的接口功能有创建Statement对象，为SQL语句绑定实参，执行select|insert|update|delete等多种类型的SQL语句，批量执行SQL语句，将结果集映射成结果对象等。
- Executor是Mybatis核心接口之一，其中定义了数据库操作的基本方法。在实际应用中经常涉及的SqlSession接口的功能，都是基于Executor接口实现的。

#### 接口模块

- SqlSession中定义了常用数据库操作方法，如增删改查等。

#### 插件

> Mybatis中，插件通过拦截器的方式实现，可以自定义很多强大的功能，如主键生成、黑白名单、分页、分库、分表等。