package rgfdataproject

import java.io.File
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date

import scala.collection.mutable.ListBuffer

/**
  * Created by geoff_000 on 01/03/2016.
  */
object DataSetFactory extends App {

  def apply():ListBuffer[LineOfData] = {

    def getListOfFiles(dir: String): List[File] = {
      val d = new File(dir)
      if (d.exists && d.isDirectory) {
        d.listFiles.filter(_.isFile).toList
      } else {
        List[File]()
      }
    }

    val PATH: String = "Z:\\Shares\\9 - Finance\\Accounts\\2016\\MonitoringTest\\RGF_DATA\\31-12-15\\"

    def filesInFolder: List[File] = getListOfFiles(PATH)


    val files: List[File] = filesInFolder

    var dataSet: ListBuffer[LineOfData] = ListBuffer[LineOfData]()

    for (i <- 0 to files.length - 1) {

      //this section gets the short name of the DP (filename without the .csv extension)
      var p = Paths.get(files(i).toString)
      var file = p.getFileName.toString
      var pos = file.lastIndexOf(".")
      if (pos > 0) {
        file = file.substring(0, pos)
      }

      // this section reads lines of data
      var bufferedSource = io.Source.fromFile(files(i).toString())
      var counter: Int = 0
      val sdf: SimpleDateFormat = new SimpleDateFormat("dd/mm/yyyy")


      for (line <- bufferedSource.getLines) {

        val cols = line.split(",").map(_.trim)

        if (counter > 2) {
          val shortName = file
          val loanRef = cols(0)
          val name = cols(1)
          val drawDate = cols(2)
          val date: Date = sdf.parse(drawDate)
          val drawAmount = cols(3)
          val amount: BigDecimal = BigDecimal(drawAmount)
          val repaidAmount = cols(4)
          val repaid: BigDecimal = BigDecimal(repaidAmount)
          val writeOff = cols(5)
          val wo: BigDecimal = BigDecimal(writeOff)
          val balance = cols(6)
          val bal: BigDecimal = BigDecimal(balance)

          val lineOfData: LineOfData = new LineOfData(dpShortName = file, loanId = loanRef, name = name, drawDate = date
            , drawAmount = amount, repaid = repaid, writeOff = wo, balance = bal)
          dataSet += lineOfData
        }
        counter += 1
      }
      bufferedSource.close
    }
    dataSet
  }
}
