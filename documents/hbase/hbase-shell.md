### hbase shell 常用命令
* 查看帮助
```
hbase(main):009:0> help
COMMAND GROUPS:
  Group name: general
  Commands: status, table_help, version, whoami

  Group name: ddl
  Commands: alter, alter_async, alter_status, create, describe, disable, disable_all, drop, drop_all, enable, enable_all, exists, get_table, is_disabled, is_enabled, list, show_filters

  Group name: namespace
  Commands: alter_namespace, create_namespace, describe_namespace, drop_namespace, list_namespace, list_namespace_tables

  Group name: dml
  Commands: append, count, delete, deleteall, get, get_counter, incr, put, scan, truncate, truncate_preserve

  Group name: tools
  Commands: assign, balance_switch, balancer, catalogjanitor_enabled, catalogjanitor_run, catalogjanitor_switch, close_region, compact, compact_rs, flush, major_compact, merge_region, move, split, trace, unassign, wal_roll, zk_dump

  Group name: replication
  Commands: add_peer, append_peer_tableCFs, disable_peer, disable_table_replication, enable_peer, enable_table_replication, list_peers, list_replicated_tables, remove_peer, remove_peer_tableCFs, set_peer_tableCFs, show_peer_tableCFs

  Group name: snapshots
  Commands: clone_snapshot, delete_all_snapshot, delete_snapshot, list_snapshots, restore_snapshot, snapshot

  Group name: configuration
  Commands: update_all_config, update_config

  Group name: security
  Commands: grant, revoke, user_permission

  Group name: visibility labels
  Commands: add_labels, clear_auths, get_auths, list_labels, set_auths, set_visibility
```

##### Group name: general
* 查看服务状态
```
hbase(main):001:0> status
2 servers, 0 dead, 229.5000 average load

```
* 查看hbase版本
```
hbase(main):002:0> version
1.0.3, rf1e1312f9790a7c40f6a4b5a1bab2ea1dd559890, Tue Jan 19 19:26:53 PST 2016
```
* 查看表操作的帮助
```
hbase(main):010:0> table_help
```
##### Group name: ddl
* create 创建表
```
//创建表必须指定表名和至少一个列族
hbase(main):029:0> create 'example', {NAME => 'f1', VERSIONS => 5}

hbase(main):033:0> desc 'example'
Table example is ENABLED                                                                                                                                                                                                                                                      
example                                                                                                                                                                                                                                                                       
COLUMN FAMILIES DESCRIPTION                                                                                                                                                                                                                                                   
{NAME => 'f1', BLOOMFILTER => 'ROW', VERSIONS => '5', IN_MEMORY => 'false',
 KEEP_DELETED_CELLS => 'FALSE', DATA_BLOCK_ENCODING => 'NONE', TTL => 'FOREVER',
  COMPRESSION => 'NONE', MIN_VERSIONS => '0', BLOCKCACHE => 'true',
   BLOCKSIZE => '65536', REPLICATION_SCOPE => '0'} 
1 row(s) in 0.1160 seconds

1. 可配置的数据块大小  BLOCKSIZE => '65536'
HFile数据块大小可以在列族层设置，这个数据块不同于HDFS数据块。HFile数据块默认值
是65536个字节或64kb。数据块索引存储每个HFile数据块的起始键。数据块大小设置影响到数据块
索引的大小，数据块越小，索引越大，从而占用更大内存空间，同时加载进内存的数据块更小，
随机查找性能更好。但是如果需要更好的序列扫描性能，那么一次加载更多HFile数据进入内存更为
合理，这意味着数据块应该设置为更大值。相应地索引变小，你将在随机读性能上付出代价。

2. 数据块缓存  BLOCKCACHE => 'true'
把数据放进读缓存，但工作负载却经常不能从中获得性能提升——例如，
如果一张表或表里的列族只被顺序化扫描访问或者很少被访问，
你不会介意Get或Scan花费时间是否有点儿长。在这种情况下，
你可以选择关闭那些列族的缓存。如果你只是执行很多顺序化扫描，
你会多次倒腾缓存，并且可能会滥用缓存把应该放进缓存获得性能提升的数据给排挤出去。
如果关闭缓存，你不仅可以避免上述情况发生，而且可以让出更多缓存给其他表和同一表的其他列族使用。
数据块缓存默认是打开的。你可以在新建表或者更改表时关闭它

3. 激进缓存 IN_MEMORY => 'false'
你可以选择一些列族，赋予它们在数据块缓存里有更高的优先级（LRU缓存）。
如果你预期一个列族比另一个列族随机读更多，这个特性迟早用得上。
这个配置也是在表实例化时设定

```