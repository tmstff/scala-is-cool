case class ComplexNumber(re: Int, im: Int) {
  def +(that: ComplexNumber) = ComplexNumber(re + that.re, im + that.im)
  def -(that: ComplexNumber) = ComplexNumber(re - that.re, im - that.im)
  def *(that: ComplexNumber) = ComplexNumber(re*that.re - im*that.im, re*that.im + im*that.re)
}

object ComplexNumber {
  implicit def intToComplex(i: Int): ComplexNumber = ComplexNumber(i, 0)
}

object I extends ComplexNumber(0, 1)
