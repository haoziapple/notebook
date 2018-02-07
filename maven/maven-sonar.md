# Maven整合Sonar
- sonar版本:5.6.6
- Maven版本:3.3.9
- JDK版本:1.8

## settings.xml文件添加sonar profile
```xml
<profile>
    <id>sonar</id>
	<activation>
		<activeByDefault>true</activeByDefault>
	</activation>
	<properties>
		<sonar.host.url>http://10.108.26.211:80</sonar.host.url>
	</properties>
</profile>
```
## 执行mvn sonar:sonar时出现Unsupported major.minor version 52.0
> pom中添加jdk版本控制
```xml
<build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
</build>
```
## 项目jdk改为1.8后出现"解决:javac: 无效的目标发行版: 1.8"问题
> [参考博文](http://blog.csdn.net/qq_37107280/article/details/73246274)

> settings.xml中添加jdk-1.8的profile
```xml
<profile>  
    <id>jdk-1.8</id>  
  
    <activation>  
      <activeByDefault>true</activeByDefault>  
      <jdk>1.8</jdk>  
    </activation>  
    <properties>  
      <maven.compiler.source>1.8</maven.compiler.source>  
      <maven.compiler.target>1.8</maven.compiler.target>  
      <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
    </properties>  
  
    <repositories>  
      <repository>  
        <id>jdk17</id>  
        <name>Repository for JDK 1.8 builds</name>  
        <url>http://www.myhost.com/maven/jdk18</url>  
        <layout>default</layout>  
        <snapshotPolicy>always</snapshotPolicy>  
      </repository>  
    </repositories>  
</profile>  
```

## 多版本jdk时调整java版本的问题
- C:\Windows\System32下的java.exe，javaw.exe，javaw.exe，javaws.exe等文件删除
- C:\ProgramData\Oracle\Java\javapath下的快捷方式删除
- 以上两步完成后，修改JAVA_HOME环境变量就可达到修改java版本的目的

## sonar生成覆盖率报告的问题
- 注意不要跳过测试，否则不会生成报告
- 执行JaCoCo命令后再上传sonar
> mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=false

> mvn sonar:sonar
- 参考链接:
    - [sonar示例项目](https://github.com/SonarSource/sonar-scanning-examples)
    - [Code Coverage by Unit Tests for Java Project](https://docs.sonarqube.org/display/PLUG/Code+Coverage+by+Unit+Tests+for+Java+Project)
    - [Usage of JaCoCo with Java Plugin](https://docs.sonarqube.org/display/PLUG/Usage+of+JaCoCo+with+Java+Plugin)
    - [Advanced SonarQube Scanner Usages](https://docs.sonarqube.org/display/SCAN/Advanced+SonarQube+Scanner+Usages)
> PS:还是上官网比较靠谱