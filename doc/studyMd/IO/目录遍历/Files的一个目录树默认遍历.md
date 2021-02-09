# 目录树的遍历和文件查找



### 目录树的遍历

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

![image-20210205171709615](http://picture.lvmz521.com//blog/admin/png/2021/2/5/1612517181269.png)





同时还有另外一个常规方法供实现

``` java
Files.walk(Paths.get("src/main/java/com/example/demo/test")).forEach(System.out::println);
//    output:  遍历所有目录和文件
//    src\main\java\com\example\demo\test
//    src\main\java\com\example\demo\test\AddAndSubtractPaths.java
//    src\main\java\com\example\demo\test\Directories.java
//    src\main\java\com\example\demo\test\PartsOfPaths.java
//    src\main\java\com\example\demo\test\PathAnalysis.java
//    src\main\java\com\example\demo\test\PathInfo.java
//    src\main\java\com\example\demo\test\RmDir.java
```





### 文件查找

``` java
package com.example.demo.Bstudy;

import com.example.demo.Base.Directories;

import java.nio.file.*;
import java.util.stream.Stream;

/**
 * @Author tiansc
 * @Description TODO
 * @Classname Find
 * @Date 2021/2/6 10:48
 */
public class Find {
    public static void main(String[] args) throws Exception {
        Path test = Paths.get("src/main/java/com/example/demo/test");
        // Creating a *directory*, not a file:
        Files.createDirectory(test.resolve("dir.tmp"));
        Files.createTempFile(test.resolve("dir.tmp"), null, null);

        Files.createDirectory(test.resolve("testDir"));
        Files.createFile(test.resolve("testDir").resolve("test.txt"));
        Files.copy(Paths.get("src/main/java/com/example/demo/Bstudy/Find.java"), test.resolve("testDir").resolve("test2.txt"));
        Files.createTempFile(test.resolve("testDir"), null, null);
        
        PathMatcher matcher = FileSystems.getDefault()
                .getPathMatcher("glob:**/*.{tmp,txt}");
        //得到所有符合匹配规则的文件及目录(输出当前相对路径)
        Files.walk(test)
                .filter(matcher::matches)
                .forEach(System.out::println);
        System.out.println("********111111*******");

        PathMatcher matcher2 = FileSystems.getDefault()
                .getPathMatcher("glob:*.tmp");
        //得到所有符合匹配规则的文件及目录(输出文件或者目录名)
        Files.walk(test)
                .map(Path::getFileName)
                .filter(matcher2::matches)
                .forEach(System.out::println);
        System.out.println("********22222*******");
        //得到所有符合匹配规则的文件(输出文件名)
        Files.walk(test)
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .filter(matcher2::matches)
                .forEach(System.out::println);

    }
}
```

输出：

``` java
//src\main\java\com\example\demo\test\dir.tmp
//src\main\java\com\example\demo\test\dir.tmp\3787153086460599535.tmp
//src\main\java\com\example\demo\test\testDir\6002365577167545579.tmp
//src\main\java\com\example\demo\test\testDir\test.txt
//src\main\java\com\example\demo\test\testDir\test2.txt
//        ********111111*******
//dir.tmp
//3787153086460599535.tmp
//6002365577167545579.tmp
//test.txt
//test2.txt
//        ********22222*******
//3787153086460599535.tmp
//6002365577167545579.tmp
//test.txt
//test2.txt
```

