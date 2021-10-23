#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* define constant */
#define MAX_LINE_SIZE 101

/* main function */
int main(void) {
	int num_seq = 0; 
	int num_nuc = 0; 
	int len_max = 0; 
	char str[MAX_LINE_SIZE]; 

	/* temporary and boolean values */
	int temp_nuc = 0; 
	int second_temp_nuc = 0; 
	/* if status == 1 we're still reading the sequence */
	int status = 0; 

	/* Open file in read mode*/
	FILE* ptrFile;
	ptrFile = fopen("input.txt", "r");
	/* Execute check until the EOF */
	while (!feof(ptrFile)) {
		fgets(str, MAX_LINE_SIZE, ptrFile); 
		if (str[0] == '>') {
			num_seq++; 
			status = 0;
		}
		else if (str[0] == 'A' || str[0] == 'T' || str[0] == 'C' || str[0] == 'G'){
			/* checks if we reached the end of the first half of the sequence */
			if (status == 0) {
				temp_nuc = (int)(strlen(str) - 1); 
				num_nuc += (int)(strlen(str) -1); 
				status = 1; 
			}else if(status == 1){
				int temp_nuc2 = (int)(strlen(str) - 1);
				/* compute final length of the sequence */
				second_temp_nuc = temp_nuc + temp_nuc2; 
				num_nuc += temp_nuc2; 
				status = 0; 
			}
		}
		if (temp_nuc > len_max) {
			len_max = temp_nuc;
		}
		else if (second_temp_nuc > len_max) {
			len_max = second_temp_nuc;
		}
	}
	printf("Numero sequenze: %d\nLunghezza della sequenza più lunga: %d\nNumero totale di nucleotidi: %d\n", num_seq, len_max, (num_nuc-temp_nuc)); //Devo rimuovere il valore ultimo temp_nuc da num_nuc in quanto il while nell'ultimo ciclo duplica il valore a causa del \n letto
	return 0;
}