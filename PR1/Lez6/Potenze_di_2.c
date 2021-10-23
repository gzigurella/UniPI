#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
int read_positive_int(void);
int Pot2 (int n);

/* main function */
int main(void){
    int x = read_positive_int();
    printf("%d\n", Pot2(x));
    fflush(stdin);
    return 0;
}

/* functions body */
int read_positive_int(void){
	int a;
	while(scanf("%d", &a) != 1 || a < 0){
        puts("Errore. Inserisci un numero intero positivo");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return a;
}

int Pot2(int n){
    if(n == 1){
        return 2;
    }else{
        return 2*Pot2(n-1);
    }
}