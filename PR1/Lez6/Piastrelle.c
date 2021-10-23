#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
long long int conta_possibilita(int n);
int read_positive_int(void);

/* main function */
int main(void) {
	int x = read_positive_int();
	printf("%lld\n", conta_possibilita(x));
	fflush(stdin);
	return 0;
}

/* functions body */
int read_positive_int(void){
		int a;
	while(scanf("%d", &a) != 1 || a < 0){
		puts("Errore. Inserisci un intero positivo.");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return a;
}

long long int conta_possibilita(int n){
	if(n == 1){
		return 2;
	}else if(n == 2){
		return 7;
	}else{
		return 2*conta_possibilita(n-1)+3*conta_possibilita(n-2);
	}
}