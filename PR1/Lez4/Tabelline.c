#include <stdio.h>

/*main function*/
int main(void){
	int n,i;
	while(scanf("%d", &n) != 1 || n<0){
    	printf("Incorretto. Inserisci un intero positivo.\n");
    	scanf("%*c");
	}
	for(i=1; i<=10; i++){
		printf("%d\n", n*i);
	}
}