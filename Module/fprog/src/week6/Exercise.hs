-- Exercise 1

-- f01 :: Num a => a -> a - f01 5 = 10
f01 :: Num a => a -> a
f01 = \x -> 2 * x

-- f01' :: Integer -> Integer - f01' 5 = 10
f01' = \x -> 2 * x

-- f01'' :: Num a () -> a -> a - f01'' () 5 = 10
f01'' () = \x -> 2 * x

-- f01''' :: Integer -> Integer -> Integer - f01''' 42 5 = 10
f01''' _ = \x -> 2 * x

-- f02 :: Integer -> Integer -> Integer - f02 10 15 = 25
f02 = \x -> \y -> x + y

-- f03 :: Integer -> Integer -> Integer
f03 = \x y -> x + y -- TODO

-- f04 :: Num a => a -> a -> a - f04 10 (x) 5 (y) = 15
f04 x = \y -> 2 * x + y

-- f05 :: (Integer, Integer) -> Integer - f05 (10,10) = 20
f05 = \(x, y) -> x + y

-- f06 :: [Integer] -> Integer - f06 [10,10] = 20
f06 = \[x, y] -> x + y

-- f07 :: [Integer -> Integer]
f07 = [\x -> x+1, \x -> 2*x, \x -> x^2] -- TODO

-- f08 :: Integer - head f07 5 = 6
f08 = head f07 5

-- f09 :: t -> t - f09 50 = 50
f09 = \x -> x

-- f10 :: [Integer -> Integer]
f10 = [f09 , \x -> x+1] -- TODO

-- f11 :: t -> Integer -> Integer, () -> Char)
f11 = \_ -> (\x -> x+1, \() -> 'a') -- TODO


-- Exercise 2

x ^+^ y = x^2 + y^2

-- g01 :: Integer -> Integer -> Integer - g01 2 5 = 29
g01 = (^+^)

-- g02 :: Integer -> Integer - g02 10 = 104
g02 = (^+^ 2)

-- g03 :: Integer -> Integer - g03 10 = 109
g03 = (3 ^+^)

-- g04 :: Integer = 13
g04 = (3 ^+^ 2)

-- g05 :: Num a => a -> a -> a - g05 10 3 = 29
g05 x y = 2*x + 3*y

-- g06 :: Integer -> Integer - g05 10 2 = 26
g06 = (`g05` 2)

-- g07 :: Integer -> Integer - g05 2 10 = 2*2 + 3*10 = 34
g07 = (2 `g05`)

-- g08 :: Integer - 2*3 + 3*2 = 12
g08 = g06 3

-- g09 :: Integer - 2*2 + 3*4 = 16
g09 = g07 4

-- g10 : Num a => a -> a -> a -> a - g10 2 5 10 = 2*2 + 3*5 + 4*10 = 59
g10 x y z = 2*x + 3*y + 4*z

-- g11 :: Integer -> Integer - 2*10 + 3*2 = 26
g11 = (`g05 ` 2)

-- g12 :: Integer - 2*3 + 3*2 
g12 = g06 3

-- g13 :: Integer - 2*2 + 3*4
g13 = g07 4

-- g14 :: Num a => a -> a -> a -> a - g14 2 5 10 = g10 3 5 10 = 2*3 + 3*4 + 4*10 = 61
g14 x = (g10 (x+1))

-- g15 :: Integer - g10 3 3 4 = 2*3 + 3*3 + 4*4
g15 = g14 2 3 4

-- g16 :: Num a => Int -> a -> a - g16 1 10 = (-) 10 2 = 10-2 = 8
g16 n = \x -> ([(+) , (-), (*)] !! n) x 2

-- g17 :: Integer - (-) 5 2 = 5-2 = 3
g17 = g16 1 5


-- Exercise 3

-- [1,2,3,4,5]
h01 = [x | x <- [1 .. 5]]

-- [2,4,6,8,10]
h02 = [2* x | x <- [1 .. 5]]

-- [(1-1),(1-2),(1-3),(1-4),(2-1),(2-2),(2-3),(2-4),(3-1),(3-2),(3-3),(3-4)]
-- [0,-1,-2,-3,1,0,-1,-2,2,1,0,-1]
h03 = [x - y | x <- [1 .. 3], y <- [1 .. 4]]

-- [(1-1),(2-1),(3-1),(4-1),(1-2),(2-2),(3-2),(4-2),(1-3),(2-3),(3-3),(4-3)]
-- [0,1,2,3,-1,0,1,2,-2,-1,0,1]
h04 = [x - y | y <- [1 .. 3], x <- [1 .. 4]]

-- [(1+1),(2+1),(2+2),(3+1),(3+2),(3+3)]
-- [2,3,4,4,5,6]
h05 = [x + y | x <- [1 .. 3], y <- [1 .. 4], x >= y]

-- ['d','s','z'] --> "dsz"
h06 = [head x | x <- ["dimdi", "schnurpsel", "zumsel"]]

-- ['d','s','z'] --> "dsz"
h07 = [x | (x : _) <- ["dimdi", "schnurpsel", "zumsel"]]

-- ["chnurpsel"]
h08 = [xs | ('s' : xs) <- ["dimdi", "schnurpsel", "zumsel"]]
