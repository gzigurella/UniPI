#include <stdio.h>
#include <stdlib.h>

/*main function*/
int main(void){
	for(size_t i = 0; i < 10; i++){
		if(i == 0 || i == 9){
			puts("***");
		}else{
			puts("*X*");
		}
	}
	return 0;
}