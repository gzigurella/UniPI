#include <stdio.h>
#include <stdlib.h>

/* define boolean */
typedef enum{false, true} boolean;

/* define node */
typedef struct n{
	int info;
	struct n * next;
}Node;

/* define list */
typedef Node * List;

/* function prototypes */
List push			(List ls, int n);
boolean checkOrder 		(List * ls);
void dump			(List ls);
void freeList			(List ls);

/* main function */
int main(void){
	List l = NULL;
	int x;
	scanf("%d", &x);
	while(x >-1){
		l = push(l, x);
		scanf("%d", &x);
	}
	boolean status = checkOrder(&l);
    if(status == true){
        puts("True");
    }else{
        puts("False");
    }
	freeList(l);
	return 0;
}

/* functions body */
boolean checkOrder(List * ls){
	List * head = ls;
	List * tail = &(*ls)->next;

	if(*ls != NULL){
		if((*ls)->next != NULL){
            if( ((*head)->info) > ((*tail)->info) ){
                if((*tail)->next == NULL){
                    return 1;
                }
                return checkOrder(tail);
            }
			
		}
    }
    return 0;
}

List push(List ls, int n){
	Node * Elemento = (Node*)malloc(sizeof(Node));
	if(Elemento != NULL){
		Elemento->info = n;
		Elemento->next = ls;
		ls = Elemento;
	}
	return ls;
}

void dump(List ls){
	Node * aux;
	aux = ls;
	while(aux != NULL){
		printf("%d -> ", aux->info);
		aux = aux->next;
	}
	puts("NULL");
	return;
}

void freeList(List ls){
	Node * temp;
	while(ls != NULL){
		temp = ls;
		ls = ls->next;
		free(temp);
	}
	return;
}
