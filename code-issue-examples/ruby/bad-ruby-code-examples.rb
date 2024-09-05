# bad Unused Variables Prefix
result = hash.map { |k, v| v + 1 }

def something(x)
  unused_var, used_var = something_else(x)
  # some code
end

# bad Parallel Assignment
a, b, c, d = 'foo', 'bar', 'baz', 'foobar'

# bad - identifier is a Bulgarian word, using non-ascii (Cyrillic) characters
заплата = 1_000


# bad Raising Explicit RuntimeError
raise RuntimeError, 'message'