package com.norcane

import java.text.SimpleDateFormat
import java.util.Date

import com.norcane.reminder.Defaults

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.reflect.macros.blackbox
import scala.util.{Failure, Success, Try}

@compileTimeOnly("enable macro paradise to expand macro annotations")
class reminder(date: String, note: String = Defaults.note, dateFormat: String = Defaults.dateFormat)
  extends StaticAnnotation {

  def macroTransform(annottees: Any*): Any = macro RememberMacros.impl
}

object reminder {

  object Defaults {
    val note = "Please make sure that the annotated part of your codebase is still valid"
    val dateFormat = "yyyy-MM-dd"
  }

}

private[this] object RememberMacros {
  def impl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    implicit def treeTostring(tree: Tree): String =
      c.eval(c.Expr[String](c.untypecheck(tree.duplicate)))

    def extract(date: String, note: String = Defaults.note, dateFormat: String = Defaults.dateFormat) =
      (date, note, dateFormat)

    // extract annotation params from all possible combinations
    val (date: String, note: String, dateFormat: String) = c.prefix.tree match {
      case q"new reminder(date = $date)" =>
        extract(date)
      case q"new reminder($date)" =>
        extract(date)
      case q"new reminder(date = $date, note = $note, dateFormat = $dateFormat)" =>
        extract(date, note, dateFormat)
      case q"new reminder($date, $note, $dateFormat)" =>
        extract(date, note, dateFormat)
      case q"new reminder(date = $date, note = $note)" =>
        extract(date, note)
      case q"new reminder($date, note = $note)" =>
        extract(date, note)
      case q"new reminder(date = $date, dateFormat = $dateFormat)" =>
        extract(date, dateFormat = dateFormat)
      case q"new reminder($date, dateFormat = $dateFormat)" =>
        extract(date, dateFormat = dateFormat)
      case q"new reminder($date, $note)" =>
        extract(date, note)
      case _ =>
        c.abort(
          c.enclosingPosition,
          "POSSIBLE BUG: unexpected annotation parameters order/combination")
    }

    // parse date from input string using the selected date format
    parseDate(date, dateFormat) match {
      case Success(parsedDate) =>
        if (parsedDate.getTime < System.currentTimeMillis())
          c.abort(c.enclosingPosition, note)
        else annottees.head
      case Failure(_) => // cannot parse reminder date using selected date format
        c.abort(c.enclosingPosition, s"Cannot parse date '$date' using selected format '$dateFormat'")
    }
  }

  def today: Date = {
    val format = new SimpleDateFormat("yyyy-MM-dd")
    format.parse(format.format(new Date()))
  }

  def parseDate(date: String, dateFormat: String): Try[Date] =
    Try(new SimpleDateFormat(dateFormat).parse(date))
}
