
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* define candidates list */
typedef struct candidate{
	char name[10];
	int votes;
	struct candidate *nextNode;
}Candidate;

/* define list of candidates */
typedef Candidate *List;

/* function prototypes */
void add_candidate		(List *l,char* name);
void incrment_Votes		(List *l, char* name);
int  search_name		(List *l, char* name);
void check_candidate	(List *l, char* name);
void print_winners	    (List l);
void dump_list		    (List l);
void order_list		    (List l);

int main(void){
	List list=NULL;
		FILE *fPtr;
		if ((fPtr = fopen("input.txt","r")) == NULL){
			puts("Impossibile aprire file");
			exit(EXIT_FAILURE);
		}else{
            /* read until the end of file */
			while(!feof(fPtr)){
				char name1[10];
				char name2[10];
				fscanf(fPtr,"%[^,],%s\n", name1, name2);
				check_candidate(&list, name1);
				check_candidate(&list, name2);

			}
			/* 
            Delete comment to show debug output

                dump_list(list);
                puts("");

            */
            order_list(list);
			print_winners(list);
		}	
	return 0;
}

void dump_list(List l){

	while(l!=NULL){
		printf("Nome:%s\tVoti:%d\n", l->name, l->votes);
		l= l->nextNode;
	}
}

void check_candidate(List *l, char* name){
    /* search for a candidate among the list */
	if (search_name(l, name) == 1){
        /* increment number of votes if found */
		incrment_Votes(l, name);
	} 
	else{
        /* append to the end of the list otherwise */
		add_candidate(l, name);
	}
}

int search_name(List *l, char* name){
    /* checks if the list is empty */
	if (*l == NULL){
		return 0;
	}
    /* checks if the current element is the one we're looking for */
	if (strcmp((*l)->name, name) == 0){
		return 1;
	}else{
        /* if it's not --> recursive call to check next pointer */
		return search_name(&(*l)->nextNode, name);
	}
	return 0;
}

void incrment_Votes(List *l, char* name){
    /* if it matches the names, then increments the votes */
	if (strcmp((*l)->name, name) == 0){
		(*l)->votes+=1;
		return;
	}else{
        /* otherwise keep looking in the next node */
		incrment_Votes(&(*l)->nextNode, name);
	}
}

void add_candidate(List *l, char* name){
    /* allocate memory for a new node */
	Candidate *newStud = calloc(1, sizeof(Candidate));
    /* add data to the node */
	newStud->votes=1;
	strcpy(newStud->name, name);
	if (newStud!=NULL){
		if (*l != NULL){
            /* push element as new head of the list */
			newStud->nextNode = *l;
			*l = newStud;  
		}else{
            /* add element to empty list */
			*l = newStud;
		}
	}
	else{
		puts("No memory left");
		exit(EXIT_FAILURE);
	}

}

void order_list(List l){
    int size_list=0;
	List aux = l;
	while(aux!=NULL){
        /* check length of the list */
		size_list++;
		aux = aux->nextNode;
	}
	/* apply selection sort algorithm */
	size_t i, j;
	List maxPtr = l;
	aux = l->nextNode;
	for (i = 0; i < size_list-1; ++i){
		aux = maxPtr->nextNode;
		for (j = i+1; j < size_list; ++j){

			if (aux->votes > maxPtr->votes){
				/* swap nodes */
				char tempNome[10];
				int tempVoti;

				strcpy(tempNome, aux->name);
				tempVoti = aux->votes;

				strcpy(aux->name, maxPtr->name);
				aux->votes = maxPtr->votes;

				strcpy(maxPtr->name, tempNome);
				maxPtr->votes = tempVoti;

			}else if(aux->votes == maxPtr->votes){
				if (strcmp(aux->name, maxPtr->name) < 0){
					/* order names lexicographically */
					char tempNome[10];
					strcpy(tempNome, aux->name);
					strcpy(aux->name, maxPtr->name);
					strcpy(maxPtr->name, tempNome);
				}
			}
			aux = aux->nextNode;
		}
		maxPtr = maxPtr->nextNode;
	}

}

void print_winners(List l){
	List aux = l;
    /* after the list is ordered the first two candidates have the most votes */
	for (size_t i = 0; i < 2; ++i){
		printf("%s con %d voti\n", aux->name, aux->votes);
		aux=aux->nextNode;
	}
}