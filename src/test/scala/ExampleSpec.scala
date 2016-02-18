import java.util.concurrent.TimeUnit

import collection.mutable.Stack
import org.scalatest._

import scala.concurrent.Await._
import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ExampleSpec extends FlatSpec with Matchers {

  val user = User(
    name = "Tim",
    orders= List(
      Order(
        id = 1,
        products = List(
          Product(
            id = 1,
            category="Tools")))))

  "Case Classes" should "be nice fellows" in {

    user.name should be ("Tim")
    user.orders(0).products(0).category should be ("Tools")

    val anotherUser = user.copy(name = "Timo")

    anotherUser.name should be ("Timo")

    (user == anotherUser) should be (false) // same as .equals!
    (user.hashCode() == anotherUser.hashCode()) should be (false)

    (user == user.copy()) should be (true) // same as .equals!
    (user.hashCode() == user.copy().hashCode()) should be (true)

    User.unapply(
      User.apply(name = "Tim", orders = Nil)) should be (("Tim", Nil))
  }

  "Pattern Matching" should "be really cool with case classes" in {
    def checkOrder(someUser: User): String =
      someUser match {
        case User(name, List(Order(1, products))) => s"$name hat genau eine order mit ID 1 und einem Produkt der Kategory ${products(0).category}"
        case User(name, Nil)                      => s"$name hat keine order"
        case User(name, _)                        => s"$name hat keine order mit ID 1"
      }

    checkOrder(user)                                    should be ("Tim hat genau eine order mit ID 1 und einem Produkt der Kategory Tools")
    checkOrder(user.copy(orders = Nil))                 should be ("Tim hat keine order")
    checkOrder(user.copy(orders = List(Order(id = 2)))) should be ("Tim hat keine order mit ID 1")
  }

  "Pattern Matching" should "work nice with lists" in {
    def nameGroup(people: List[String]): String =
      people match {
        case "Patrick" :: Nil                               => "Nur Patrick"
        case "Patrick" :: List(_*)                          => "Patrick und ein paar andere"
        case "Tim" :: "Karl" :: "Klößchen" :: "Gaby" :: Nil => "TKKG"
        case _                                              => "irgendwer"
      }

    nameGroup(List("Patrick"))                          should be ("Nur Patrick")
    nameGroup(List("Patrick", "Remigius", "Tim"))       should be ("Patrick und ein paar andere")
    nameGroup(List("Tim", "Karl", "Klößchen", "Gaby"))  should be ("TKKG")
    nameGroup(List("Alice"))                            should be ("irgendwer")
  }

  trait PiCalculatingAbility {
    def calculatePi(): Double = Math.PI
  }

  "Traits" should "enrich classes" in {
    val remi = new User("Remigius") with PiCalculatingAbility

    remi.calculatePi() should be (Math.PI)
  }

  def getUserId : Future[String] = Future("someId")

  def getOrders(userId: String): Future[List[Order]] = Future(List(Order(1)))

  "for comprehensions" should "make asnyc calls more readable" in {
    val orderIds = for(
      userId <- getUserId;
      orders <- getOrders(userId)
    ) yield (orders.map(_.id))

    get(orderIds) should be (List(1))
  }


  "Lists" should "provide nice map reduce" in {
    List(1,2,3,4,5).filter(_%2 == 0).foldLeft(0)(_+_) should be (6)
    List("Tim", "Karl", "Klößchen", "Gaby").map(_.charAt(0)).mkString should be ("TKKG")
  }

  def resultToString(either: Either[Exception, String]) =
    either match {
      case Left(e) => s"Fehler: ${e.getMessage}"
      case Right(text) => s"Erfolg: $text"
    }

  "Either" should "be a nice way to return failures or results" in {
    resultToString(Left(new Exception("kapott")))   should be ("Fehler: kapott")
    resultToString(Right("joot"))                   should be ("Erfolg: joot")
  }

  "Maps" should "be handy to use" in {
    val map = Map("a" -> "b", "c" -> "d")

    map("a") should be ("b")
    map("c") should be ("d")
  }

  "Tuples" should "be handy" in {
    val (name, orders) = toTuple

    name should be ("Tim")
    orders(0).id should be (1)
  }

  def toTuple: (String, List[Order]) = {
    User.unapply(user).get
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    } 
  }

  def get[T](eventualResponse: Future[T]): T = {
    result(eventualResponse, Duration.create(1, TimeUnit.SECONDS))
  }
}