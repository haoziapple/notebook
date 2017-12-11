# Maven 常用命令

## 查看依赖树
> mvn dependency:tree

## 使用本地骨架生成项目
> mvn archetype:generate -DarchetypeCatalog=local

## 使用选定骨架生成项目
> mvn archetype:generate -DgroupId=com.companyname.bank -DartifactId=consumerBanking -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

## 从项目生成骨架
> mvn archetype:create-from-project

## mybatis生成代码
> mvn mybatis-generator:generate

## 执行java Main方法
> mvn exec:java -Dexec.args="-pathF:\gitProject\sloth\mySlothProject -packagecom.test -h10.108.26.11 -P3306 -ufz_aquatic_zf -pfuzhong2015 -dfz_auqatic_zf -strategyssm"  -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="com.github.coolcooldee.sloth.Application"

## 远程部署到Nexus
> mvn deploy:deploy-file -DgroupId=com.fzrj.framework -Dversion=1.0.0 -Dpackaging=jar -DartifactId=starter-parent-archtype -Dfile=F:\gitProject\spring_demo\archetype\target\starter-parent-archetype-1.0.0.jar -Durl=http://10.108.26.220:8081/nexus/content/repositories/thirdparty/ -DrepositoryId=nexus-service

> pom.xml里配置了distributionManagement的话可以简单的使用mvn deploy

## 部署第三方jar包到Maven
> mvn deploy:deploy-file -DgroupId=com.demo -DartifactId=demo-project -Dversion=1.0.0 -Dpackaging=jar -Dfile=**.jar -Durl=http://10.108.26.220:8081/nexus/content/repositories/thirdparty/ -DrepositoryId=thirdparty

## 打包可执行jar包
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <configuration>
        <archive>
            <manifest>
                <mainClass>com.guanbao.testzone.App</mainClass>
                <addClasspath>true</addClasspath>
                <classpathPrefix>lib/</classpathPrefix>
            </manifest>
        </archive>
        <classesDirectory>
        </classesDirectory>
    </configuration>
</plugin>
```

## 打包跳过测试
> mvn clean package -Dmaven.test.skip=true