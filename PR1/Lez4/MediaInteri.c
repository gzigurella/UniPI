#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int n, r;
	float sum;
	while(scanf("%d", &n) != 1 || n < 0){
		scanf("%*c");
		puts("Incorretto. Inserisci un intero positivo.");
	}
	size_t i = 0;
	while(i < n){
		while(scanf("%d", &r) != 1){
			scanf("%*c");
			puts("Incorretto. Inserisci un intero.");
		}
		sum += r;
		i++;
	}
	printf("%.2f\n", (sum/n));
	fflush(stdin);
	return 0;
}