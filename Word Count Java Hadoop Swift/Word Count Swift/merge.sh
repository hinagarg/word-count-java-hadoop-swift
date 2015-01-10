#!/bin/bash
files=(f*.out);
declare -A merge;
START=$(date +%s);
for file in "${files[@]}"; do
    echo "Reading file $file";
    while IFS=$'\n' read -r line || [[ -n "$line" ]]; do
        temp=(${line/ - / });
        word=`tr -cd a-zA-Z <<<"${temp[0]}"`;
        count=`tr -cd 0-9 <<<"${temp[1]}"`;
        if [[ ${merge[$word]} == "" ]]; then
            merge["$word"]="$count";
        else
            new_count="${merge[$word]}";
            new_count=$(($new_count+$count));
            merge["$word"]="$new_count";
        fi
    done < "$file";
done
END=$(date +%s)
for i in "${!merge[@]}"
do
   echo "$i - ${merge[$i]}" >> $1
done
DIFF=$(( $END - $START ))
echo "It took $DIFF seconds"
exit 0