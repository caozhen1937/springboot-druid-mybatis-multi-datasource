package com.example.demo.mapper.master

import org.apache.ibatis.annotations.Mapper

/**
  * yarn任务统计
  */

@Mapper
trait TaskCountMapper {

  def count(): Long

}
