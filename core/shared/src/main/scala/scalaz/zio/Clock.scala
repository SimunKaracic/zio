// Copyright (C) 2018 John A. De Goes. All rights reserved.

import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import scalaz.zio._

trait Clock {
  def currentTimeMillis(unit: TimeUnit): IO[Nothing, Long]
  def nanoTime: IO[Nothing, Long]
  def sleep(length: Long, unit: TimeUnit): IO[Nothing, Unit]
}

object Clock {
  object Live extends Clock {
    def currentTimeMillis(unit: TimeUnit): IO[Nothing, Long] =
      IO.sync(unit.convert(System.currentTimeMillis(), MILLISECONDS))

    def nanoTime: IO[Nothing, Long] = IO.sync(System.nanoTime())

    def sleep(length: Long, unit: TimeUnit): IO[Nothing, Unit] =
      IO.sleep(FiniteDuration(length, unit))
  }
}
