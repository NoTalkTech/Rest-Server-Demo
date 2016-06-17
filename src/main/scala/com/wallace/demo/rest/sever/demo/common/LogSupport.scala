package com.wallace.demo.rest.sever.demo.common

import org.slf4j.LoggerFactory

/**
  * Created by 10192057 on 2016/6/16.
  */
trait LogSupport {

  protected val log = LoggerFactory.getLogger(this.getClass)

}
