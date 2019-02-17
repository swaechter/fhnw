add_library(myset STATIC MySet.cpp OrderedSet.cpp)
set_property(TARGET myset PROPERTY INTERFACE_INCLUDE_DIRECTORIES ${CMAKE_CURRENT_SOURCE_DIR})
