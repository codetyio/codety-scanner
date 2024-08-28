//
module.exports = {

  parserOptions: {
    'ecmaVersion': 13,
    'sourceType': 'module'
  },
   plugins:[
      "vue",
      "jsdoc",
      "react",
  ],
    rules: {
            "semi": "error",
            "vue/multi-word-component-names": "error",
            "vue/no-deprecated-data-object-declaration": "error",
            "vue/no-deprecated-destroyed-lifecycle": "error",
            "vue/no-deprecated-dollar-listeners-api": "error",
            "vue/no-deprecated-v-bind-sync": "error",
            "vue/no-parsing-error": "error",
            "strict": "error",
            "no-undefined": "error",
            "no-undef": "error",
            "no-redeclare": "error",
            "no-labels": "error",
            "new-cap": "error",
            "no-return-assign": "error",
            "no-useless-constructor": "error",
    'react/forbid-dom-props': ['off', { forbid: [] }],
    'react/jsx-boolean-value': ['error', 'never', { always: [] }],
    'react/jsx-closing-bracket-location': ['error', 'line-aligned'],
    'react/jsx-closing-tag-location': 'error',
    'react/jsx-curly-spacing': ['error', 'never', { allowMultiline: true }],
    'vue/no-dupe-keys': "error",
    }

}