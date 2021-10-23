	.data
arrFst: .word 10, 9, 8, 7, 6 ,5 ,4 ,3, 2, 1	@Array of 4bytes elements
arrSnd: .skip 10*4	@UNinitialized array

	.text
	.global main

main:	
	LDR R1, =arrFst	@mem first array
	LDR R2, =arrSnd	@mem second array
	MOV R0,#0		@initialize offset index
	PUSH {R1, LR}		@save array and link register

loop:
	CMP R0, #40		@check if we reach last number 10*40
	BGE fine		@jump to fine
	LDR R4, [R1, R0]	@load byte number
	STR R4, [R2, R0]	@store byte number in second array
	ADD R0, #4		@increment index by a word size (4bytes)
	BL loop		@jump to loop

fine:
	POP {R1, LR}		@return first array and link register
	MOV R0, #0		@define exti number
	MOV PC, LR		@return to main
	MOV R7, #1		@syscall 1 (exit type)
	SVC 0			@call exit function

