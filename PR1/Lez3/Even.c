#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	int x;
	while(scanf("%d", &x) != 1){
		scanf("%*c");
		puts("ERROR_NAN, input is not a number");
	}
	if(x % 2 == 0){
		printf("%d\n",1);
	}else{
		printf("%d\n",0);
	}
	fflush(stdin);
	return 0;
}