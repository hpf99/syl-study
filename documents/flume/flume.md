## flume sources 介绍

* spooling Directory source

	 是监控指定目录下新文件的变化，一旦出现新文件，就解析该文件的内容，然后写入到channel，写入完成后，就标记该文件已经完成或删除该文件！