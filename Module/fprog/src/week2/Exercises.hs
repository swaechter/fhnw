-- Exercise 1

-- Char
x = 'x'

-- Char
val1 = x

-- Char
val2 = 'x'

-- [Char]
val3 = "x"

-- [Char]
val4 = ['x']

-- [Char]
val5 = [x, 'x']

-- [Char]
val6 = [x, x, x, x]

-- Not working
-- val7 = ['x', "x"]

-- Not working
-- val8 = [x, True]

-- [Bool]
val9 = [x == 'x', True]

-- [[[Char]]]
val10 = [[" True "]]

-- Not working
-- val11 = [[True, False], True]

-- [[Bool]]
val12 = [[True, False], []]

-- Char
val13 = ('x')

-- (Char,Char)
val14 = (x, 'x')

-- (Char,Char,Char,Char)
val15 = (x, x, x, x)

-- (Char,[Char])
val16 = ('x', "x")

-- (Char,Bool)
val17 = (x, True)

-- (Bool,Bool)
val18 = (x == 'x', True)

-- [Char] --> Ein Tupel benötigt ein Komma, also mindestens 2 Elemente
val19 = ((" True "))

-- ((Bool,Bool),Bool)
val20 = ((True, False ), True)

-- ((Bool,Bool),())
val21 = ((True, False ), ())


-- Exercise 2

-- [Bool]
a = [True]

-- [Bool]
val22 = a

-- [Bool]
val23 = a ++ a ++ [True]

-- [Bool]
val24 = a ++ []

-- Bool
val25 = head a

-- [Bool]
val26 = tail a

-- Not working
-- val27 = head 'x'

-- Char
val28 = head "x"

-- [Char]
val29 = tail "x"

-- Char
val30 = "dimdi" !! 2

-- [Char]
val31 = "dimdi" ++ "ding"

-- Exercise 3

einkaufsliste = [(3, "Widerstand 10 kOhm"),(5, "Kondensator 0.1 microFarad" ), (2, "Zahnrad 38 Zaehne")]
preisliste = [("Zahnrad 38 Zaehne", 1200),("Widerstand 10 kOhm", 50),("Widerstand 20 kOhm", 50),("Kondensator 0.1 microFarad", 50)]

-- [(Bool,Char)]
val32 = [(True, 'a'), (False, 'b')]

-- Not working
-- val33 = [(True, 'a'), ('b', False )]

-- [(Bool,Char)
val34 = [(True, 'a'), ('a' == 'b', head "a")]

-- ([Bool],[Char])
val35 = ([True, 'a' == 'b'], ['a'])

-- (Char,Char,Char,Char)
val36 = ('a', 'b', 'c', 'd')

-- (Char,(Char,(Char,Char)))
val37 = ('a', ('b', ('c', ('d'))))

-- ((Char,Char),Char,Char)
val38 = (('a', 'b'), 'c', 'd')

-- [Char]
val39 = ['a', 'b', 'c', 'd']

-- [(Integer,[Char])]
val40 = einkaufsliste

-- [([Char],Integer)]
val41 = preisliste

-- [(Integer,[Char])],[([Char],Integer)])
val42 = (einkaufsliste, preisliste )

-- Exercise 4

f1 :: Int -> Int
f1 x = x^2 + x + 1

f2 :: Int -> Int
f2 x = 2*x + 1

-- Not working
-- val43 = f1

-- Int
val44 = f1 5

-- Not working
-- val45 = f1 f2

-- Int
val46 = f1 ( f2 5)

-- [Int]
val47 = [f1 5 , f2 6 , 5 , 6]

-- [Int -> Int]
val48 = [f1 , f2 , f1 ]

-- Not working
-- val49 = [f1 5 , f2 ]

-- [Int, Int -> Int]
val50 = (f1 5 , f2 )

-- Int
val51 = ([ f1 , f2 , f1 ] !! 1) 3

-- Int, but not working (Index too large)
val52 = ([ f1 , f2 , f1 ] !! 5) 3

-- Exercise 5
g1 :: Int -> Int -> Int -> Int
g1 x y z = x ^2 + y ^2 + z ^2

g2 :: Int -> Int -> Int
g2 x y = 2* x + 2* y

-- Int -> Int -> Int -> Int, but not working
-- val53 = g1

-- Int -> Int -> Int, but not working
-- val54 = g1 2

-- Int -> Int, but not working
-- val55 = g1 2 3

-- Int
val56 = g1 2 3 4

-- Not working
-- val57 = g1 2 3 4 5

-- (Int -> Int -> Int -> Int, Int -> Int -> Int)
val58 = (g1, g2)

-- (Int -> Int -> Int, Int -> Int -> Int)
val59 = (g1 2, g2)

-- (Int -> Int, Int -> Int)
val60 = (g1 2 3, g2 4)

-- (Int, Int)
val61 = (g1 2 3 4, g2 4 5)

-- Not working
-- val62 = [g1, g2]

-- [Int -> Int -> Int]
val63 = [g1 2, g2]

-- [Int -> Int]
val64 = [g1 2 3, g2 4]

-- [Int]
val65 = [g1 2 3 4, g2 4 5]
