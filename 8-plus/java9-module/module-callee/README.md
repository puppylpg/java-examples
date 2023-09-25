# 关于maven
使用java modules和使用maven没什么关系。工程下有个module-info.java它就是module了。
但是，不用maven非常不方便！compile output文件夹都要自己设……想引入个外部包也找不到。
所以maven还是正常用吧，二者不相互影响。

# 启动
## 关于package
module的类不能在top level（default package），必须有个可限定的package：
```bash
$ /home/pichu/.jdks/openjdk-20.0.2/bin/java -javaagent:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/lib/idea_rt.jar=38277:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -p /home/pichu/Codes/Java/mine/java-examples/8-plus/java9-module/module-callee/target/classes -m module.callee/Main

Error occurred during initialization of boot layer
java.lang.module.FindException: Error reading module: /home/pichu/Codes/Java/mine/java-examples/8-plus/module-callee/target/classes
Caused by: java.lang.module.InvalidModuleDescriptorException: Main.class found in top-level directory (unnamed package not allowed in module)
```

在module项目下，idea确实是使用`-p`指定module的（代替非moudule是的`-cp`），使用`-m`启动module：
```bash
/home/pichu/.jdks/openjdk-20.0.2/bin/java -javaagent:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/lib/idea_rt.jar=46245:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -p /home/pichu/Codes/Java/mine/java-examples/8-plus/java9-module/module-callee/target/classes -m module.callee/com.puppylpg.callee.Main

Hello world!
Hello INTERNAL world!
```

加入第三方非module依赖commons-lang3，也使用`-p`把它搞成automatic module：
```bash
/home/pichu/.jdks/openjdk-20.0.2/bin/java -javaagent:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/lib/idea_rt.jar=46661:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -p /ssd/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/home/pichu/Codes/Java/mine/java-examples/8-plus/java9-module/module-callee/target/classes -m module.callee/com.puppylpg.callee.Main

Hello world!
Hello INTERNAL world!
```

```
$ jar -d -f ~/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar
找不到模块描述符。已派生自动模块。

org.apache.commons.lang3@3.9 automatic
requires java.base mandated
contains org.apache.commons.lang3
contains org.apache.commons.lang3.arch
contains org.apache.commons.lang3.builder
contains org.apache.commons.lang3.concurrent
contains org.apache.commons.lang3.event
contains org.apache.commons.lang3.exception
contains org.apache.commons.lang3.math
contains org.apache.commons.lang3.mutable
contains org.apache.commons.lang3.reflect
contains org.apache.commons.lang3.text
contains org.apache.commons.lang3.text.translate
contains org.apache.commons.lang3.time
contains org.apache.commons.lang3.tuple
```

# module-info.java
require当然是必要的。比如`requires commons.lang3`，否则用不了这个包。当然包的引入还是用maven，比较方便。

如果想被别的module使用，必须exports出去。

internal包可以不exports出去，caller就用不了，非常爽。
