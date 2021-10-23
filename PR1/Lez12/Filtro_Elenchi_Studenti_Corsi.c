#include <stdio.h>
#include <stdlib.h>

/* define node */
typedef struct n{
    int mat;
    struct n * next;
}Node;

/* define list */
typedef Node * List;

/* function prototypes */
List append             (List l, int x);
List push               (List l, int x);
void cancellaDuplicates (List *l);
void filter             (List *l, List *q);
void dump               (List l);
void freeList           (List l);

/* functions body */
void dump(List l){
    List aux;
    aux = l;
    printf("(");
    while(aux != NULL){
        printf("%d ", aux->mat);
        aux = aux->next;
    }printf(")\n");
    return;
}

void freeList(List l){
    while(l != NULL){
        Node * temp;
        temp = l;
        l = l->next;
        free(temp);
    }
    return;
}

List push(List l, int x){
    /* allocate memory for a new head node */
    Node * newPtr =(Node*)malloc(sizeof(Node));
    newPtr->mat = x;
    newPtr->next = l;
    return newPtr;
}

List append(List l, int x){
    /* allocate memory for a new tail node */
    Node * newPtr =(Node*)malloc(sizeof(Node));
    if(l == NULL){
        l = push(l, x);
    }
    else{
        for(newPtr = l; newPtr->next != NULL; newPtr = newPtr->next);
        newPtr->next =(Node*)malloc(sizeof(Node));
        newPtr->next->mat = x;
        newPtr->next->next = NULL;
    }
    return l;
}

void cancellaDuplicates(List * l){
    if(*l != NULL && (*l)->next != NULL){
        if((*l)->mat == ((*l)->next)->mat){

            List temp = *l;
            *l = (*l)->next;
            free(temp);
            temp = NULL;
            cancellaDuplicates(l);
        }
    cancellaDuplicates(&(*l)->next);
    }
}

void filter(List *l, List *q){
    while(*q != NULL){

		if (*l != NULL && (*l)->mat == (*q)->mat){
			
			List tmpPtr = *l;
			*l = (*l)->next;
			free(tmpPtr);
			tmpPtr = NULL;
		}
		q = &(*q)->next;
	}
}

/* main function */
int main(void){
    List Fls = NULL;
    List Sls = NULL;
    int n;

    /* read first list values */
    scanf("%d", &n);
    int i = n;
    while(n >= i){
        Fls = append(Fls, n);
        scanf("%d", &n);
        if(n > i){
            i = n;
        }
    }
    /* print first list */
    puts("Primo elenco:");
    dump(Fls);

    /* first list without duplicates */
    puts("Primo elenco senza duplicati:");
    cancellaDuplicates(&Fls);
    dump(Fls);

    /* read second list values */
    scanf("%d", &n);
    int j = n;
    while(n >= j){
        Sls = append(Sls, n);
        scanf("%d", &n);
        if(n > j){
            j = n;
        }
    }

    /* print second list */
    puts("Secondo elenco:");
    dump(Sls);
    
    /* second list without duplicates */
    puts("Second elenco senza duplicati:");
    cancellaDuplicates(&Sls);
    dump(Sls);

    /* filter list */
    filter(&Fls, &Sls);
    puts("Primo elenco filtrato:");
    dump(Fls);

    /* free memory and finish execution */
    freeList(Fls);
    freeList(Sls);
    return 0;
}