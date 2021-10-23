#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int op;
	float init, perc;
	scanf("%d\n", &op);
	if(op < 0 || op > 1){
		puts("invalid input");
		fflush(stdin);
		return 0;
	}else{
		scanf("%f\n", &init);
		if(init < 0){
			puts("invalid input");
			fflush(stdin);
			return 0;
		}
		scanf("%f\n", &perc);
		if(perc < 0 || perc > 100){
			puts("invalid input");
			fflush(stdin);
			return 0;	
		}
	}
	if(op == 1){
		float ivato = init + init * (perc/100);
	    printf("%20s%20s%20s\n", "Prezzo_Init", "Percentuale", "Prezzo_ivato");
	    printf("%20.2f%20.2f%20.2f\n", init, perc, ivato);  
	}else{
		float off = init - init * (perc/100);
	    printf("%20s%20s%20s\n", "Prezzo_Init", "Percentuale", "Prezzo_scontato");
		printf("%20.2f%20.2f%20.2f\n", init, perc, off);  
	}
	fflush(stdin);
	return 0;
}