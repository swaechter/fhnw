--   Type     Values
data Answer = Yes | No | Unknown

answers :: [Answer] -- Types
answers = [No, Unknown, Yes] -- Values

data Shape = Circle Float | Rect Float Float
--   Type    Value

-- Circle :: Float -> Shape
-- Circle 5.0 :: Shape

-- Rect :: Float -> Float -> Shape
-- Rect 5.0 :: Float -> Shape
-- Rect 5.0 6.0 :: Shape
