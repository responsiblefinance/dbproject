package rgfdataproject

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

import scala.collection.mutable.ListBuffer

/**
  * Created by geoff_000 on 01/03/2016.
  */
object Cleaner {

  def apply(sample:ListBuffer[LineOfData]): ListBuffer[String]={

    val sdf:SimpleDateFormat = new SimpleDateFormat("dd/mm/yyyy")
    var errorLog:ListBuffer[String] = new ListBuffer[String]()

    for (line <- sample){
      val calculatedBal:BigDecimal = line.getDrawAmount - line.getRepaidAmount - line.getWriteOff
      val reportedBal = line.getBalance
      if (reportedBal != calculatedBal) errorLog += ("Failed case- IncorrectBalanceException: " + line.getDp + " " + line.getLoanRef
            + " **reported balance "
            + reportedBal + " , calculated balance " + calculatedBal + ". difference: " + (calculatedBal - reportedBal) + "**")

      val minDateString:String = "01/09/2012"
      val maxDateString:String = "31/03/2019"
      val minDate:Date = sdf.parse(minDateString)
      val maxDate:Date = sdf.parse(maxDateString)
      val reportedDate:Date = line.getdrawDate
      if(reportedDate.before(minDate)){
        errorLog += ("Failed case- DateBeforeException: " + line.getDp + " " + line.getLoanRef + " **reported date "
            + sdf.format(reportedDate) + " is before min date " + sdf.format(minDate) + ". difference: "  +
            getDateDiff(minDate,reportedDate,TimeUnit.DAYS) + " days**")
      }
      if (reportedDate.after(maxDate)){
        errorLog += ("Failed case- DateAfterException: " + line.getDp + " " + line.getLoanRef + " **reported date "
          + sdf.format(reportedDate) + " is after max date " + sdf.format(maxDate) + ". difference: "  +
          getDateDiff(minDate,reportedDate,TimeUnit.DAYS) + " days**")
      }

    }
    errorLog
  }

  def getDateDiff(date1:Date,date2:Date,timeUnit:TimeUnit):Long={
    val diffInMillies:Long = (date2.getTime() - date1.getTime())
    timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS)
  }
}
