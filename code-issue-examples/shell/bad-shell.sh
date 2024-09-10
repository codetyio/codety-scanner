 echo $1                           # Unquoted variables
 find . -name *.ogg                # Unquoted find/grep patterns
 rm "~/my file.txt"                # Quoted tilde expansion
 v='--verbose="true"'; cmd $v      # Literal quotes in variables

find /src -type f \( -name '*.sh' -o -name '*.bash' -o -name '*.ksh' -o -name '*.bashrc' -o -name '*.bash_profile' -o -name '*.bash_login' -o -name '*.bash_logout' \) | xargs shellcheck
