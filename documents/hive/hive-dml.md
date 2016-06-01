# dml 语法

## 写入hive表中的四种方式

* **导入数据**

```
    load data [local] inpath 'filepath' [overwrite] into table tablename [partition (particol=val1 , particol=val2,...)]
    
local : 上传本地文件时使用local关键字
filepath : hdfs文件地址 如：hdfs://namenode:9000/user/hive/project/data1
```
    
* **Inserting data into Hive Tables from queries**

```
--覆盖该表或分区中的所有数据 unless ‘IF NOT EXISTS’ is provided for a partition 
insert overwrite table tablename1 [partition (partcol1=val1,partcol2=val2,...) [if not exists]] select_statment1  FROM  from_statment;

--在原表的基础上添加数据
insert into table tablename [partition ( partcol1=val1,partcol2=val2,...)] select_statment FROM from_statment;

--insert时，from子句可以放在 select子句后 也可以放在insert子句之前
from from_statment insert overwrite table tablename1[partition(partcol=val1,partcol2=val2,...) [ if not exists ]] select select_statment;

--在原表的基础上添加数据
from from_statment insert into table tablename1 [partition (partcol1=val1,partcol2=val2,...)] select select_statment;

如：from page_view_stg pvs insert overwrite table page_view partition (dt='2008-06-09',country) select pvs.viewTime,pvs.userid,pvs.page_url,null,null,pvs.cut;


overwrite : 覆盖原表中的所有数据
into      : 在原表的基础上继续添加数据

当表tblproperties("immutable"="true")设置为true --表属性为不可变的，默认是false
into 不可以往已有数据的表中添加数据，但是当表为空时可以添加
overwrite 不受immutable属性的影响



```
* **Inserting values into tables from SQL**

    ```
    insert into table table_name [partition (partcol1 [=val1] , partcol2 [=val2],...)] values (val1 [,val2]);
    
    ```