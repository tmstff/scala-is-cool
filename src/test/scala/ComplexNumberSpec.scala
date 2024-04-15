import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ComplexNumberSpec extends AnyFlatSpec with Matchers {

  "A complex number" should "be writable in terms like 'a + b*I'" in {

    val number = 1 + 2 * I

    number should be (ComplexNumber(1, 2))
  }
}
