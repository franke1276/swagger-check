package de.leanovate.swaggercheck.schema

import com.fasterxml.jackson.databind.JsonNode
import de.leanovate.swaggercheck.SwaggerChecks
import de.leanovate.swaggercheck.model.{CheckJsNull, CheckJsValue}
import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object EmptyDefinitionSpecification extends Properties("EmptyDefinition") {
  val swaggerChecks = SwaggerChecks(SwaggerAPI(None, Map.empty, Map.empty))

  property("generate") = forAll(EmptyDefinition.generate(swaggerChecks)) {
    node: CheckJsValue =>
      node == CheckJsNull
  }

  property("verify") = forAll(Gen.oneOf(SchemaObject.arbitraryObj(swaggerChecks), SchemaObject.arbitraryArray(swaggerChecks), SchemaObject.arbitraryValue)) {
    node: CheckJsValue =>
      EmptyDefinition.verify(swaggerChecks, Nil, node).isSuccess
  }
}