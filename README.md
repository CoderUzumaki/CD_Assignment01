# Compiler Design Assignment_01 - Design a Custom Instruction for a given expression.

## VexRiscv Custom Instruction: `complexop`

This project demonstrates the addition of a custom instruction `complexop` to the VexRiscv processor. The `complexop` instruction calculates the expression `a * b - c / d + e`, where `a` and `b` are read from registers `rs1` and `rs2`, and `c`, `d`, and `e` are hardcoded as 8, 2, and 10, respectively. The result is written to the destination register `rd`.

This README provides a step-by-step guide to set up the environment, install dependencies, clone the repository, generate the Verilog file, and verify the custom instruction in the instruction set.

---
## Prerequisites

- **Windows Subsystem for Linux (WSL):** You need to have WSL installed with Ubuntu 20.04 or higher.

---
## Step-by-Step Instructions

### Step 1: Update Package Lists
Open a terminal in your WSL Ubuntu 20.04 environment and update the package lists:
```bash
sudo apt update
```

### Step 2: Install Java
Install the Java Development Kit (JDK), which is required to run SBT (Scala Build Tool):
```bash
sudo apt install openjdk-11-jdk
```

### Step 3:  Install SBT (Scala Build Tool)
SBT is required to build the VexRiscv project. Install SBT by downloading the binary directly:
```bash
wget https://github.com/sbt/sbt/releases/download/v1.6.0/sbt-1.6.0.tgz
tar -xzf sbt-1.6.0.tgz
sudo mv sbt /opt/sbt
echo 'export PATH=$PATH:/opt/sbt/bin' >> ~/.bashrc
source ~/.bashrc
```

Verify SBT installation
```bash
sbt --version
```

### Step 4: Install the RISC-V Toolchain
Install the RISC-V GCC toolchain to compile and disassemble the test program:
```bash
sudo apt install gcc-riscv64-unknown-elf
```

verify the installation:
```bash
riscv64-unknown-elf-gcc --version
```

### Step 5: Clone the Repository
Clone the CD_Assignment01 repository to get the base project
```bash
git clone https://github.com/CoderUzumaki/CD_Assignment01.git
cd CD_Assignment01
```

### Step 6: Generate the Verilog File
Run the GenFull application to generate the Verilog file for the VexRiscv processor with the custom complexop instruction:
```bash
sbt "runMain vexriscv.demo.GenFull"
```

This will generate a file named VexRiscv.v in the current directory. Verify its presence:
```bash
ls -l VexRiscv.v
```

### Step 7: Compile the Test Program
I have created a test file called `test_complexop.S` which is present in the `CD_Assignment` directory. Compile this test program to generate an ELF binary
```bash
riscv64-unknown-elf-gcc -march=rv32i -mabi=ilp32 -nostdlib -o test_complexop.elf test_complexop.S
```

### Step 8: Verify the Custom Instruction
Use `objdump` to disassemble the ELF binary and verify that the custom `complexop` instruction is present:
```bash
riscv64-unknown-elf-objdump -d test_complexop.elf
```

You should see output similar to:
```
00000000 <_start>:
   0:   00500093   li    x1,5
   4:   00300113   li    x2,3
   8:   0000500b   .word 0x0000500b  # My custom instruction
   c:   0000006f   j     c <_start+0xc>
```

---
## Results

- ### Custom Instruction Code
    - The `CustomInstruction.scala` file:
    ```bash
    cat src/main/scala/vexriscv/demo/CustomInstruction.scala
    ```

- ### Test Program:
    - The `test_complexop.S` file:
    ```bash
    cat test_complexop.S
    ```

---
## Note

I have used the [VexRiscv](https://github.com/SpinalHDL/VexRiscv) GitHub repository, for making this project.

This project is an academic assignment, assigned to me by my Compiler Design Professor, Dr. Sonal Yadav.


---
    Submitted By:
        Abhinav Mishra
        23115003
        B.Tech. CSE, 4th Sem
        NIT Raipur

    Submitted To:
        Dr. Sonal Yadav
        Assistant Professor
        NIT Raipur
---
