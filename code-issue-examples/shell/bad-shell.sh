 echo $1                           # Unquoted variables
 find . -name *.ogg                # Unquoted find/grep patterns
 rm "~/my file.txt"                # Quoted tilde expansion
 v='--verbose="true"'; cmd $v      # Literal quotes in variables
 for f in "*.ogg"                  # Incorrectly quoted 'for' loops
 touch $@                          # Unquoted $@
 echo 'Don't forget to restart!'   # Singlequote closed by apostrophe
 echo 'Don\'t try this at home'    # Attempting to escape ' in ''
 echo 'Path is $PATH'              # Variables in single quotes
 trap "echo Took ${SECONDS}s" 0    # Prematurely expanded trap
 unset var[i]                      # Array index treated as glob


 find /src -type f \( -name '*.sh' -o -name '*.bash' -o -name '*.ksh' -o -name '*.bashrc' -o -name '*.bash_profile' -o -name '*.bash_login' -o -name '*.bash_logout' \) | xargs shellcheck