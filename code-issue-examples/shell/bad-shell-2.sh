
echo 'Path is $PATH'              # Variables in single quotes
trap "echo Took ${SECONDS}s" 0    # Prematurely expanded trap
unset var[i]                      # Array index treated as glob
