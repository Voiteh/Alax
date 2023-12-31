import mill._, scalalib._
import $ivy.`net.mlbox::mill-antlr:0.1.0`
import net.mlbox.millantlr.AntlrModule


trait CommonModule extends ScalaModule {
  def scalaVersion = "3.1.3"
  def ivyDeps = Agg(ivy"org.scala-lang::scala-library:2.13.10")
}

object utilities extends CommonModule {

}

object ast  extends CommonModule with AntlrModule{
  def moduleDeps = Seq(utilities)

  override def antlrGenerateVisitor: Boolean = true

  override def antlrGenerateListener: Boolean = false
  override def antlrPackage: Option[String] = Some("org.alax.ast")


}

