	.data
arr:	.skip 10*4 @Initializes array of 10 int elements with 0s (4 bytes)

	.text
	.global main

main:
	LDR R0, =arr	@mem read arr
	MOV R1, #3	@mem set num
	MOV R2, #1	@define condition fulfilled
	MOV R4, #0	@arr index
	PUSH {LR}	@save link register

confronta:
	CMP R4, #10		@check index
	BGE fine		@jump to fine
	CMP R1, R4		@set flags
	BPL positive		@jump to positive
	BMI negative		@jump to negative

positive:
	STR R2, [R0, R4, LSL #2]	@store result
	ADD R4, R4, #1          	@increment index
	BL confronta			@jump back to confronta

negative:
	ADD R4, R4, #1          	@increment index
	BL confronta			@jump back to confronta

fine:
	POP {LR}			@restore link register
	MOV R0, #0			@define exit number
	MOV PC, LR			@return main
	MOV R7, #1			@syscall 1 (exit type)
	SVC 0				@call exit function

