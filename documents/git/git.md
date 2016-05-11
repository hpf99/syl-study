### git 笔记

1. git config --global 配置

```
git config --global user.name "name"
git config --global user.email "email_address"
```

2. Create a new repository

```
git clone git@gitlab.com:username/tc_goods_comment.git
git push -u origin master
```
	
3. Existing folder or Git repository

```
git init
git remote add origin git@gitlab.com:username/tc_goods_comment.git
git add .
git commit
git push -u origin master
```