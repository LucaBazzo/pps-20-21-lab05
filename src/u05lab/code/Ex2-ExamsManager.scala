package u05lab.code

object ExamsManagerTest extends App {

  /* See: https://bitbucket.org/mviroli/oop2018-esami/src/master/a01b/e1/Test.java */

  trait ExamResult {

    def getKind: Kind
    def getEvaluation: Option[Int]
    def cumLaude: Boolean

  }

  trait Kind

  object Kind {
    case class RETIRED() extends Kind
    case class FAILED() extends Kind
    case class SUCCEEDED() extends Kind
  }

  sealed trait ExamResultFactory {

    def failed: ExamResult
    def retired: ExamResult
    def succeededCumLaude: ExamResult
    def succeeded(evaluation: Int): ExamResult

  }

  sealed trait ExamsManager {

    def createNewCall(call: String)
    def addStudentResult(call: String, student: String, result: ExamResult)
    def getAllStudentsFromCall(call: String): Set[String]
    def getEvaluationsMapFromCall(call: String): Map[String, Int]
    def getResultsMapFromStudent(student: String): Map[String, String]
    def getBestResultFromStudent(student: String): Option[Int]

  }

  class ExamResultFactoryImpl extends ExamResultFactory {

    private case class AbstractExamResult(var kind: Kind) extends ExamResult{

      def apply(kind: Kind) = {
        this.kind = kind
      }

      override def getKind: Kind = kind

      override def getEvaluation: Option[Int] = Option.empty

      override def cumLaude: Boolean = false

      override def toString = s"$kind"
    }

    private class AbstractSuccessExamResult(var evaluation: Int)
      extends AbstractExamResult(Kind.SUCCEEDED()){

      if (evaluation < 18 || evaluation > 30) throw new IllegalArgumentException()

      override def getEvaluation: Option[Int] = Option(this.evaluation)
    }

    var FAILED: ExamResult = AbstractExamResult(Kind.FAILED())
    var RETIRED: ExamResult = AbstractExamResult(Kind.RETIRED())
    var CUMLAUDE: ExamResult = new AbstractSuccessExamResult(30) {

      override def cumLaude: Boolean = true

      override def toString = s"$kind" + "(30L)"

    }

    override def failed: ExamResult = FAILED

    override def retired: ExamResult = RETIRED

    override def succeededCumLaude: ExamResult = CUMLAUDE

    override def succeeded(evaluation: Int): ExamResult = {
      new AbstractSuccessExamResult(evaluation) {
        override def toString = s"$kind" + "("+ evaluation +")"
      }
    }

  }

  class ExamsManagerImpl extends ExamsManager{

    type MyMapType = Map[String, scala.collection.mutable.Map[String, ExamResult]]
    var map : MyMapType = Map()

    private def checkArgument(condition: Boolean) = if(!condition) throw new IllegalArgumentException()

    override def createNewCall(call: String): Unit = {
      checkArgument(!map.contains(call))
      //map += (call -> Map[String, ExamResult])
    }

    override def addStudentResult(call: String, student: String, result: ExamResult): Unit = {
      checkArgument(map.contains(call))
      checkArgument(!map.get(call).contains(student))
      //println(this.map(call) += (student, result))
    }

    override def getAllStudentsFromCall(call: String): Set[String] = ???

    override def getEvaluationsMapFromCall(call: String): Map[String, Int] = ???

    override def getResultsMapFromStudent(student: String): Map[String, String] = ???

    override def getBestResultFromStudent(student: String): Option[Int] = ???
  }

}