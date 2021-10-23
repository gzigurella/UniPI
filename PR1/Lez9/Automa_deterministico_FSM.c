#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* define constant */
#define MAX_LINE 101
#define MAX_STATE_CHAR 10
#define DELIM ";"

/* define states */
typedef struct t{
	char input;
	char * nextState;
	struct t * nextTransition;
}Transition;

typedef struct s{
	char * state;
	Transition * transitionList;
}State;

/* function prototypes */
int checkLast(char * s, char ** finalStates, int n);
char* move(char s, char * currentState, State * m, int nStates);
int FSM(char * string, State * m, int nStates, char ** finalStates, int nFinalStates);
void addElement(Transition ** List, char* nextS, char s);

/* main functions */
int main(void){
	/* initialize index var */
	int i;
	/* initialize input var */
	char line[MAX_LINE];
	int states_number;
	scanf("%d", &states_number);
	int nFinalStates = 0;
	
	/* initialize FSM behaviour on a matrix */
	State * fsmTable = malloc(sizeof(State) * states_number);
	/* allocating extra memory for final states */
	char ** finalStates = malloc(states_number * sizeof(char*));

	for(i = 0; i < states_number; i++){
		/* read input from stdin, ignoring new lines */
		scanf(" %[^\n]", line);
		char * token = strtok(line, DELIM);

		/* the first token is the State name */
		fsmTable[i].state = malloc(sizeof(char)*(strlen(token)+1));
		strcpy(fsmTable[i].state, token);
		
		/* initialize transitions list and add transition */
		fsmTable[i].transitionList = NULL;
		token = strtok(NULL, DELIM);
		while(token != NULL){
			char * state, state_char;
			state = malloc(sizeof(char)*MAX_STATE_CHAR);
			/* search state instructions from the second token */
			sscanf(token, "(%[^,],%c)", state, &state_char);
			/* add transition */
			addElement(&fsmTable[i].transitionList, state, state_char);
			/* check if state is a Final state */
			if(state[0] == 'F'){
				finalStates[nFinalStates] = state;
				nFinalStates +=1;
			}
			token = strtok(NULL, DELIM);
		}
	}
	/* read strings while it's different from 'FINE' */
	scanf(" %[^\n]", line);
	while(strcmp(line, "FINE")!= 0){
		/* check if the string is recognized */
		if(FSM(line, fsmTable, states_number, finalStates, nFinalStates) == 1){
			printf("%s\n", line);
		}
		scanf(" %[^\n]", line);
	}
	return 0;	
}

/* functions body */
	/* recursively add one element at the end of a list of transitions */
void addElement(Transition ** List, char* nextS, char s){
	/* if list is empty add it as head */
	if(*List == NULL){
		*List = malloc(sizeof(Transition));
		(*List)->input = s;
		(*List)->nextState = nextS;
		(*List)->nextTransition = NULL;
	}else{
		/* otherwise append it to the end of the list */
		addElement(&((*List)->nextTransition), nextS, s);
	}
	return;
}

	/* checks if a state is in final state */
int checkLast(char * s, char ** finalStates, int n){
	int i;
	for(i = 0; i < n; i++ )
		if(strcmp(s, finalStates[i]) == 0)
			return 1;
	return 0;
}

	/* move from the current state using the proper transition, 
	returns "NULL" if next transition is not found */
char * move(char s, char * currentState, State * m, int nStates){
	int i;
	for(i = 0; i < nStates; i++){
		if(strcmp(m[i].state, currentState) == 0){
			/* find transition for the state */
			Transition * t = m[i].transitionList;
			while(t != NULL){
				if(t->input == s)
					return t->nextState;
				t = t->nextTransition;
			}
		}
	}
	/* state or transition not found */
	return "NULL";
}

int FSM(char* string, State *m, int nStates, char ** finalStates, int nFinalStates){
	/* start the FSM and set the initial state */
	char * currentState = m[0].state;
	int i;
	for(i = 0; i < strlen(string); i++){
		/* move between states */
		currentState = move(string[i], currentState, m, nStates);
		/* check if FSM recognized the state */
		if(strcmp(currentState, "NULL") == 0){
			return 0;
		}
	}
	/* check if FSM reached the final state */
	if(checkLast(currentState, finalStates, nFinalStates))
		return 1;
	return 0;
}