import js from "@eslint/js";
export default [
    {
        plugins: {
                  "currentRule" : js,
        },
        rules: {
            "currentRule/eqeqeq": "error",
            "currentRule/no-unused-vars": "error",
            "currentRule/prefer-const": ["error", { "ignoreReadBeforeAssign": true }],
            "currentRule/no-undefined": "error",
            "currentRule/no-undef": "error",
            "currentRule/no-redeclare": "error",
            "currentRule/no-labels": "error",
            "currentRule/new-cap": "error",
            "currentRule/no-return-assign": "error",
            "currentRule/no-useless-constructor": "error",
            "currentRule/jsdoc/require-description": "error",
        }
    }
];