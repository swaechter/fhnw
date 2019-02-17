listOne [] = []
listOne (x : xs) = x + 1 : listOne xs

listSquare [] = []
listSquare (x : xs) = x * x : listSquare xs

listSquare [] = []
listSquare (x : xs) = f x : listSquare
    where f x = x * x
 
listf _ [] = []
listf f (x: xs) = f x : listf f xs

listOne = listf (\x -> x + 1)
listSquare = listf (\x -> x * x);

listf :: (a -> b) -> [a] -> [b]
-- listf wird in Prelude durch map implementiert

sum [] = 0
sum (x : xs) = x + sum xs

product [] = 1
product (x : xs) = x * product xs

or [] = False
or (x : xs) = x || or xs

and [] = True
and (x : xs) = x || and xs

agg :: (a -> b -> b) -> b -> [a] -> b
agg f v [] = v
agg f v (x : xs) = x `f` agg f v xs

sum :: (a -> Num) => [a] -> b
sum = agg (+) 0

product :: (a -> Num) => [a] -> b
product = agg(*) 1

or :: (a -> Num) => [a] -> b
or = agg(||) False

and :: (a -> Num) => [a] -> b
and agg(&&) True

foldr :: (a -> b -> b) -> b -> [a] -> b
foldr _ v [] = v
foldr f v (x : xs) = x `f` foldr f v xs

--  [x1,x2,x3] v
--            +
--         +
--     +
--  Typ von v

-- foldr + v [x1,x2,x3]
-- x1 + foldr v [x2,x3]
-- x1 + (x2 + foldr v [x3])
-- x1 + (x2 + (x3 foldr + v []))
-- x1 + (x2 + (x3 + v))

foldl :: (a -> b -> a) -> a -> [b] -> a
foldl _ v [] = v
foldl f v (x : xs) = f (v `f` x) xs


-- a      b
-- v [x1,x2,x3]
--   + a
--      + a
--         + a
--             a

-- foldl + v [x1,x2,x3]
-- foldl (v + x1) [x2,x3]
-- foldl ((v + x1) + x2) [x3]
-- foldl (((v + x1) + x2) + x3) []
-- (((v + x1) + x2) + x3)
-- foldl: Rekursiver Aufruf an äusserster Position
-- foldr macht es möglich, immer gleich auf das Resultat zurückzugreifen und wenn nötig abzubreichen (True && True) && False --> Abbruch
