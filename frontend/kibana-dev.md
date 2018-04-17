# kibana二次开发笔记
- 使用版本v6.2.2

## 打包步骤
- 安装node-6.12.2，并在PATH中添加安装路径
- 安装yarn-v1.6.0
- 安装python2.7
- 在项目根目录下执行```yarn install```
- 安装grunt：```npm install -g grunt-cli```
- 在项目根目录下执行```grunt build```，执行时间比较久
- 打包好的文件在“target”文件夹下

## 插件开发
- 安装yeoman：npm install -g yo
- 安装插件generator：npm install -g generator-kibana-plugin
- 执行命令生成项目：yo kibana-plugin
> 注意!执行命令时需要和kibana在相同的目录下
- 参考链接：[elastic/generator-kibana-plugin](https://github.com/elastic/generator-kibana-plugin)， [elastic/template-kibana-plugin](https://github.com/elastic/template-kibana-plugin/)

## 插件打包
- 到插件项目目录下，执行命令：npm run build
- 压缩包在build目录下

## 安装插件
> $ bin/kibana-plugin install file:///some/local/path/my-plugin.zip
- 参考链接：[Installing Plugins](http://www.elastic.co/guide/en/kibana/current/_installing_plugins.html)