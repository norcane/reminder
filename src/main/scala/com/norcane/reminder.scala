/*
 * @reminder :: say goodbye to forgotten TODOs in your codebase!
 * Copyright (c) 2018 norcane
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.norcane

import java.text.SimpleDateFormat
import java.util.Date

import com.norcane.reminder.Defaults

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.reflect.macros.blackbox
import scala.util.{Failure, Success, Try}

/**
  * Use this annotation to mark a part of your code you want to revisit in the future (temporary
  * solution, quick&dirty hacks). After the date you select, this code won't longer compile, forcing
  * you to review it again.
  *
  * @param date       date until when the part of the code must be revisited
  * @param note       optional reminder note (used as part of the compilation error)
  * @param dateFormat format used for parsing the date (default is ''yyyy-MM-dd'')
  */
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
        c.abort(c.enclosingPosition,
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
