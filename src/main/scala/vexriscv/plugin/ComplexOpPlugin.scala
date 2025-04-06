package vexriscv.plugin

import vexriscv.{VexRiscv, Stageable, DecoderService}
import spinal.core._

class ComplexOpPlugin extends Plugin[VexRiscv] {
  // Flag to identify our instruction in the pipeline
  object IS_COMPLEXOP extends Stageable(Bool)

  override def setup(pipeline: VexRiscv): Unit = {
    import pipeline.config._

    // Tell the decoder about our instruction
    val decoderService = pipeline.service(classOf[DecoderService])
    decoderService.addDefault(IS_COMPLEXOP, False)
    decoderService.add(Seq(
      MaskedLiteral("0000000----------000-----0001011") -> Seq(
        (IS_COMPLEXOP, True),
        (REGFILE_WRITE_VALID, True),  // We write to rd
        (BYPASSABLE_EXECUTE_STAGE, False),  // Multi-cycle operation
        (RS1_USE, True),  // Use rs1 (a)
        (RS2_USE, True)   // Use rs2 (b)
      )
    ))
  }

  override def build(pipeline: VexRiscv): Unit = {
    import pipeline._
    import pipeline.config._

    // Add logic in the execute stage
    execute plug new Area {
      import execute._

      // When our instruction is detected
      when(input(IS_COMPLEXOP)) {
        // Read operands (only rs1 and rs2 for now)
        val a = input(RS1).asSInt
        val b = input(RS2).asSInt
        // Hardcode c, d, e for now
        val c = 8
        val d = 2
        val e = 10

        // Calculate the expression: a * b - c / d + e
        val result = (a * b) - (c / d) + e

        // Write result to rd
        insert(REGFILE_WRITE_DATA) := result.asBits
      }
    }
  }
}
