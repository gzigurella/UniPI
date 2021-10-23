#include <stdio.h>
#include <stdlib.h>

/* define node */
typedef struct n{
    int info;
    struct n * next;
}Node;

/* define list */
typedef Node* List;

/* function prototypes */
int  productBorders     (List l, int N, int M);
int  isEmpty            (List l);
void dump               (List l);
void freeList           (List l);
List push               (List l, int x);
void InserOrd           (List * l);


/* functions body */
    /* execute exercise request */
void SortedInsert(List * head, List newNode){
	Node dummy;
	List current = &dummy;
	dummy.next = *head;

    /* find the right position */
	while (current->next != NULL && current->next->info < newNode->info){
        current = current->next;
    }

	newNode->next = current->next;
	current->next = newNode;
	*head = dummy.next;
}

void InserOrd(List * l){
    Node *curr, *succ, *aux;
    aux = NULL;
    curr = *l;

    while(curr != NULL){
        succ = curr->next;

        /* insert node in ordered fashion */
        SortedInsert(&aux, curr);
        curr = succ;
    }
    *l = aux;
    return;
}

    /* checks if given list is empty */
int isEmpty(List l){
    return l == NULL;
}

    /* add element to the top */
List push(List l, int x){
    /* allocate a new element */
    Node * newPtr = (Node*)malloc(sizeof(Node));
    /* inser value in list and return */
    newPtr->info = x;
    newPtr->next = l;
    return newPtr;
}

    /* prints list elements */
void dump(List l){
    List aux = l;
    while(aux != NULL){
        printf("%d -> ", aux->info);
        aux = aux->next;
    }
    puts("NULL");
    return;
}

    /* free list memory */
void freeList(List l){
	Node * temp;
	while(l != NULL){
		temp = l;
		l = l->next;
		free(temp);
	}
	return;
}

/* main function */
int main(void){
    List lista = NULL;
    int x;

    /* read list values */
    scanf("%d", &x);
    while(x > -1){
        lista = push(lista, x);
        scanf("%d", &x);
    }

    /* order, print and free list */
    InserOrd(&lista);

    dump(lista);
    freeList(lista);
    return 0;
}