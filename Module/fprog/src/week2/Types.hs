-- [Char]
list1 = ['a', 'b', 'c']

-- (Char,Char,Char)
tuple1 = ('a', 'b', 'c')

-- [(Bool,Char)]
list2 = [(False, '0'), (True, '1')]

-- ([Bool],[Char])
tuple2 = ([False, True], ['0', '1'])

-- [[a] -> [a]]
list3 = [tail, init, reverse]

-- [a] -> a
second xs = head (tail xs)

-- (a,b) -> (a,b)
swap (x, y) = (y, x)

-- a -> b -> (a,b)
pair x y = (x, y)

-- Num a => a -> a
double x = x * 2

-- Eq a => [a] -> Bool
palindrome xs = reverse xs == xs

-- (t -> t) -> t -> t
twice f x = f (f x)
