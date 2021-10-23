#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/*main function*/
int main(void){
	char a;
	scanf("%c", &a);
	if(islower(a) >= 1){
		printf("%c\n", toupper(a));
	}else{
		puts("invalid input");
	}
	fflush(stdin);
	return 0;
}