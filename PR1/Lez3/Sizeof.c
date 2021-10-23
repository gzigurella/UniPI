#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int n;
	while(scanf("%d", &n) != 1){
		scanf("%*c");
		puts("ERROR_NAN, the input is not a number");
	}

	/*bytes needed to allocate n variables */
	printf("%ld\n", sizeof(int)*n);
	fflush(stdin);
	return 0;
}