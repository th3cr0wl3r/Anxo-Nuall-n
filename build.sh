#!/bin/bash
set -e
cd "$( dirname "${BASH_SOURCE[0]}" )"
mkdir -p build
javac -d build xxx/hairyhamster/namefinder/Namefinder.java

cd build
jar cfe namefinder.jar xxx.hairyhamster.namefinder.Namefinder xxx/hairyhamster/namefinder/Namefinder.class
cd ..
