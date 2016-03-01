package rgfdataproject

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by geoff_000 on 01/03/2016.
  */
class LineOfData(dpShortName:String, loanId:String,name:String,drawDate:Date,drawAmount:BigDecimal,
                 repaid:BigDecimal,writeOff:BigDecimal,balance:BigDecimal) {

  val sdf:SimpleDateFormat = new SimpleDateFormat("dd/mm/yyyy")


  def getDp:String = dpShortName
  def getLoanRef:String = loanId
  def getdrawDate:Date = drawDate
  def getDrawAmount:BigDecimal = drawAmount
  def getRepaidAmount:BigDecimal = repaid
  def getWriteOff:BigDecimal = writeOff
  def getBalance:BigDecimal = balance



  override def toString={
    " " + dpShortName + " " + loanId + " " + name + " " + " " + sdf.format(drawDate) + " " + drawAmount +
        " " + repaid + " " + writeOff + " " + balance

  }

}


