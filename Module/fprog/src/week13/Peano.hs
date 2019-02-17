date Nat = Zero | Succ Nat
--   Type  Value  Value Type

Zero :: Nat
Succ :: Nat -> Nat

Succ Zero :: Nat
--   Nat value
-- Nat value

Succ (Succ Zero) ::: Nat
Succ (Succ (Succ Zero)) :: Nat

add Zero n = n
add (Succ m) n = Succ (add m n)

app [] ys = ys
app (x:xs) ys = x : app xs ys

-- Einfache Expressions
data Expr = Val Int
    | Add Expr Expr
    | Mult Expr Expr

Add (Val 1) Mult (Val 2) (Val 3)
--  Expr         Expr    Expr
--  Expr    Expr

eval :: Expr -> Int
eval (Val x) = x
eval (Add e1 e2) = eval e1 + eval e2
eval (Mult e1 e2) = eval e1 * eval e2

-- Verbesserte Expressions
data Expr = Val Int
    | Dyad Opr Expr Expr

data Opr = Plus | Minus | Times | Div

operatorvalues = Dyad Plus (Val 1) (Dyad Times (Val 2) (Val 3))

eval :: Expr -> Int
eval (Val x) = x
eval (Dyad opr e1 e2) = operator opr (eval e1) (eval e2)

operator :: Opr ->  (Int -> Int -> Int)
operator Plus = (+)
operator Minus = (-)
operator Times = (*)
operator Div = div
