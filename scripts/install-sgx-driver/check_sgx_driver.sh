#!/bin/bash
ls /dev/isgx >/dev/null 2>1  && echo "SGX driver installed" || echo "SGX driver NOT installed"