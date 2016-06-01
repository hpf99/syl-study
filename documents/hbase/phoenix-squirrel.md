### 安装使用squirrel    --phoenix的客户端
* phoenix 、squirrel 介绍(参考网友的一些分析)
```
HBase，一个NoSQL数据库，可存储大量非关系型数据。
HBase，可以用HBase shell进行操作，也可以用
HBase Java api进行操作。HBase虽然是一个数据库，
但是它的查询语句，很不太好用。
phoenix 实现了使用sql来操作hbase
类似于操作mysql一样的关系型数据库。

phoenix，由saleforce.com开源的一个项目，后又捐给了Apache。
它相当于一个Java中间件，帮助开发者，
像使用jdbc访问关系型数据库一些，访问NoSql数据库HBase。

phoenix，操作的表及数据，存储在hbase上。
phoenix只是需要和Hbase进行表关联起来。
然后再用工具进行一些读或写操作。

//phoenix 的安装和如何操作hbase 请参考phoenix-hbase文档

其实，可以把Phoenix只看成一种代替HBase的语法的一个工具。
虽然可以用java可以用jdbc来连接phoenix，然后操作HBase，
但是在生产环境中，不可以用在OLTP中。在线事务处理的环境中，
需要低延迟，而Phoenix在查询HBase时，虽然做了一些优化，
但延迟还是不小。所以依然是用在OLAT中，再将结果返回存储下来。

mysql的话，可以CLI命令行的方式操作；可以通过用jdbc，
在Java代码中访问；可以通过用navicat进行访问管理；
phoenix，怎么用呢？~可以看成是mysql。Phoenix可以在CLI下操作；
可以用jdbc操作；可以用phoenix的一个客户端工具Squirrel 访问
Squirrel SQL Client，是一个连接数据库的客户端工具。
一般支持JDBC的数据库都可以用它来连接
```
* OLTP 和 OLAT介绍
```
OLTP的特点一般有:
    1.实时性要求高;
    2.数据量不是很大;
    3.交易一般是确定的，所以OLTP是对确定性的数据进行存取;
    4.并发性要求高并且严格的要求事务的完整,安全性;
OLAP的特点一般有:
    1.实时性要求不是很高，很多应用的顶多是每天更新一下数据;
    2.数据量大，因为OLAP支持的是动态查询，
所以用户也许要通过将很多数据的统计后才能得到想要知道的信息
    3.因为重点在于决策支持，所以查询一般是动态的，
也就是说允许用户随时提出查询的要求
```
* squirrel的安装使用[squirrel下载地址](http://www.squirrelsql.org/)
```
1.下载最新版本squirrel-sql-3.7.1-standard.jar
2.java -jar squirrel-sql-3.7.1-standard.jar 运行该jar 进行安装
3.把phoenix包中的phoenix-core-4.7.0-HBase-1.0.jar
    phoenix-4.7.0-HBase-1.0-client.jar拷贝到squirrel安装目录下的lib包下
3.安装完成后打开该客户端进入主界面


```