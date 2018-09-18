package com.wallace.demo.rest.sever.demo.common

/**
  * Created by 10192057 on 2018/4/23 0023.
  */
trait Using extends LogSupport {
  protected def using[A <: {def close() : Unit}, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }
}
