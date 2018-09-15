package com.example.demo.mapper.slave

/**
  * yarn任务统计
  */

@Mapper
trait TaskCountMapper {

  def count():Long

}
