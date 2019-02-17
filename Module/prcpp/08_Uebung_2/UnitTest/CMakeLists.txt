find_package(Qt5Core REQUIRED)
find_package(Qt5Test REQUIRED)

set(CMAKE_AUTOMOC ON)

add_executable(unittest UnitTest.cpp)
target_link_libraries(unittest myset Qt5::Test)
add_test(NAME unittest COMMAND unittest)
