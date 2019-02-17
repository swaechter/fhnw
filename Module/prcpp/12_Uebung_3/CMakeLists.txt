cmake_minimum_required(VERSION 3.0)
project(Uebung3)

find_package(Qt5Core REQUIRED)
find_package(Qt5Test REQUIRED)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)

enable_testing()

add_executable(unittest UnitTest.cpp UnitTest.h Vector.h Op.h Expression.h)
target_link_libraries(unittest Qt5::Test)
add_test(NAME unittest COMMAND unittest)
