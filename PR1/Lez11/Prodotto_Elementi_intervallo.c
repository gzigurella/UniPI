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
List order_append       (List l, int x);
List push               (List l, int x);


/* functions body */
    /* execute exercise request */
int productBorders(List l, int N, int M){
    if(isEmpty(l)){
        return -1;
    }
    int i = 1;
    List aux;
    for(aux = l; aux != NULL ; aux = aux->next){
        if(aux->info > N && aux->info < M){
            i *= aux->info;
        }
    }
    return i;
}

    /* checks if given list is empty */
int isEmpty(List l){
    return l == NULL;
}

    /* add element in ordered fashion */
List order_append(List l, int x){
    if(isEmpty(l) || x < l->info){
        l = push(l, x);
        return l;
    }
    /* initialize auxiliary lists */
    Node *temp, *prev, *curr;
    temp = (Node*)malloc(sizeof(Node));
    prev = NULL;
    for(curr = l ; curr != NULL && x > curr->info; curr = curr->next){
        prev = curr;
    }
    /* insert value in list and return */
    temp->info = x;
    temp->next = curr;
    if(prev != NULL){
        prev->next = temp;
        return l;
    }
    else return temp;
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
    int min, max, x;
    /* scan maximum range */
    scanf("%d\n%d", &min, &max);
    if(min > max){
        exit(EXIT_FAILURE);
    }
    scanf("%d", &x);
    while(x > -1){
        lista = order_append(lista, x);
        scanf("%d", &x);
    }
    printf("%d\n", productBorders(lista, min, max));
    freeList(lista);
    return 0;
}