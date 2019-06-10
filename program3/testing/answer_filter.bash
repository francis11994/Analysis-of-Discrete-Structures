#!/bin/bash
sed -n "/answer:/s/.*answer: \(.*\)/\1/p"
