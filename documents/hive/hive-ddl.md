## DDL 语法

* **操作database**
    + create database
    
        ```
        create (database|schema) [if not exists] database_name [comment database_comment]
        [location hdfs_path][with dbproperties(property_name=property_val,...)];
        ```
    + drop database
    
        ```
        drop (database|schema) [if exists] database_name [restrict|casecade];
        ```
    + alter database
        
        ```
        alter (database|schema) database_name set dbproperties(property_name=property_val,...);
        
        alert (database|schema) database_name set owner [user|role] user_or_role;
        ```
    + use database
    
        ```
        use database_name;
        
        user default;
        
        --查看当前使用的的数据库
        select current_database();
        ```
* **操作table**
    + create table
    
        ```
        --Row Format, Storage Format, and SerDe |Partitioned Tables 
        --External Tables |Create Table As Select (CTAS)
        create [temporary] [external] table [if not exists] [db_name.]table_name 
        [(col_name data_type [comment col_comment],...) ] 
        [comment table_comment]
        [partitioned by (col_name data_type [comment col_comment],...)]
        [clustered by (col_name , col_name,...) [sorted by (col_name [asc|desc] , ...)] into num_buckets buckets]
        [skewed by (col_name , col_name , ...) on ((col_val , col_val , ...) , (col_val , col_val , ...) , ...)
            [stored as directories] 
            [
                [row format row_format]
                [stored as file_format]
                    | stored by 'storage.handler.class.name' [with serdeproperties(...)]
            ]
        ]
        [location hdfs_path]
        [tblproperties(property_name=property_val , ...)]
        [as select_statment]
        
        --Create Table Like
        create [temporary] [external] table [if not exists] [dbname.]table_name
        like existing_table_or_view_name
        [location hdfs_path]
        
        --创建表时 如果没有使用 if not exists 语句，相同名称的表已经存在时就会报错
        --表名和字段名是不区分大小写的
        --SerDe and property names 是区分大小写的
        
        ```
        - 数据类型
            
            ```
            data_type : 
            
                primitive_type --原数据类型（基础数据类型）
                array_type  --数组
                map_type   -- map集合
                struct_type 
                union_type
            
            primitive_type:  --原数据类型（基础数据类型）
            
                tinyint
                smallint
                int
                bigint
                boolean
                float
                double
                string
                binary
                timestamp
                decimal
                decimal(precision,scale)
                date
                varchar
                char
            
            array_type    --数组
            
                array<data_type>
                
            map_type     -- map集合
            
                map< primitive_type, data_type >
            
            struct_type
            
                struct< col_name:data_type [comment col_comment] , ...>
            
            union_type
            
                uniontype< data_type,data_type, ...>
            ```
        
        - row_format
        
            ```
            delimited [fields terminated by char [escaped by char ]]
            [collection items terminated by char]
            [map keys terminated by char]
            [lines terminated by char]
            [null defined as char]
            |serde serde_name [serdeproperties (property_name=property_val,...)]
            ```
        - file_format
        
            ```
            SEQUENCEFILE
            |TEXTFILE
            |RCFILE
            |ORC
            |PARQUET
            |AVRO
            |INPUTFORMAT input_format_classname OUTPUTFORMAT output_format_classname
            ```
        - Partitioned Tables
        
            ```
            create table table_name(
                id  int,
                dtDontQuery string,
                name    string
            )
            partitioned by (date string);
            
            --一个表可以拥有一个或者多个分区，每个分区以文件夹的形式单独存在表文件夹的目录下。
            --分区是以字段的形式在表结构中存在，通过describe table命令可以查看到字段存在，但是该字段不存放实际的数据内容，仅仅是分区的表示。
            --在Hive Select查询中一般会扫描整个表内容，会消耗很多时间做没必要的工作。
            --有时候只需要扫描表中关心的一部分数据，因此建表时引入了partition概念。
            --表中的一个 Partition 对应于表下的一个目录,Partition 就是辅助查询，缩小查询范围，加快数据的检索速度和对数据按照一定的规格和条件进行管理。
            
            eg:
            
            create table page_view(
                viewTime    int,
                userId      bigint,
                page_url    string,
                referrer_url    string,
                ip  string  comment 'ip address of user',
                country     string comment 'country of origination'
            )
            comment 'this is the staging page view table'
            partitioned by (dt string,country string)
            row format delimited fields terminated by ','
            stored as SEQUENCEFILE
            
            ```
            
        - External Tables
        
            ```
            --如果数据已经存在HDFS的'/user/hadoop/warehouse/page_view'上了，如果想创建表，指向这个路径，就需要创建外部表:
            create external table page_view(
                viewTime    int,
                userId      bigint,
                page_url    string,
                referrer_url    string,
                ip  string  comment 'ip address of user',
                country     string comment 'country of origination'
            )
            comment 'this is the staging page view table'
            row format delimited fields terminated by ','
            stored as textfile
            location '<hdfs_location_path>'
            --创建表，有指定EXTERNAL就是外部表，没有指定就是内部表，内部表在drop的时候会从HDFS上删除数据，而外部表不会删除。
            --外部表和内部表一样，都可以有分区，如果指定了分区，那外部表建了之后，还要修改表添加分区。
            --外部表如果有分区，还可以加载数据，覆盖分区数据，但是外部表删除分区，对应分区的数据不会从HDFS上删除，而内部表会删除分区数据。
            ```
            
        - Create Table As Select (CTAS)
        
            ```
            CTAS 对目标表有限制
            --目标表不可以是分区表
            --目标表不可以是外部表
            --目标表不可以是一组桶表
            
            create table new_key_value_store
                row format serde 'org.apache.hadoop.hive.serde2.columnar.ColumnarSerDe'
                stored as rcfile
            as
            select (key % 1024) new_key,concat(key,value) key_value_pair
            from key_value_store
            sort by new_key,key_value_pair;
            ```
        - Create Table Like
        
            ```
            --复制表结构  like创建表只复制表结构，不会复制数据
            create table empty_key_value_store
            like key_value_store
            ```
        - Bucketed Sorted Tables
        
            ```
            create table page_view(
                viewTime int,
                userid  bigint,
                ip  string
            )
            partitioned by (dt string,country string)
            clustered by (userid) sorted by (viewTime) into 32 buckets
            row format delimited fields terminated by '\001'
            collection items terminated by '\002'
            map keys terminated by '\003'
            stored as SEQUENCEFILE;
            ```
    + Drop Table
    
        ```
        drop table [if exists ] table_name [purge]
        --删除表会移除表的元数据和数据，而HDFS上的数据，如果配置了Trash，会移到.Trash/Current目录下。
        --删除外部表时，表中的数据不会被删除。
        ```
    
    + Truncate Table
    
        ```
        --删除表或分区中的所有行
        truncate table table_name [partition (partition_col_name=partition_val_name,...)]
        ```