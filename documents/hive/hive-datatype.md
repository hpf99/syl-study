## hivede 数据类型

>Hive支持两种数据类型，一类叫原子数据类型，一类叫复杂数据类型。
>原子数据类型包括数值型、布尔型和字符串类型，具体如下表所示：

基本数据类型

类型    |         描述            |实例
---     |    ------------------   | --- 
TINYINT |1个字节（8位）有符号整数 |  1
SMALLINT|2字节（16位）有符号整数  |  1
INT     |4字节（32位）有符号整数  |  1
BIGINT  |8字节（64位）有符号整数  |  1
FLOAT   |4字节（32位）单精度浮点数|  1.0
DOUBLE  |8字节（64位）双精度浮点数|  1.0
BOOLEAN |       true/false        | true
STRING  |       字符串            | "ABC"

hive是用java开发的，hive里的基本数据类型和java的基本数据类型也是一一对应的，除了string类型。有符号的整数类型：TINYINT、SMALLINT、INT和BIGINT分别等价于java的byte、short、int和long原子类型，它们分别为1字节、2字节、4字节和8字节有符号整数.Hive的浮点数据类型FLOAT和DOUBLE,对应于java的基本类型float和double类型。而hive的BOOLEAN类型相当于java的基本数据类型boolean。

>复杂数据类型包括数组（ARRAY）、映射（MAP）和结构体（STRUCT），具体如下表所示：

复杂数据类型

类型    |         描述              |实例
---     |    ------------------     | ---
ARRAY   |一组字段类型相同的有序字段 | array<string>
map     |一组无序键/值对            |map<string,string>
STRUCT  | 可以理解为一个对象        |struct<a: string,b:int,c:double>