#include <stdio.h>
#include <stdlib.h>

/* define list element */
typedef struct node{
    int val;
    struct node * next;
}Nodo;

/* define list pointer */
typedef Nodo* Lista;

/* function prototypes */
Lista push(Lista  l, int v);
Lista append(Lista l, int v);
Lista delete(Lista l, int v);
void dump(Lista l);


/* main functions */
int main(void){
    Lista list = NULL;
    int x = 1;
    scanf("%d", &x);
    while(x != 0){
        if(x > 0){
            if(x%2 == 0){
                list = push(list, x);
            }
            else{
                list = append(list, x);
            }
        }
        else{
            x = x * (-1);
            list = delete(list, x);
        }
        scanf("%d", &x);
    }
    dump(list);
    return 0;
}

/* functions body */
Lista push(Lista l, int v){
    /* allocate memory for a new head node */
    Nodo * newPtr =(Nodo*)malloc(sizeof(Nodo));
    newPtr->val = v;
    newPtr->next = l;
    return newPtr;
}

Lista append(Lista l, int v){
    /* allocate memory for a new tail node */
    Nodo * newPtr =(Nodo*)malloc(sizeof(Nodo));
    if(l == NULL){
        l = push(l, v);
    }
    else{
        for(newPtr = l; newPtr->next != NULL; newPtr = newPtr->next);
        newPtr->next =(Nodo*)malloc(sizeof(Nodo));
        newPtr->next->val = v;
        newPtr->next->next = NULL;
    }
    return l;
}

Lista delete(Lista l, int v){
    /* deleting the first occurrance of value 'd' */
    Nodo *temp, *prev, *succ;
    if(l == NULL){
        return l;
    }
    if(l->val == v){
        temp = l;
        l = l->next;
        free(temp);
        return l;
    }else{
        prev = NULL;
        /* searching for value 'd' through the list */
        for(succ = l; succ != NULL && succ->val != v; succ = succ->next){
            prev = succ;
        }
        if(succ != NULL && succ->val == v){
            temp = succ;
            prev->next = succ->next;
            free(temp);
            return l;
        }
    }
    return l;
}

void dump(Lista l){
    Nodo * aux;
    aux = l;
    while(aux != NULL){
        printf("%d\n", aux->val);
        aux = aux->next;
    }
    return;
}