package com.example.demo.mapper.slave

import org.apache.ibatis.annotations.Mapper

/**
  * yarn任务统计
  */

@Mapper
trait TaskCountMapper {

  def count(): Long

}
