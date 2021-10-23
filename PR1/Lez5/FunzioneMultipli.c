#include <stdio.h>
#include <stdlib.h>

void multipli(int a, int b);
int read_int();

/*main function*/
int main(void){
	int N, M;
	N = read_int();
	M = read_int();
	
	multipli(N, M);
	
	fflush(stdin);
	return 0;
}

int read_int(){
	int a;
	while(scanf("%d", &a) != 1){
		puts("Inserisci un intero.");
		scanf("%*[^\n]");
		scanf("%*c");
	}
	return a;
}

void multipli(int a, int b){
	int i = 1;
	while(b*i < a){
		printf("%d\n", b*i);
		i++;
	}
	return;
}