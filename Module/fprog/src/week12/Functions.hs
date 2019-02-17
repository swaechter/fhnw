-- Implementieren Sie mit foldr  oder foldl die Funktionen map, filter length, reverse und (++)

-- [x1,x2,x3] v     []
--           + [f x3]
--       + [f x2, f x3]
--    + [f x1, f x2, f x3]
-- [f x1,f x2,f x3]
mymyapl f = foldr (\x accu -> f x : accu) []

-- v [x1,x2,x3]
--  +
--      +
--          + [f x1,f x2,f x3]
mymapr f = foldl (\accu x -> accu ++ [f x]) []

myfilterr p = foldr (\x accu -> if p x then x : accu else accu) []

myfilterl p = foldl (\accu x -> if p x then accu ++ [x] else accu) []

mylengthr = foldr (\_ accu -> accu + 1) 0

mylengthr = foldl (\accu _ -> accu + 1) 0

myreverser = foldr (\x accu -> accu + [x]) []

myreversel = foldl (\accu \x -> x : accu) []
-- (\x accu -> x : accu) = (:)
-- (\x y -> f x y) = f
-- \x y -> f y x


flip :: (a -> b -> c) -> b -> a -> c
flip f = \x y -> f y x

-- mynewreversel :: 
mynewreversel = foldl (flip (:)) []

myappendr (++ ys) = foldr (\x accu -> x : accu) ys

myappendl (xs ++) = foldl (\accu x -> accu ++ [x]) xs

-- foldr :: (a -> b -> b) -> b -> [a] -> b
--  [a]        b
--  [x1,x2,x3] v
--         b () a -> b

-- foldl :: (a -> b -> a) -> a -> [b] -> a
--  a [b]
--  v [x1,x2,x3]
--  a -> b -> a() a

-- foldr :: (a -> b -> b) -> b -> [a] -> b
-- foldl :: (b -> a -> b) -> b -> [a] -> b
