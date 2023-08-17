
命令并没有变短，以前所有`-cp`指定的包，现在都要在`-p`里指定，一个也不少：
```bash
/home/pichu/.jdks/openjdk-20.0.2/bin/java -javaagent:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/lib/idea_rt.jar=44919:/ssd/JetBrains/Toolbox/intellij-idea-ultimate/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -p /ssd/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/home/pichu/Codes/Java/mine/java-examples/8-plus/java9-module/module-callee/target/classes:/home/pichu/Codes/Java/mine/java-examples/8-plus/java9-module/module-caller/target/classes -m module.caller/com.puppylpg.caller.Main

Hello world!
```

如果不使用-m启动模块（还是以前的启动方式），还要使用--add-modules把-p指定的module再写一遍（如果他们最终只有一个root，写那个就行）：
因为如果不使用-m，默认的root模块就是`java.*`，即jre。以他们为root，是找不到自己指定的module的。所以要使用--add-modules手动指定root，根据这个root可以找到所有的其他module和jre module。
- https://www.cnblogs.com/javastack/p/13384441.html

callee不exports出来的package，即使是public，caller也用不了。

