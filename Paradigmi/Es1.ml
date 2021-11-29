open Printf;;
(* Es 1.1 write function average that takes two integers and calculate their average value as float *)
let average a b = ((float_of_int a) +. (float_of_int b)) /. 2.0;;

(* Es 1.2 write a function 'gt' that given two integers checks which is greater *)
let gt a b = if(a > b) then (string_of_int a) ^ " è maggiore di " (string_of_int b)
  else (string_of_int b) ^ " è maggiore di " (string_of_int a);;

(* Es 1.3 write a function 'sum_mul' that given two integers return a tuple of their sum and their product *)
let sum_mul a b = ((a+b), (a*b))

(* Es 1.4 write a function that given a function f and a parameter n return the absolute value of the result *)
let abs_apply f n = let temp = (f n) in 
  if temp > 0 then temp else (-temp);;

(* Es 1.6  make a sum function that takes a paramater n and sum all integers from 0 to n
            it must work for negative numbers as well *)

let rec sum n = if n < 0 then n + sum (n + 1) else 
                if(n == 0) then 0 else n + sum (n - 1);;

let testA = sum (-5);;
let testB = sum (5);;

Printf.printf "Test su numeri negativi %d\n" testA;; 
Printf.printf "Test su numeri positivi %d\n" testB;; 

(* Es 1.7 write a modulo function that takes two parameters and calculates the mod operator without using it
          it can also take negative numbers as parameters *)

let rec modulo a b = if ((a < 0) && (b < 0)) then modulo (-a) (-b) 
  else if(a < b) then a 
  else modulo (a - b) b;;

let testC = modulo 5 3;;
let testD = modulo (-5) (-3);;

Printf.printf "Test sui numeri negativi %d\n" testD;;
Printf.printf "Test sui numeri positivi %d\n" testC;;

(* Es 1.8 write two functions, ping and pong, that take a parameter n and writes n times ping/pong alternating
          and starting with the function name; for example ping 3 returns "PING PONG PING" and pong 3 returns "PONG PING PONG" *)

let rec ping n = if n = 0 then "" 
  else if(n mod 2) = 0
    then "PONG " ^ ping (n - 1) 
    else "PING " ^ ping (n - 1);;

let rec pong n = if n = 0 then "" 
  else if(n mod 2) = 0
    then "PING " ^ pong (n - 1) 
    else "PONG " ^ pong (n - 1);;

Printf.printf "%s\n" (pong 3);;
Printf.printf "%s\n" (ping 3);;
