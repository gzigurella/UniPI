#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
void check(int n);
int read_int(void);

/* main function */
int main(void) {
	int x = read_int();
	check(x);
	fflush(stdin);
	return 0;
}

/* functions body */
int read_int(void){
		int a;
	while(scanf("%d", &a) != 1){
		puts("Errore. Inserire un numero intero positivo oppure un numero intero negativo per terminare");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return a;
}

void check(int n){
	static int s;
	if(n < 0 && s == 0){
		puts("Sequenza in ordine strettamente crescente");
		return;
	}else if(n < 0 && s > 0){
		puts("Sequenza non in ordine strettamente crescente");
		return;
	}else if(n > 0){
		static int c;
		if(c >= n){
			s = s+1;
			check(read_int());
			return;
		}else{
			c = n;
			check(read_int());
		}
	}
	return;
}