-- Exercise 6
h1 x = (x, x, x)
h2 x = [x, x, x]
h3 x = [(x, x), (x, x)]
h4 x y = (x, y)
h5 x y = [x, y]

-- h1 :: a -> (a, a, a), but not working
-- val1 = h1

-- h2 :: a -> [a], but not working
-- val2 = h2

-- h3 :: a -> [(a, a)], but not working
-- val3 = h3

-- h4 :: a -> b -> (a, b), but not working
-- val4 = h4

-- h5 :: a -> a -> [a], but not working
-- val5 = h5

-- h1 'a' :: (Char, Char, Char)
val6 = h1 'a'

-- h1 True :: (Bool, Bool, Bool)
val7 = h1 True

-- h4 'a' "True" :: (Char, String)
val8 = h4 'a' "True"

-- h5 'a' "True" :: Type error
-- val9 = h5 'a' "True"

-- h5 True True :: [True]
val10 = h5 True True

-- [] :: [a]
val11 = []

-- () :: ()
val12 = ()

-- head [] :: a, but not working
val13 = head []

-- Not working, because head expects a list
-- val14 = head ()
