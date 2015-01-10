#! /bin/bash
declare -A occur
START=$(date +%s)
printf "test"
while read line; do
   for word in $line; do
    if [[ ${occur[$word]} == "" ]]; then
        occur["$word"]="1";
    else
    count="${occur[$word]}";
    count=$((count+1));
    occur["$word"]="$count";
    fi
    done
done < $1
END=$(date +%s)
for i in "${!occur[@]}"
do
  echo "$i - ${occur[$i]}"
done
DIFF=$(( $END - $START ))
echo "It took $DIFF seconds"
exit 0
