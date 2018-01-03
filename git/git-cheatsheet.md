# Git Cheatsheet

## 分支
> git branch -a

> git checkout -b dev origin/dev，作用是checkout远程的dev分支，在本地起名为dev分支，并切换到本地的dev分支

git clone xxx.git "指定目录"

git clone -b [new_branch_name]  xxx.git

## 查看远程地址
> git remote -v

## git密码保存
- [参考链接](https://git-scm.com/docs/git-credential-store)
- .gitconfig中添加如下配置的作用: 登录的用户名密码将以明文形式保存在.git-credentials中
```
[credential]
helper = store
```
ttt
- .git-credentials文件的作用:保存用户名、密码以及试用的host
```
http://[用户名]:[密码]@58.213.73.154%3a8099
```

```
#查看系统支持哪种helper
$ git help -a | grep credential
#设置密码保存为磁盘明文保存形式
$ git config --global credential.helper store
```