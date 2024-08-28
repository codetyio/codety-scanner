module.exports = {
   extends: [
    "plugin:@typescript-eslint/eslint-recommended",
    "plugin:@typescript-eslint/recommended",
  ],

  parser: "@typescript-eslint/parser",
  plugins: [
    "@typescript-eslint"
  ],
  parserOptions: {
    'ecmaVersion': 13,
    'sourceType': 'module',
     project:true
  },
    rules: {
        "no-array-delete":"error"
    }

}