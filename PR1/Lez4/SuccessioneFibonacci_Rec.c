#include <stdio.h>
#include <stdlib.h>

int Fib(int i);

/*main function*/
int main(void){
	int n;
	while(scanf("%d", &n) != 1 || n < 0){
		scanf("%*c");
		puts("Inserire un intero positivo");
	}
	size_t c = 0;
	while(Fib(c) < n+1){
		printf("%d\n", Fib(c));
		c++;
	}
	fflush(stdin);
	return 0;
}

int Fib(int i){
	if(i == 0 || i == 1){
		return i;
	}
	else{
		return(Fib(i-1)+Fib(i-2));
	}
}