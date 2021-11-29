(* MiniCaml's Interpreter -- linguaggio funzionale didattico *)
type ide = string ;; (* Identifier *)


type typeName = (* Define types *)
    | TInt | TBool | TString (* Value types *)
    | TClosure | TRecClosure (* Function types *)
    | TUnbound (* Unkown type *)
;;

(* Abstract Expressions -- necessary to generate Abstract Syntax Tree *)
type exp =
    | EInt of int | EString of string
    | CstTrue | CstFalse
    | Den of ide
    (* Arithmetic operations *)
    | Sum of exp * exp | Sub of exp * exp | Mul of exp * exp | Div of exp * exp
    (* Compare operations *)
    | Zero of exp | EQ of exp * exp | LT of exp * exp | GT of exp * exp
    (* Logic operations *)
    | And of exp * exp | Or of exp * exp | Not of exp
    (* Control-flow operations *)
    | IfThenElse of exp * exp * exp 
    | Let of ide * exp * exp | LetRec of ide * ide * exp * exp
    | Fun of ide * exp | Apply of exp * exp
;;

(* Polymorphic Enviroment *)
type 't env = ide -> 't;;

type evT = 
    | Int of int | Bool of bool | String of string
    | Closure of ide * exp * evT env
    | RecClosure of ide * ide * exp * evT env
    | Unbound
;;

let emptyEnv = function x -> Unbound ;;

(* Bind a string x to a primitive value evT *)
let bind (s: evT env) (x: ide) (v: evT) =
    function (i: ide) -> if (i = x) then v else (s i)
;;

(* get froma a primitive value evT its type descriptor *)
let getType (x: evT) : typeName = match x with
    | Int(n) -> TInt
    | Bool(b) -> TBool
    | String(s) -> TString
    | Closure(i, e, en) -> TClosure
    | RecClosure(i, j, e, en) -> TRecClosure
    | Unbound -> TUnbound
;;

(* Type checking *)
let typecheck ((x, y) : (typeName*evT)) =
    match x with
    | TInt -> (match y with | Int(u) -> true | _ -> false)
    | TBool -> (match y with | Bool(u) -> true | _ -> false)
    | TString -> (match y with | String(u) -> true | _ -> false)
    | TClosure -> (match y with | Closure(i, e, n) -> true | _ -> false)
    | TRecClosure -> (match y with | RecClosure(i, j, e, n) -> true | _ -> false)
    | TUnbound -> (match y with | Unbound -> true | _ -> false)
;;

(* Primitive operations *)
exception RuntimeError of string*string;;

let isZero(x) = match (typecheck(TInt, x), x) with
    | (true, Int(v)) -> Bool(v = 0)
    | (_, _) -> raise (RuntimeError ("IsZero", "Argument is Not A Number"))
;;

let intEQ(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Bool(v = w)
    | (_, _, _, _) -> raise (RuntimeError("Equal", "Arguments must be Numbers"))
;;

let intSum(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Int(v+w)
    | (_, _, _, _) -> raise (RuntimeError("Sum", "Arguments must be Numbers"))
;;

let intSub(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Int(v-w)
    | (_, _, _, _) -> raise (RuntimeError("Subtraction", "Arguments must be Numbers"))
;;

let intMul(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Int(v*w)
    | (_, _, _, _) -> raise (RuntimeError("Multiplication", "Arguments must be Numbers"))
;;

let intDiv(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Int(v/w)
    | (_, _, _, _) -> raise (RuntimeError("Division", "Arguments must be Numbers"))
;;

let intLT(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Bool(v < w)
    | (_, _, _, _) -> raise (RuntimeError("Less Then", "Arguments must be Numbers"))
;;

let intGT(x, y) = match (typecheck(TInt, x), typecheck(TInt, y), x, y) with
    | (true, true, Int(v), Int(w)) -> Bool(v > w)
    | (_, _, _, _) -> raise (RuntimeError("Greater Than", "Arguments must be Numbers"))
;;

let boolAND(x, y) = match (typecheck(TBool, x), typecheck(TBool, y), x, y) with
    | (true, true, Bool(v), Bool(w)) -> Bool(v && w)
    | (_, _, _, _) -> raise (RuntimeError("AND", "Arguments must be Booleans"))
;;

let boolOR(x, y) = match (typecheck(TBool, x), typecheck(TBool, y), x, y) with
    | (true, true, Bool(v), Bool(w)) -> Bool(v || w)
    | (_, _, _, _) -> raise (RuntimeError("OR", "Arguments must be Booleans"))
;;

let boolNOT(x) = match (typecheck(TBool, x), x) with
    | (true, Bool(v)) -> Bool(not(v))
    | (_, _) -> raise (RuntimeError("NOT", "Arument is not Bool"))
;;

(* Interpreter -- evaluates expressions *)
let rec eval (e:exp) (s:evT env) : evT =
    match e with
    (* Primitive values *)
    | EInt(n) -> Int(n) | EString(s) -> String(s)
    | CstTrue -> Bool(true) | CstFalse -> Bool(false)
    | Den(i) -> (s i)

    (* Arithmetic expressions *)
    | Mul(e1, e2) -> intMul((eval e1 s), (eval e2 s))
    | Sum(e1, e2) -> intSum((eval e1 s), (eval e2 s))
    | Sub(e1, e2) -> intSub((eval e1 s), (eval e2 s))
    | Div(e1, e2) -> intDiv((eval e1 s), (eval e2 s))

    (* Compare expressions *)
    | Zero(e1) -> isZero(eval e1 s)
    | EQ(e1, e2) -> intEQ((eval e1 s), (eval e2 s))
    | LT(e1, e2) -> intLT((eval e1 s), (eval e2 s))
    | GT(e1, e2) -> intGT((eval e1 s), (eval e2 s))
    
    (* Logic expressions *)
    | And(e1, e2) -> boolAND((eval e1 s), (eval e2 s))
    | Or(e1, e2) -> boolOR((eval e1 s), (eval e2 s))
    | Not(e1) -> boolNOT(eval e1 s)

    (* Control-flow expressions *)
    | IfThenElse(e1, e2, e3) -> let g = eval e1 s in
                                (match (typecheck(TBool, g), g) with
                                |(true, Bool(true)) -> eval e2 s
                                |(true, Bool(false)) -> eval e3 s
                                | (_, _) -> raise(RuntimeError("Eval", "IfThenElse expression not recognized"))
                                )
    | Let(i, e, ebody) -> eval ebody (bind s i (eval e s))
    | Fun(arg, ebody) -> Closure(arg, ebody, s)
    | LetRec(f, arg, fBody, leBody) -> let benv = bind (s) (f) (RecClosure(f, arg, fBody, s)) in
        eval leBody benv
    | Apply (eF, eArg) -> let fclosure = eval eF s in
                            (match fclosure with
                            | Closure(arg, fbody, fDecEnv) -> let aVal = eval eArg s in
                                                              let aEnv = bind fDecEnv arg aVal in
                                                              eval fbody aEnv
                            | RecClosure(f, arg, fbody, fDecEnv) -> let aVal = eval eArg s in
                                                                    let rEnv = bind fDecEnv f fclosure in
                                                                    let aEnv = bind rEnv arg aVal in
                                                                    eval fbody aEnv
                            | _ -> raise(RuntimeError("Eval", "Apply function"))                                  
                            )
;;
