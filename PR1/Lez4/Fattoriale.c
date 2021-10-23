#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int x;
	int fact = 1;
	while(scanf("%d", &x) != 1 || x < 0){
		scanf("%*c");
		puts("Incorretto. Inserisci un intero positivo.");
	}
	for(; x >= 1; x--){
		fact *= x;
	}
	printf("%d\n", fact);
	fflush(stdin);
	return 0;
}