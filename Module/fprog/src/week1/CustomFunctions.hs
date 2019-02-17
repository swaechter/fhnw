-- Exercise 1
customLastV1 xs = head (reverse xs)
customLastV2 xs = xs !! ((length xs) - 1)

-- Exercise 2
customInitV1 xs = reverse (tail (reverse xs))
customInitV2 xs = reverse (drop 1 (reverse xs))
