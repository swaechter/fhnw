-- Exercise 1

-- 2 :: Num a => a
val1 = 2

-- 4 :: Num a => a
val2 = 2 + 2

-- 2 :: Int :: Int
val3 = 2 :: Int

-- 2 :: Float :: Float
val4 = 2 :: Float

-- 4 :: Double :: Double
val5 = (2 + 2) :: Double

-- 2.0 :: Fractional a => a
val6 = 2.0

-- Typing error
-- val7 = 2.0 :: Int

-- 4.0 :: Fractional a => a
val8 = 2 + 2.0

-- Typing error
-- val9 = (2 :: Int) + (2 :: Double)

-- 4 :: Int
val10 = (2 :: Int) + 2

-- Num a1, Num a2 => (a1, a2)
val11 = (2 , 2)

-- Num a => [a]
val12 = [2 , 2]

-- Fractional t => [t]
-- val13 = [2 , 2.0]

-- Typing error
-- val14 = [2 :: Float , 2 :: Double ]


-- Exercise 2

-- f1 :: Num a => b -> a
f1 x = 2

-- f2 :: Num a => a -> a
f2 x = 2 * x

-- f3 :: Eq a => a -> a -> a -> Bool
f3 x y z = x == y && y == z

-- f4 :: Ord a => a -> a -> a -> Bool
f4 x y z = x < y && y < z

-- f5 :: Ord a => a -> a -> a-> Bool 
f5 x y z = x == y && y < z

-- f6 :: (Ord a, Num a) => a -> a -> a
f6 x y = 2 * x < y

-- f7 :: (Ord a, Num a) => a -> a -> a
f7 x y = min (abs x) (negate y)

-- f8 :: Num a => a -> a -> [a]
f8 x y = [x, y, 2]

-- f9 :: (Integral a, Fractional a) => a -> a -> a
f9 x y = x `div` y + x / y

-- Typing of f1, but not working
-- val15 = f1

-- f1 Char
val16 = f1 'a'

-- f1 [Char]
val17 = f1 "a"

-- f1 (Num a => b -> a)
val18 = f1 f1

-- Typing of f2, but not working
-- val19 = f2

-- f2 Int
val20 = f2 2

-- f2 Fractional
val21 = f2 2.0

-- Typing error
-- val22 = f2 'a'

-- (Char, Char) == (Char, Char) :: Bool
val23 = ('a', 'b') == ('c', 'd')

-- (Char, Char) == (Char, Char) :: Bool
val24 = ('a', 'b') < ('c', 'd')

-- Typing error
-- val25 = ('a', 'b') < ('c', 'd', 'e')

-- [Char] < [Char] :: Bool
val26 = ['a', 'b'] < ['c', 'd', 'e']

-- Typing of f3, but not working
-- val27 = f3

-- f3 (Char, Char) (Char, Char) (Char, Char) :: Bool
val28 = f3 ('a', 'b') ('a', 'b') ('a', 'b')

-- Typing of f4, but not working
-- val29 = f4

-- Typing error
-- val30 = f4 2 2

-- Typing of f5, but not working
-- val31 = f5

-- f5 [] [] [Int] :: Bool
val32 = f5 [] [] [2 , 2]

-- Typing of f6, but not working
-- val33 = f6

-- Typing error
-- val34 = (f6) 2

-- Typing of f7, but not working
-- val35 = f7

-- Typing error
-- val36 = f7 (2 :: Int) (2 :: Integer)

-- Typing of f8, but not working
-- val37 = f8

-- f8 2 2.0 :: Fractional t => [t]
val38 = f8 2 2.0

-- Typing of f9, but not working
-- val39 = f9

-- Typing error
-- val40 = f9 2 2
