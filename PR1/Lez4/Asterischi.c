#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int n;
	while(scanf("%d", &n) != 1 || n < 0){
		scanf("%*[^\n]");
		scanf("%*c");
		puts("Incorretto. Inserisci un intero positivo.");
	}
	while(n >= 0){
		for(size_t i = 0; i < n; i++){
			printf("*");
		}
		n = n-2;
		printf("\n");
	}
	fflush(stdin);
	return 0;
}