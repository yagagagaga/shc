package org.apache.spark.sql.execution.datasources.hbase

import java.util

import scala.util.Random

import org.apache.spark.Partitioner

import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.hbase.util.Bytes

class HRegionPartitioner(allRegionLocations: List[Array[Byte]]) extends Partitioner {

  private lazy val map: util.TreeMap[String, Int] = {
    val ret = new util.TreeMap[String, Int]()

    allRegionLocations.zipWithIndex
      .foreach { case (k, v) => ret.put(Bytes.toStringBinary(k), v) }
    ret
  }

  override def numPartitions: Int = {
    allRegionLocations.size
  }

  override def getPartition(key: Any): Int = {
    val tokens: Array[String] = StringUtils.splitPreserveAllTokens(key.toString, "/")
    val random: Int = Random.nextInt(numPartitions)
    if (tokens.length != 3) {
      random
    } else {
      val Array(rk, _, _) = tokens
      val key = map.floorKey(rk)
      if (key == null) {
        random
      } else {
        map.getOrDefault(key, random)
      }
    }
  }
}

object HRegionPartitioner {
  def apply(allRegionLocations: List[Array[Byte]]): HRegionPartitioner = {
    new HRegionPartitioner(allRegionLocations)
  }
}
