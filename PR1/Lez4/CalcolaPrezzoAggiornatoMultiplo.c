#include <stdio.h>
#include <stdlib.h>

void operazioni(int x);

/*main function*/
int main(void){
	int op;
	
	do{
		while(scanf("%d", &op) != 1 || op > 1){
			puts("scelta non valida");
			scanf("%*[^\n]");
			scanf("%*c");
		}
		operazioni(op);
	}while(op >= 0);
	if(op < 0){
		fflush(stdin);
		return 0;
	}
}

void operazioni(int x){
	if(x < 0){
		return;
	}
	float init, perc;
	while(scanf("%f", &init) != 1 || init < 0){
		puts("Prezzo non valido");
		scanf("%*[^\n]");
		scanf("%*c");		
	}
	while(scanf("%f", &perc) != 1 || perc < 0 || perc > 100){
		puts("Percentuale non valida");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	if(x == 1){
		/* Prezzo con Iva */

		float ivato = init + init * (perc/100);
	    printf("%20s%20s%20s\n", "Prezzo_Init", "Percentuale", "Prezzo_ivato");
	    printf("%20.2f%20.2f%20.2f\n", init, perc, ivato);
	    return;
	}else if(x == 0){
		/* Prezzo con Sconto */

		float ivato = init - init * (perc/100);
	    printf("%20s%20s%20s\n", "Prezzo_Init", "Percentuale", "Prezzo_scontato");
	    printf("%20.2f%20.2f%20.2f\n", init, perc, ivato); 
	    return;
	}
}