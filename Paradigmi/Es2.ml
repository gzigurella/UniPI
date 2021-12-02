(* Utility functions *)
open Printf;;

(* print list *)
let rec print_list lis =
    match lis with
    | [] -> printf "\n"
    | h::t ->
        match h with
        | 0 -> print_list t
        | _ -> printf "%d " h; print_list t
    ;;

(* print list of tuples *)
let rec print_multiset ms =
        match ms with
        | [] -> printf "\n"
        | (x,n)::t -> printf "(%d,%d)" x n; print_multiset t
        ;;

(* Es 2.1 Scrivere una funzione genera_lista che prende un intero positivo n e restituisce una lista contenente la lista [1; 2; ... n]. 
    Nel caso in cui n sia minore o uguale di zero restituisce la lista vuota. *)

let rec genera_lista n =
    if n <= 0 then []
    else (genera_lista (n-1))@[n](* genera ricorsivamente la lista ed appendici una lista contenente il successivo numero *)
    ;;

(* print_list (genera_lista 5);; -- remove comment to debug print -- *) 

(* Es 2.2 Scrivere una funzione media che prende una lista di interi e calcola la media dei suoi elementi. *)
let media list = let acc = List.fold_left (+) 0 list in acc / (List.length list);;

(* printf "%d\n" (media [5;3;4]);; -- remove comment to debug print -- *)

(* Es 2.3 Scrivere una funzione take che prende una lista lis e un intero n e restituisce la lista contenente i primi n elementi di lis. 
            Se n è minore di 0 resituisce la lista vuota.*)

let rec take lis n= 
    if n<=0 then []
    else match lis with
        | [] -> []
        | h::t -> h::(take t (n-1))
    ;;

(* print_list (take [5;3;4] 2);; -- remove comment to debug print -- *)

(* Es 2.4 Scrivere una funzione drop che prende una lista lis e un intero n e restituisce una lista contenente gli elementi di lis ad eccezione dei primi n. 
    Se n è minore di 0 restituisce lis. *)

let rec drop lis n= 
    if n<=0 then lis
    else match lis with
        | [] -> []
        | h::t -> drop t (n-1) (* keep discarding elements until n = 0, then return the list *)
    ;;

(* print_list (drop [5;3;4] 1);; -- remove comment to debug print -- *)

(* Es 2.5  Scrivere una funzione somma_costante che prende una lista di coppie di interi e verifica (restituendo true o false) se tutte le coppie hanno elementi la cui somma è sempre lo stesso valore. *)

(* check if all elements of the list are the same *)

let rec sum_tuple_list lis =
    match lis with
        | [] -> []
        | (a, b)::t ->[a+b]::sum_tuple_list t
    ;;

let rec somma_costant lis = 
    match lis with
        | [] -> true
        | h::[] -> true
        | h::t -> if h = (List.hd t) then somma_costant t else false
    ;;

(*

let l1 = [(1,3);(2,2);(0,4)] ;; (*expected true*)
let l2 = [(1,3);(2,2);(1,4)] ;; (*expected false*)

if (somma_costant (sum_tuple_list l1))  then (printf "True\n") else (printf "False\n");;
if (somma_costant (sum_tuple_list l2))  then (printf "True\n") else (printf "False\n");; 
        
-- remove comment to debug print --*)

(* Es 2.6 Scrivere una funzione ord che prende una lista e verifica (restituendo true o false) 
se i suoi elementi sono ordinati in modo crescente. *)
let rec ord lis = 
    match lis with
        | [] -> true
        | h::[] -> true
        | h::t -> if h < (List.hd t) then ord t else false
    ;;

(* let res = (ord [1;5;3]);;

printf "%B\n" res;; -- remove comment to test ord function*)


(* Es 2.7 Scrivere una funzione min che prende un elemento x e una lista lis 
e restituisce il minore tra x e il minimo della lista lis.*)

