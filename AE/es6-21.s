	.data
arr:	.word 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10	@arr of 4bytes elements

	.text
	.global main

main:
	LDR R3, =arr	@load mem array
	MOV R0, #0	@initialize index arr
	PUSH {LR}	@memorize link register
	
loop:
	CMP R0, #40		@check index value
	BGE fine		@jump to fine
	LDR R4, [R3, R0]	@load value
	LSL R4, R4, #7		@multiply by 128 = 2^7
	STR R4, [R3, R0]	@store new value in arr
	BL loop		@jump to loop
	
fine:
	POP {LR}	@take link register from stack
	MOV R0, #0	@define exit number
	MOV PC, LR	@return main
	MOV R7, #1	@syscall 1 (exit type)
	SVC 0		@call exit function
