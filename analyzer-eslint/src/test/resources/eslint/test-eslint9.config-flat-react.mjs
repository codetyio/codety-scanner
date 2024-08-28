//v9
import react from "eslint-plugin-react";
export default [
    {
    "env": {
        "es9": true,
        "browser": true,
        "node": true
      },
        files: ['**/*.{js}'],
        "parser": "@babel/eslint-parser",
        "parserOptions": {
            'sourceType': 'script',
            "ecmaFeatures": {
              "jsx": true
            }
          },
        plugins: {
                  "currentRule" : react,
        },
        rules: {
            'currentRule/forbid-dom-props': ['off'],
            'currentRule/no-namespace': ['off'],
            'currentRule/no-set-state': ['off'],
//             'currentRule/jsx-closing-bracket-location': ['error', 'line-aligned'],
//             'currentRule/jsx-closing-tag-location': 'error',
//             'currentRule/jsx-curly-spacing': ['error', 'never', { allowMultiline: true }],
        }
    }
];