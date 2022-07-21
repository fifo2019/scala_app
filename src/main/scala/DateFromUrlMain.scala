import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import scala.io.Source
import java.io._

import scala.language.implicitConversions


object DateFromUrlMain extends App {

  case class Name(common: String)
  case class Country(name: Name, capital: List[String], area: Double, region: String)
  case class CountryDto(name: String, capital: String, area: Double)

  val sourceUrl = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json"

  def createDto(country: Country): CountryDto = {
    CountryDto(
      name = country.name.common,
      capital = country.capital.head,
      area = country.area
    )
  }

  val dataJson = Source.fromURL(sourceUrl)
  val decoded = decode[List[Country]](dataJson.mkString)

   val result = decoded match {
      case Right(value) =>
        value
          .filter(_.region == "Africa")
          .sortBy(_.area)(Ordering[Double].reverse)
          .slice(0, 10)
          .map(createDto)
          .asJson
          .noSpaces
      case Left(value) => throw new RuntimeException(s"Parsing problems: $value")
  }

  val writer = new PrintWriter(new File("src/main/data/result.json" ))

  writer.write(result)
  writer.close()
}