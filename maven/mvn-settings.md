# Maven常用配置

## 打可执行jar包
```xml
<build>
	<pluginManagement>
		<plugins>
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
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
			    <configuration>
					<source>1.7</source><!-- 源代码使用的开发版本 -->
					<target>1.7</target><!-- 需要生成的目标class文件的编译版本 -->
					<!-- 一般而言，target与source是保持一致的，但是，有时候为了让程序能在其他版本的jdk中运行(对于低版本目标jdk，源代码中需要没有使用低版本jdk中不支持的语法)，会存在target不同于source的情况 -->
					<encoding>UTF-8</encoding>
					<verbose>true</verbose>
					<fork>true</fork><!-- fork is enable,用于明确表示编译版本配置的可用 -->
					<executable>${JAVA_HOME}/bin/javac</executable>
				</configuration>
			</plugin>
		</plugins>
	</pluginManagement>
</build>
```