/*
 * @reminder :: say goodbye to forgotten TODOs in your codebase!
 * Copyright (c) 2018-2019 norcane
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

import org.scalatest.{FlatSpec, Matchers}

/**
  * Test suite for the [[reminder]] annotation.
  */
class reminderSpec extends FlatSpec with Matchers {

  "@reminder annotation" should "compile with reminder date only" in {

    @reminder("2200-02-02")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date (named param) only" in {
    @reminder(date = "2200-02-02")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date and note" in {
    @reminder("2200-02-02", "this is some note")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date and note (named param)" in {
    @reminder("2200-02-02", note = "this is some note")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date (named param) and note (named param)" in {
    @reminder(date = "2200-02-02", note = "this is some note")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date and date format (named param)" in {
    @reminder("2200/02/02", dateFormat = "yyyy/MM/dd")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date (named param) and date format (named param)" in {
    @reminder(date = "2200/02/02", dateFormat = "yyyy/MM/dd")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date, note and date format" in {
    @reminder("2200/02/02", "this is some note", "yyyy/MM/dd")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "compile with reminder date, note and date format (all named params)" in {
    @reminder(date = "2200/02/02", note = "this is some note", dateFormat = "yyyy/MM/dd")
    def doNothing(): Unit = ()

    doNothing()
  }

  it should "not compile with reminder date in past" in {
    """@reminder(date = "2008-02-02") def doNothing(): Unit = ()""" shouldNot compile
  }

  it should "not compile if reminder date doesn't match date format" in {
    """@reminder(date = "2200/02/02") def doNothing(): Unit = ()""" shouldNot compile
  }
}
