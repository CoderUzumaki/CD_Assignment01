.global _start

_start:
    # Load values into registers
    li x1, 5    # a = 5
    li x2, 3    # b = 3

    # Call our custom instruction: complexop x6, x1, x2
    .word 0x0000500B  # CUSTOM0 opcode, rd=x6, rs1=x1, rs2=x2

    # Result in x6 should be (5 * 3 - 8 / 2 + 10 = 15 - 4 + 10 = 21)

    # Infinite loop
    j .
