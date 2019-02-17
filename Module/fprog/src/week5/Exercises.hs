-- Exercise 1

-- [1, 2, 3, 4]
val1 = 1 : 2 : 3 : [4]

-- [1, 2, 3, 4]
val2 = 1 : 2 : [3, 4]

-- [[1, 2], [3]]
val3 = (1 : 2 : []) : (3 : []) : []

-- [(1, 2), (3, 4), (5, 6)]
val4 = (1, 2) : (3, 4) : [(5, 6)]

-- [[]]
val5 = [] : []

-- [[],[]]
val6 = [] : [] : []

-- [[[]]]
val7 = ([] : []) : []

-- [[[[]]]]
val8 = (([] : []) : []) : []

-- ["ab"]
val9 = 'a' : 'b' : []


-- Exercise 2

-- 1 : 2 : 3 : []
val10 = [1, 2, 3]

-- (1 : 2 : []) : ([]) : (3 : 4 : []) : []
val11 = [[1, 2], [], [3, 4]]

-- ([]) : (('a' : []) : []) : ([] : []) : []
val12 = [[], ["a"], [[]]]


-- Exercise 3

-- f1 :: [t] -> (t, t, [t])
f1 (x : y : z) = (x, y, z)

-- f2 :: [t] -> (t, t)
f2 [x, y] = (x, y)

-- f3 :: [t] -> (t, t)
f3 (x : y : []) = (x, y)

-- f1 :: (t, t, [t]), but not working
-- val13 = f1 []

-- f2 :: (t, t), but not working
-- val14 = f2 []

-- f3 :: (t, t), but not working
-- val15 = f3 []

-- f1 [1] :: Num t => (t, t, [t]), but not working
--val16 = f1 [1]

-- f2 [1] :: Num t => (t, t), but not working
-- val17 = f2 [1]

-- f3 [1] :: Num t => (t, t), but not working
-- val18 = f3 [1]

-- f1 [1, 2] :: Num t => (t, t, [t]) - (1,2,[])
val19 = f1 [1, 2]

-- f2 [1, 2] :: Num t => (t, t) - (1,2)
val20 = f2 [1, 2]

-- f3 [1, 2] :: Num t => (t, t) - (1,2)
val21 = f3 [1, 2]

-- f1 [1, 2, 3] :: Num t => (t, t, [t]) - (1,2,[3])
val22 = f1 [1, 2, 3]

-- Not working
-- val23 = f2 [1, 2, 3]

-- Not working
--val24 = f3 [1, 2, 3]

-- f1 (1 : 2 : 3 : []) :: Num t => (t, t, [t]) - (1,2,[3])
val25 = f1 (1 : 2 : 3 : [])

-- Not working
-- val26 = f2 (1 : 2 : 3 : [])

-- Not working
-- val27 = f3 (1 : 2 : 3 : [])

-- f1 ['a', 'b', 'c'] :: (Char, Char, [Char]) - (Char,Char,[Char])
val28 = f1 ['a', 'b', 'c']

-- f1 [[1], [2, 3], []] :: Num t => ([t], [t], [[t]]) -- ([1], [2,3], [[]])
val29 = f1 [[1], [2, 3], []]

-- f1 (1 : 2 : 3 : [4, 5]) :: Num t => (t, t, [t]) -- (1,2,[3,4,5])
val30 = f1 (1 : 2 : 3 : [4, 5])

-- f1 [1 .. 100] :: (Enum t, Num t) => (t, t, [t]) -- (1,2,[3..100)
val31 = f1 [1 .. 100]

-- f4 :: (t, t) -> [t]
f4 (x, y) = [x, y]

-- f4 ([1, 2], [3, 4, 5]) :: Num t => [[t]] - [[1,2],[3,4,5]]
val33 = f4 ([1, 2], [3, 4, 5])

-- g1 :: Num a => [Char] -> a
g1 "dimdi" = 1
g1 ['d', 'o', 'm', 'd', 'o'] = 2
g1 ('d' : 'i' : 'n' : 'g' : []) = 3
g1 ('d' : 'i' : 'n' : 'g' : _ ) = 4
g1 (x : y) = 5
g1 _ = 6

-- 2
val34 = g1 "domdo"

-- 3
val35 = g1 "ding"

-- 4
val36 = g1 "dingdimdi"

-- 5
val37 = g1 "dumdu"

-- 6
val38 = g1 ""

-- g2 :: Num a => [Char] -> a
g2 (d : "imdi")
    | d == 'd' || d == 'D' = 1
g2 (z : "umsel")
    | z == 'z' || z == 'Z' = 2
g2 _ = 3

-- 1
val39 = g2 "dimdi"

-- 1
val40 = g2 ['D', 'i', 'm', 'd', 'i']

-- 2
val41 = g2 ('Z' : 'u' : "msel")

-- 3
val42 = g2 "dimdiding"

-- h1 :: [Char] -> Char
h1 ['a', 'b'] = 'a'
h1 ['a', b ] = b
h1 (_ : _ : 'm' : _) = 'm'
h1 (a : b) = a

-- 'a'
val43 = h1 "ab"

-- 'c'
val44 = h1 "ac"

-- 'm'
val45 = h1 "dimdi"

-- 'm'
val46 = h1 "zumsel"

-- 's'
val47 = h1 "schnurpsel"

-- h2 :: [(t1, t2)] -> (t1, t2)
h2 [(a , b), c] = c
h2 (a : b : c) = a

-- (3,4)
val49 = h2 [(1 , 2) , (3 , 4)]

-- (1,2)
val50 = h2 [(1 , 2) , (3 , 4) , (5 , 6)]

-- Runtime exception
val51 = h2 [(1 , 2)]

-- h3 :: [] -> [Char]
h3 ((x : y) : z) = y
h3 ([] : _) = "2"
h3 [] = "3"

-- "imdi"
val52 = h3 ["dimdi"]

-- "2"
val53 = h3 ["", "dimdi", "domdo"]

-- "2"
val54 = h3 [[]]

-- "3"
val55 = h3 []
