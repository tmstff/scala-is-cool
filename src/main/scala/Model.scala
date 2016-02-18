case class User (name: String, orders: List[Order] = Nil)

case class Order (id: Int, products: List[Product] = Nil)

case class Product (id: Int, category: String)
