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
        ```