#!/bin/bash

mysql --host=127.0.0.1 --port=13306 --user=root --password=password --database=pinch_hit --comments < "dump-pinch_hit-202103241343.sql"

