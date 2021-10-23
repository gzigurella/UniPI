	.data
arr:	.word 10, 9, 8, 7, 6, 5 ,4 ,3 ,2 ,1

	.text
	.global main

main:
	LDR R0, =arr	@read mem arr
	MOV R1, #0	@index offset
	PUSH {LR}	@save link register

loop:
	CMP R1, #10			@check index value
	BGE fine			@jump to fine
	LDR R4, [R0, R1, LSL #2]	@read arr value
	LSR R4, #1			@divide value by 2
	STR R4, [R0, R1, LSL #2]	@store new value
	ADD R1, R1, #1			@increment index
	BL loop				@jump to loop

fine:
	POP {LR}	@restore link register
	MOV R0, #0	@define exit value
	MOV PC, LR	@return main
	MOV R7, #1	@syscall 1 (exit type)
	SVC 0		@call exit function

