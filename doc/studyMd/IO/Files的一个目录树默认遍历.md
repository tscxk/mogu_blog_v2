# 目录树的遍历

目录树的方法实现依赖于 **Files.walkFileTree()**，"walking" 目录树意味着遍历每个子目录和文件。Visitor设计模式提供了一种标准机制来访问集合中的每个对象，然后你需要提供在每个对象上执行的操作。 利用这些接口，我们能很方便的在遍历的同时执行我们想要的操作，例如打印所有文件名称，或是遍历完成后删除操作



``` java
package com.example.demo.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @Author tiansc
 * @Description TODO
 * @Classname RmDir
 * @Date 2021/2/5 16:29
 */
public class RmDir {
    public static void rmdir(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            /**
             * 在访问每个文件时执行
             * @param file
             * @param attrs
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                return FileVisitResult.CONTINUE;
            }

            /**
             * 在访问目录前执行
             * @param dir
             * @param attrs
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return super.preVisitDirectory(dir, attrs);
            }

            /**
             * 在访问目录完成之后（包括访问子目录）
             * @param dir
             * @param exc
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            /**
             * 访问文件失败时执行
             * @param file
             * @param exc
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return super.visitFileFailed(file, exc);
            }
        });
    }
}

```



使用方法：

``` java
RmDir.rmdir(Paths.get("src/main/java/com/example/demo/test"));

```

![image-20210205171709615](C:\Users\95250\AppData\Roaming\Typora\typora-user-images\image-20210205171709615.png)





同时还有另外一个常规方法供实现

``` java
Files.walk(Paths.get("src/main/java/com/example/demo/test")).forEach(System.out::println);
//    output:  遍历出目录及文件
//    src\main\java\com\example\demo\test
//    src\main\java\com\example\demo\test\AddAndSubtractPaths.java
//    src\main\java\com\example\demo\test\Directories.java
//    src\main\java\com\example\demo\test\PartsOfPaths.java
//    src\main\java\com\example\demo\test\PathAnalysis.java
//    src\main\java\com\example\demo\test\PathInfo.java
//    src\main\java\com\example\demo\test\RmDir.java
```

