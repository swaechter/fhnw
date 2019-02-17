plusC :: Int -> Int -> Int
plusC x y = x + y

plusP :: (Int, Int) -> Int
plusP (x, y) = x + y

-- myuncurry plusC --> plusP
-- mycurry plusP --> plusC

myuncurry :: (a -> b -> c) -> ((a, b) -> c)
myuncurry f = \(x, y) -> f x y
-- oder myuncurry f (x, y) = f x y

mycurry :: ((a, b) -> c) -> (a -> b -> c)
mycurry f = \x y -> f (x, y)
-- oder mycurry f x y = f (x, y)

myuncurry plusC (3, 4)
mycurry plusP 3 4

map (myuncurry (+)) [(1, 2), (9, 3), (17, 2)] -- [3, 12, 19]

-- Situation: f (g(x))
-- Idee (f.g) x

map ((2*).(+1)) [1, 2, 3]

(.) :: (b -> c) -> (a -> b) -> a -> c
f.g = \x -> f(g(x))

-- Beispiel: compiler = codegen.typechecker.parser.scanner
