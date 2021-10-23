#include <stdio.h>
#include <stdlib.h>
#define GREGORY_CONST 4

int read_positive_int(void);
void approx_pi(int a);

/*main function*/
int main(void){
	int N = read_positive_int();
	approx_pi(N);
	fflush(stdin);
	return 0;
}

int read_positive_int(void){
	int a;
	while(scanf("%d", &a) != 1 || a < 0){
		puts("Inserisci un intero positivo.");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return a;
}

void approx_pi(int a){
	int b = 1;
	float d = 1;
	float pi = 0;
	do{
		if( b % 2 != 0){
			pi += (GREGORY_CONST/d);
		}else if( b % 2 == 0){
			pi -= (GREGORY_CONST/d);
		}
		d +=2;
		b++;
	}while( b <= a);
	printf("%.6f\n", pi);
	return;
}