let rec min x lis =
    match lis with
        | [] -> x
        | h::t -> if h < x then min h t else min x t
    ;;

(* let m = min 3 [7;4;6];;

printf "%d\n" m;;  -- remove comment to test min function *)

(* Es 2.8 Scrivere una funzione remove che prende un elemento x e una lista lis
 e restituisce una lista uguale a lis ma senza la prima occorrenza dell'elemento x. *)

let rec remove x lis =
    match lis with
        | [] -> []
        | h::t -> if h = x then t else h::(remove x t)
    ;;

(* let r = remove 3 [7;3;3;6;3];;

print_list r;; -- remove comment to test remove function *)

(* Es 2.9 Scrivere una funzione sort che prende una lista di elementi di qualunque tipo e restituisce una lista con gli stessi elementi ordinati in modo crescente.
(Suggerimento: possono essere utili le funzioni min e remove degli esercizi precedenti *)

let rec sort lis =
        match lis with
        | [] -> []
        | h::t -> (min h t)::(sort (remove (min h t) lis))
    ;;

open Stdlib;; (* use to import polymorphic compare function *)
let sort_module lis = List.sort compare lis;;

(* let s = sort [7;3;3;6;3];;
print_list s;; -- remove comment to test sort function *)

(* Es. 2.10 Scrivere una funzione (o meglio, un predicato) set che prende una lista e 
verifica (restituendo true o false) se i suoi elementi sono tutti diversi tra loro, 
cioè se la lista è una corretta rappresentazione di un insieme. 

La funzione deve avere tipo 'a -> bool, in modo da poter essere essere applicata 
ad insiemi con elementi di qualunque tipo. *)

let rec exists x list =
    match list with
        | [] -> false
        | h::t -> if h = x then true else exists x t
    ;;

let rec set list =
    match list with
        | [] -> true
        | h::t -> if exists h t then false else set t
    ;;

(*
let s1 = set [1;2;3;4;5];;
let s2 = set [1;2;5;4;5];;

printf "lista1: %B\nlista2: %B\n" s1 s2;; -- remove comment to test the set function*)

(* Es 2.11 Un multi-insieme (o multiset) estende il concetto di insieme consentendo la possibilità di avere più occorrenze dello stesso elemento.   
Un multiset può essere definito come una lista di coppie (x,n) in cui x è l'elemento considerato e n è il numero di occorrenze di quell'elemento. 
Scrivere una funzione multiset che, data una lista di coppie di tipo 'a*int, verifica se è una corretta rappresentazione di un multiset (contentente coppie che sono tutte diverse tra loro nel primo elemento). *)

let rec multiset list =
    match list with
        | [] -> true
        | (x,n)::t -> if exists (x,n) t then false else multiset t
    ;;

(* let m1 = multiset [(1,2);(2,3);(3,4)];;
let m2 = multiset [(1,2);(2,3);(3,4);(1,2)];;

printf "lista1: %B\nlista2: %B\n" m1 m2;; -- remove comment to test the multiset function *)

(* Es 2.12 Scrivere una funzione crea_multiset che, 
data una lista di qualunque tipo anche con elementi ripetuti, 
la trasforma in un multiset verificabile tramite la funzione multiset dell'esercizio precedente. *)

(* count all occurances of x in a list *)
let rec count x list =
    match list with
        | [] -> 0
        | h::t -> if h = x then 1 + (count x t) else count x t
    ;;

(* remove all occurances of x in a list *)
let rec remove_all x list =
    match list with
        | [] -> []
        | h::t -> if h = x then remove_all x t else h::(remove_all x t)
    ;;

let rec crea_multiset list =
    match list with
        | [] -> []
        | h::t -> (h,(count h list))::(crea_multiset (remove_all h list))
    ;;

(*
let c1 = crea_multiset [1;2;1;2;2;5;6;6;8;8;8] ;;

print_multiset c1;; -- remove comment to test crea_multiset function *)
