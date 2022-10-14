# Apache Spark - Apache HBase Connector

forked from [hortonworks-spark/shc](https://github.com/hortonworks-spark/shc).

## Change Log

This version based on [hortonworks SHC](https://github.com/hortonworks-spark/shc). You can store the DataFrame into HBase by using bulkload. This is an example:

```scala
df.write
  .format("hbase")
  .option(HBaseTableCatalog.tableName, "test_table")
  .option(HBaseTableCatalog.rowKey, "rk")
  .option(HBaseTableCatalog.cf, "f")
  .option(HBaseRelation.WRITE_MODE, HBaseRelation.Restrictive.BULKLOAD)
  .option(HBaseRelation.HFILE_TEMP_PATH, "hdfs:///tmp/hfile")
  .save()

// structured-streaing is also suported
df.writeStream
  .format("hbase")
  .option("checkpointLocation", "hdfs:///tmp/structured-streaming-checkpoint/")
  .option(HBaseTableCatalog.tableName, "test_table")
  .option(HBaseTableCatalog.rowKey, "rk")
  .option(HBaseTableCatalog.cf, "f")
  .option(HBaseRelation.WRITE_MODE, HBaseRelation.Restrictive.BULKLOAD)
  .option(HBaseRelation.HFILE_TEMP_PATH, "hdfs:///zmk/hfile")
  .outputMode(OutputMode.Append())
  .trigger(Trigger.ProcessingTime(Seconds(10).milliseconds))
  .start()
  .awaitTermination()
```

More information can be found [here](https://github.com/hortonworks-spark/shc).
