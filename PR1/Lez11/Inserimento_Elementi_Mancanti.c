#include <stdio.h>
#include <stdlib.h>

/* define node */
typedef struct n{
    int info;
    struct n * next;
}Node;

/* define list */
typedef Node * List;

/* function prototypes */
List orderAdd   (List l, int x);
List missingAdd (List l);
void dump       (List l);
void freeList   (List l);
int  isEmpty    (List l);

/* functions body */
void dump(List l){
    List aux;
    for(aux = l; aux != NULL; aux = aux->next){
        printf("%d --> ", aux->info);
    }
    printf("NULL\n");
    return;
}

int isEmpty(List l){
    return l == NULL;
}

void freeList(List l){
    if(!isEmpty(l)){
        List temp;
        while(l != NULL){
            temp = l;
            l = l->next;
            free(temp);
        }
    }
    return;
}

List orderAdd(List l, int x){
    Node * temp, * succ, * prev;
    temp = malloc(sizeof(Node));
    prev = NULL;
    for(succ = l; succ != NULL && x < succ->info; succ = succ->next){
        prev = succ;
    }
    temp->info = x;
    temp->next = succ;
    if(prev != NULL){
        prev->next = temp;
        return l;
    }
    else return temp;
}

List missingAdd(List l){
    List aux;
    aux = l;
    while(aux->next != NULL){
        if(aux->info > (aux->next)->info+1){
            l = orderAdd(l, (aux->info-1));
        }
        aux = aux->next;
    }
    return l;
}

/* main function */
int main(void){
    List ls = NULL;
    int n;

    /* read list values */
    scanf("%d", &n);
    while(n > -1){
        ls = orderAdd(ls, n);
        scanf("%d", &n);
    }

    if(!isEmpty(ls)){
        ls = missingAdd(ls);
    }
    dump(ls);
    freeList(ls);
    return 0;
}