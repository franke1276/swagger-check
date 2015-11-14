package de.leanovate.swaggercheck.schema

import com.fasterxml.jackson.annotation.JsonProperty
import de.leanovate.swaggercheck.shrinkable.CheckJsValue
import de.leanovate.swaggercheck.{SwaggerChecks, VerifyResult}
import org.scalacheck.Gen

case class ReferenceDefinition(
                                @JsonProperty("$ref")
                                ref: String
                                ) extends SchemaObject {
  def simpleRef: String = if (ref.startsWith("#/definitions/")) ref.substring(14) else ref

  override def generate(ctx: SwaggerChecks): Gen[CheckJsValue] = {
    ctx.swaggerAPI.definitions.get(simpleRef)
      .map(_.generate(ctx))
      .getOrElse(throw new RuntimeException(s"Referenced model does not exists: $simpleRef"))
  }

  override def verify(ctx: SwaggerChecks, path: Seq[String], node: CheckJsValue): VerifyResult = {
    ctx.swaggerAPI.definitions.get(simpleRef)
      .map(_.verify(ctx, path, node))
      .getOrElse(throw new RuntimeException(s"Referenced model does not exists: $simpleRef"))
  }
}
