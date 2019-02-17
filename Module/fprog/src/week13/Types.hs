data Shape = Circle Float | Rect Float Float

-- Umsetzung 1
allArea :: [Shape] -> Float
allArea [] = 0
allArea (x:xs) = area x (allArea xs)

-- Umsetzung 2
allArea = foldr (\x accu -> area x + accu) 0

-- Umsetzung 3
allArea = sum [area x | x <- xs]

area :: Shape -> Float
area (Rect w h) = w * h;
area (Circle r) = r * pi;

allArea[Rect 2 3, Circle 1, Rect 3 4] -- 6 + Pi + 12 = 21.412

-- Maybe
-- f :: Int -> Int -> Int
-- f x y = y * safediv x y
--      Int * Maybe Int

f :: Int -> Int -> Int
f x y = y * h (safediv x y)
	where
		h Nothing = 0
		h (Just x) = x
-- 		h :: Maybe a -> a
