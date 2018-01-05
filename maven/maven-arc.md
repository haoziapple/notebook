# springboot的maven架构
- spring-boot-build(指定revision与basedir)
	- spring-boot-project(指定包含的module，无依赖版本信息)
		- sping-boot-dependencies(指定依赖版本信息，dependencyManagement，pluginManagement，parent为spring-boot-build)
		- spring-boot-parent(依然有自己的dependencyManagement和pluginManagement，parent为spring-boot-dependencies)
		- spring-boot-docs(parent为spring-boot-parent)
		- spring-boot-starters(parent为spring-boot-parent,指定了很多module)
			- *spring-boot-starter-parent(parent为spring-boot-dependencies,desc:Parent pom providing dependency and plugin management for applications
		built with Maven)
		- others

## TODO问题
发现spring-boot-build的pom中有这么一句：
```
<!-- Most elements are in profiles so they are stripped out during maven-flatten -->
```
了解maven-flatten
- [使用flatten-maven-plugin对发布的POM进行精简](https://www.cnblogs.com/jonath/p/7729903.html)

spring-boot-parent为什么仍然需要自己的dependencyManagement？pom中有这么一句：
```
<!-- Additional Spring Boot only Dependencies (not useful for users) -->
```

spring-boot-parent中为什么不指定module?

## 一些插件学习
versions-maven-plugin:更新version
```
mvn versions:set -DnewVersion=0.0.3-SNAPSHOT
mvn versions:revert
```

## 官方文档学习
- [pomReference](http://maven.apache.org/pom.html)
They come in five different styles:
1. env.X: Prefixing a variable with "env." will return the shell's environment variable. For example, ${env.PATH} contains the PATH environment variable.
Note: While environment variables themselves are case-insensitive on Windows, lookup of properties is case-sensitive. In other words, while the Windows shell returns the same value for %PATH% and %Path%, Maven distinguishes between ${env.PATH} and ${env.Path}. As of Maven 2.1.0, the names of environment variables are normalized to all upper-case for the sake of reliability.
2. project.x: A dot (.) notated path in the POM will contain the corresponding element's value. For example: ```<project><version>1.0</version></project> ```is accessible via ${project.version}.
3. settings.x: A dot (.) notated path in the settings.xml will contain the corresponding element's value. For example: ```<settings><offline>false</offline></settings> ```is accessible via ${settings.offline}.
4. Java System Properties: All properties accessible via java.lang.System.getProperties() are available as POM properties, such as ${java.home}.
5. x: Set within a ```<properties /> ```element in the POM. The value of ```<properties><someVar>value</someVar></properties> ```may be used as ${someVar}.