package u05lab

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test
import u05lab.code.ExamsManagerTest._

class Ex2ExamsManagerTest {

  val erf: ExamResultFactory = new ExamResultFactoryImpl();
  val em: ExamsManager = new ExamsManagerImpl();

  @Test def testExamResultsBasicBehaviour(): Unit ={
    // esame fallito, non c'è voto// esame fallito, non c'è voto

    assertEquals(erf.failed.getKind, Kind.FAILED())
    assertEquals(Option.empty, erf.failed.getEvaluation)
    assertFalse(erf.failed.cumLaude)
    assertEquals(erf.failed.toString, "FAILED()")

    // lo studente si è ritirato, non c'è voto
    assertEquals(erf.retired.getKind, Kind.RETIRED())
    assertEquals(Option.empty, erf.retired.getEvaluation)
    assertFalse(erf.retired.cumLaude)
    assertEquals(erf.retired.toString, "RETIRED()")

    // 30L
    assertEquals(erf.succeededCumLaude.getKind, Kind.SUCCEEDED())
    assertEquals(erf.succeededCumLaude.getEvaluation, Some(30))
    assertTrue(erf.succeededCumLaude.cumLaude)
    assertEquals(erf.succeededCumLaude.toString, "SUCCEEDED()(30L)")

    // esame superato, ma non con lode
    assertEquals(erf.succeeded(28).getKind, Kind.SUCCEEDED())
    assertEquals(erf.succeeded(28).getEvaluation, Some(28))
    assertFalse(erf.succeeded(28).cumLaude)
    assertEquals(erf.succeeded(28).toString, "SUCCEEDED()(28)")
  }

  // verifica eccezione in ExamResultFactory
  @Test
  def optionalTestEvaluationCantBeGreaterThan30() {
    try { erf.succeeded(32); assert(false) } catch { case _:IllegalArgumentException => }
  }

  @Test
  def optionalTestEvaluationCantBeSmallerThan18() {
    try { erf.succeeded(17); assert(false) } catch { case _:IllegalArgumentException => }
    prepareExams()
  }

  // metodo di creazione di una situazione di risultati in 3 appelli
  private def prepareExams() {
    em.createNewCall("gennaio");
    em.createNewCall("febbraio");
    em.createNewCall("marzo");

    em.addStudentResult("gennaio", "rossi", erf.failed); // rossi -> fallito
    em.addStudentResult("gennaio", "bianchi", erf.retired); // bianchi -> ritirato
    em.addStudentResult("gennaio", "verdi", erf.succeeded(28)); // verdi -> 28
    em.addStudentResult("gennaio", "neri", erf.succeededCumLaude); // neri -> 30L

    em.addStudentResult("febbraio", "rossi", erf.failed); // etc..
    em.addStudentResult("febbraio", "bianchi", erf.succeeded(20));
    em.addStudentResult("febbraio", "verdi", erf.succeeded(30));

    em.addStudentResult("marzo", "rossi", erf.succeeded(25));
    em.addStudentResult("marzo", "bianchi", erf.succeeded(25));
    em.addStudentResult("marzo", "viola", erf.failed);
  }

}
