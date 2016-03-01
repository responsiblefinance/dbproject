package rgfdataproject

import java.io.File
import java.util

import scala.collection.mutable.ListBuffer

/**
  * Created by geoff_000 on 01/03/2016.
  */
object Main extends App{


  val list:ListBuffer[LineOfData] = DataSetFactory()
  val errors:ListBuffer[String] = Cleaner(list)
  for (line <- errors) println(line)



}