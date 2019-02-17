merge :: Ord a => [a] -> [a] -> [a]
merge xs [] = xs
merge [] ys = ys
merge (x : xs) (y : ys)
    | x < y = x : merge xs (y : ys)
    | otherwise = y : merge (x : xs) ys


-- merge [2, 7] [3, 9, 11]
-- 2 : merge [7] [3, 9, 11]
-- 3 : merge [7] [9, 11]
-- 7 : merge [] [9, 11]
-- [9, 11]

msort :: Ord a => [a] -> [a]
msort [] = []
msort [x] = [x]
msort xs = merge (msort(take m xs)) (msort(drop m xs))
    where
        m = length xs `div` 2

delDups :: Eq a => [a] -> [a]
delDups [] = []
delDups (x : xs)
    | x `elem` xs = delDups xs
    | otherwise = x : delDups xs

elem' :: Eq a => a -> [a] -> Bool
elem' _ [] = False
elem' n (x:xs)
    | x == n = True
    | otherwise = elem' n xs

removeEachSnd :: [a] -> [a]
removeEachSnd [] = []
removeEachSnd (x : xs)
    | mod len 2 == 0 = removeEachSnd(xs)
    | otherwise = x : removeEachSnd(xs)
        where
            len = length(xs)
