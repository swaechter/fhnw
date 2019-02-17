data Tree a = Leaf a | Node [Tree a]

tree1 = Node [Node [Leaf 3, Leaf 4, Leaf 5], Leaf 6]
--                  Tree Int
--                 [Tree Int]                Tree Int
--            Tree Int
--           [Tree Int]
--      Tree Int

sumT :: Num a => Tree a -> a
sumT (Leaf a) = a
sumT (Node (xs)) = sum [sumT x | x <- xs]

flattenT :: Tree a -> [a]
flattenT (Leaf a) = [a]
flattenT (Node xs) = foldr (\x accu -> accu ++ (flattenT x)) [] xs

-- occursT :: Eq a => a -> Tree a -> Bool
-- occursT e (Leaf a) = e == a
-- occursT e (Node xs) = foldr(\x accu -> if occursT e x then True else accu) False xs

-- lookupT :: Eq a => a -> Tree (a, b) -> Maybe b
-- lookupT e (Leaf a) = if e == a then Just a else Nothing
-- lookupT e (Node xs) = foldr(\x accu -> if lookupT e x then Just e else accu) Nothing xs
