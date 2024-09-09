
 echo 'Path is $PATH'              # Variables in single quotes
 trap "echo Took ${SECONDS}s" 0    # Prematurely expanded trap
 unset var[i]                      # Array index treated as glob


 find /src -type f \( -name '*.sh' -o -name '*.bash' -o -name '*.ksh' -o -name '*.bashrc' -o -name '*.bash_profile' -o -name '*.bash_login' -o -name '*.bash_logout' \) | xargs shellcheck