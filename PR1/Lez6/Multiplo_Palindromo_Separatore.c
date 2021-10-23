#include <stdio.h>
#include <stdlib.h>

/* function prototypes */
int palindroma(void);

/* main function */
int main(void) {
for(int i = 0; i < 3; i++){
	if(palindroma()){
		if (getchar() == '\n'){ 
			printf("palindrome\n");
			}
		}else{
			printf("non palindrome\n");
			while(getchar() != '\n');
		}
	}
}

/* functions body */
int palindroma(void) {
	char c;
	c = getchar(); // <-- reads from stdin
	if (c == '*') {
		return 1;
	}
	if(palindroma()){
		return (c == getchar()); // <-- after '*' check the second string with the stored one
	}else{
		return 0;
	}
}