### phoenix 安装
* 首先确定已经安装了hadoop  
    ```
    //这里以hadoop-2.6.0为例
    ```
* 再确定已经安装了hbase   
    ```
    //这里以hbase-1.0.3
    ```
* 下载phoenix-4.7.0-HBase-1.0-bin.tar.gz 包
    ```
    把phoenix-4.7.0-HBase-1.0-server.jar
      phoenix-core-4.7.0-HBase-1.0.jar
      phoenix-server-4.7.0-HBase-1.0.jar
      phoenix-server-4.7.0-HBase-1.0-runnable.jar
    这些jar包放到每个服务器上的hbase/lib/下，重新启动hbase
    ```
* 通过命令连接
    ```
    进入phoenix-4.7.0-HBase-1.0-bin/bin/目录
    ./sqlline.py node1,node2,node3   //node1,node2,node3是zookeeper的hostname
    通过该命令进入客户端，可以执行sql
    ```
### phoenix 导入csv文件数据到hbase
导入csv格式的文件数据到hbase，有两种方式。一种是通过psql.py命令工具，
另一种是通过基于mapreduce的导入工具。psql.py命令工具是单线程的，
适合导入几十兆以内的数据，基于mapreduce的导入工具可以更好的导入更大的数据
* psql.py 导入[官网资料](http://phoenix.apache.org/bulk_dataload.html)
    + 数据样例 data.csv
    ```
    12345,John,Doe
    67890,Mary,Poppins
    ```
    + 表结构
    ```
    CREATE TABLE example (
    my_pk bigint not null,
    m.first_name varchar(50),
    m.last_name varchar(50) 
    CONSTRAINT pk PRIMARY KEY (my_pk))
    ```
    + 上传数据
    ```
    ./psql.py node1,node2,node3 -t EXAMPLE data.csv
    //指定表名 貌似必须大写 小写会报错(有待继续了解)
    //如果不指定表名默认会找csv文件名
    
    //参数的描述
    -t (table) 指定要导入数据的表名,默认表名是和CSV文件名一致，该参数区分大小写
    -d 自定义分隔符 默认是逗号分隔，如：-d '~' 使用'~'分隔
    ```
* mapreduce导入(后续补充)

### phoenix 和hbase中已经存在的表建立映射关系
```
假如已经通过hbase shell建立表
create 't1',{NAME => 'f1' , VERSIONS => 5}
则可以通过phoenix shell来建立映射
CREATE VIEW "t1" (pk VARCHAR PRIMARY KEY , "f1".val VARCHAR );
    如果需要新建表可以通过pnoenix shell
CREATE TABLE "t1" (pk VARCHAR PRIMARY KEY , "f1".val VARCHAR );
在hbase shell中建立表，指定了表名't1',列族名'f1'
在phoenix shell中建立表，指定了表名't1',列族名'f1',还指定了
rowkey 必须是一个varchar(字符串)，列的值必须是一个 varchar
如果在使用hbase shell 创建表时使用的事大写字母
create 'T1',{NAME => 'F1' , VERSIONS => 5}
则在phoenix shell中就可以不用使用双引号
CREATE VIEW t1 (pk VARCHAR PRIMARY KEY , f1.val VARCHAR );
```

### phoenix 的sql语法可以参考[官网](http://phoenix.apache.org/language/index.html)


    
    