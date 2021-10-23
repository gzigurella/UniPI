	.data
sorg:	.string "Questa stringa va copiata!\n"
dest:	.skip 25*1 @char is a 1byte type, int are 4bytes, long int are 8bytes

        .text
        .global main

main:
        LDR R1, =sorg   @mem string
        LDR R0, =dest   @locate mem for UNinitialized string
        MOV R4, #0      @index offset
        PUSH {LR}       @save link register

strcopia:
        LDRB R2, [R1, R4]       @Load character R2 from sorg
        STRB R2, [R0, R4]       @Store character R2 to dest
        CMP R2, #0              @check null character
        BEQ stampa              @jump to stampa
        ADD R4, R4, #1          @increment index
        BL strcopia             @jump to strcopia
stampa:
        BL printf       @call printf function
        POP {LR}        @restore link register

fine:
        MOV R0, #0      @define exit number
        MOV PC, LR      @return main
        MOV R7, #1      @syscall 1 (exit type)
        SVC 0           @call exit function

