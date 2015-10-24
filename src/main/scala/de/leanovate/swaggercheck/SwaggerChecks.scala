package de.leanovate.swaggercheck

import java.io.InputStream

import io.swagger.models.Swagger
import io.swagger.parser.SwaggerParser
import org.scalacheck.Gen

import scala.io.Source

class SwaggerChecks(swagger: Swagger) {

  def jsonGenerator(name: String): Gen[String] = GenSwaggerJson.modelJsonGen(swagger.getDefinitions.get(name))
}

object SwaggerChecks {
  def apply(swaggerAsString: String) :SwaggerChecks= {
    val swagger = new SwaggerParser().parse(swaggerAsString)

    new SwaggerChecks(swagger)
  }

  def apply(swaggerInput: InputStream) :SwaggerChecks= {
    apply(Source.fromInputStream(swaggerInput).mkString)
  }
}