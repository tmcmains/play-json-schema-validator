package com.eclipsesource.schema

import org.specs2.mutable.Specification
import play.api.libs.json.Json

class SimplePerformanceSpec extends Specification {

  def timed(name: String)(body: => Unit) {
    val start = System.currentTimeMillis()
    body
    println(name + ": " + (System.currentTimeMillis() - start) + " ms")
  }

  val validator = SchemaValidator()
  val schemaUrl = getClass.getResource("/issue-99-1.json")
  val schema = JsonSource.schemaFromUrl(schemaUrl).get

  val instance = Json.parse(
    """{
      |"mything": "the thing"
      |}""".stripMargin)

  timed("preloaded") {
    for (_ <- 1 to 1000) validator.validate(schema, instance)
  }
  timed("url based") {
    for (_ <- 1 to 1000) validator.validate(schemaUrl, instance)
  }

}
