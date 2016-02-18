import org.scalatest._

class ComplexNumberSpec extends FlatSpec with Matchers {

  "A complex number" should "be writable in terms like 'a + b*I'" in {

    val number = 1 + 2 * I

    number should be (ComplexNumber(1, 2))
  }
}
