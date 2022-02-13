package org.apache.spark.sql.execution.datasources.hbase

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.{DataFrame, SQLContext}

class HBaseSink(sqlContext: SQLContext, parameters: Map[String, String]) extends Sink with Logging {

    override def addBatch(batchId: Long, data: DataFrame): Unit = {
        val query: QueryExecution = data.queryExecution
        val rdd: RDD[InternalRow] = query.toRdd
        val df: DataFrame = sqlContext.internalCreateDataFrame(rdd, data.schema)

        // noinspection ScalaCustomHdfsFormat
        df.write
            .format("hbase")
            .options(parameters)
            .save()
    }
}
