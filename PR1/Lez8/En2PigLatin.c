#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_STR 128
#define MAX_TKN 20
#define DELIM " "

/* function prototypes */
char* translate_str(char * s);
char* read_str(char * s);
char* lookup_str(char * s);
char* quit(char * s);
char* (*ptr_func[4]) (char * s) = {read_str, lookup_str, translate_str, quit};

/* auxiliary function prototypes */
void choice( int* choicePtr); 
char* en2piglatin(char * s);

/* main functions */
int main(void){
    char * str = (char*)malloc(sizeof(char) * MAX_STR);
    int choice_pointer;
    choice(&choice_pointer);
    while(choice_pointer > 0 && choice_pointer <= 4){
        (*ptr_func[choice_pointer-1])(str);
        choice(&choice_pointer);
    }
    free(str);
    return 0; 
}

/* functions body */
void choice(int* choicePtr) {
    /* asks for user choice until it gets a valid input */
	printf("Scegliere l\'operazione:\n");
	while (scanf("%d", choicePtr) != 1) {
		printf("Invalid input.\nScegliere l\'operazione:\n");
		while (getchar() != '\n');
	}
	while (getchar() != '\n');
    return;
}

char* translate_str(char * s){
    /* allocate memory for token and copy of input string */
    char * token = (char*)malloc(sizeof(char)*MAX_TKN);
    char * str_cpy = (char*)malloc(sizeof(char)*(strlen(s)+1));

    strcpy(str_cpy, s);

    token = strtok(str_cpy, DELIM);
    while(token != NULL){
        if (token != NULL) {
				printf("%s", en2piglatin(token));
				token = strtok(NULL, DELIM);
				if (token != NULL) {
				putchar(' ');
			}
		}
    }
    putchar('\n');
    free(token);
    free(str_cpy);
    return s;
}

char* en2piglatin(char * s){
    char* str_cpy = 0;
	size_t uppercase = 0; 
	if (s != NULL) {
		str_cpy = (char*)malloc(sizeof(char)*(strlen(s) + 1));
		if (str_cpy != NULL) {
			strcpy(str_cpy, s);
            /* check if string starts with a vowel */
			if (isupper(str_cpy[0])) { 
				str_cpy[0] = tolower(str_cpy[0]); 
				uppercase = 1;
			}
			switch (str_cpy[0]) {
				case 'a':
				case 'e':
				case 'i':
				case 'o':
				case 'u':
					str_cpy = realloc(str_cpy, (strlen(str_cpy) + 4) * sizeof(char)); 
					if (str_cpy != NULL) {
						strcat(str_cpy, "way");
					}
					break;
				default: {
					/* if string doesn't start with a vowel then move every vowel */
					char* firstVocalPtr = &str_cpy[1], * tmp = 0;

                    /* break string at first vowel */
					firstVocalPtr = strpbrk(firstVocalPtr, "aeiouy"); 
					tmp = malloc((strlen(str_cpy) - strlen(firstVocalPtr) + 1) * sizeof(char));
					if (tmp != NULL) {

                        /* copy the first part of the string */
						strncpy(tmp, str_cpy,(strlen(str_cpy) - strlen(firstVocalPtr)));
						str_cpy = realloc(str_cpy, (strlen(str_cpy) + 4) * sizeof(char));
						if (str_cpy != NULL) {
							str_cpy = firstVocalPtr; 

                            /* unify separated strings */
							strcat(str_cpy, tmp); 
							strcat(str_cpy, "ay");
						}
					}
				}
			}
            /* if first letter was uppercase return it to an uppercase letter */
			if (uppercase != 0) {
				str_cpy[0] = toupper(str_cpy[0]);
			}
			return str_cpy;
		}
	}
	return NULL;
}

char* read_str(char * s){
    puts("Inserire stringa:");
    /* reads string from stdin with max length MAX_STR */
    fgets(s, MAX_STR, stdin);

    /* reallocate memory for str */
    s = realloc(s, strlen(s)*sizeof(char));

    /* remove newline at the end of string */
    if(s[strlen(s)-1] == '\n'){
        s[strlen(s)-1] = '\0';
    }
    return s;
}

char* lookup_str(char * s){
    /* allocate memory to copy string */
    char * str = (char*)malloc(sizeof(char)*(strlen(s)+1));
    strcpy(str, s); 

    /* capitalize string */
    for(size_t i = 0; i < strlen(str); i++){
        str[i] = toupper(str[i]);
    }

    /* shows last input string in CAPS*/
    printf("%s\n", str);
    return s;
}

char* quit(char *s){
    /* free memory occupied by string */
    puts("Ciao!");
    free(s);

    /* quit from the program */
    exit(EXIT_SUCCESS);
    return s;
}