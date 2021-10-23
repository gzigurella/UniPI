#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
int read_positive_int(void);
int inverti_numero(int n);
void stampa_cifre(int n);
void stampa_cifre_inverso(int n);

/* main function */
int main(void){
    int x = read_positive_int();
    stampa_cifre(x);
    stampa_cifre_inverso(x);
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

void stampa_cifre(int n){
    if(n == 0){
        printf("%d\n", n);
        return;
    }else{
        if(n%10 != n){
            printf("%d\n", n % 10);
            stampa_cifre(n / 10);
        }else{
            printf("%d\n", n);
            return;
        }
    }
    return;
}

void stampa_cifre_inverso(int n){
    if(n != 0){
        int k = inverti_numero(n);
        stampa_cifre(k);
    }else{
        puts("0");
        return;
    }
    return;
}

int inverti_numero(int n){
    static int r;
    
    if(n == 0){
        return 0;
    }

    r = r * 10;
    r = r + (n % 10);
    inverti_numero(n/10);
    return r;
}