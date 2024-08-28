import js from "@eslint/js";
import jsdoc from "eslint-plugin-jsdoc";
import react from "eslint-plugin-react";
// import tseslint from 'typescript-eslint'; //typescript does not support Eslint 9.x as of Jul 2024.

export default [
    js.configs.recommended,
    {
        plugins: {
            "jsdoc": jsdoc,
            "react": react,
            "js" : js,
        },
        rules: {
            "js/no-unused-vars":"error",
            "js/no-undefined": "error",
            "js/o-undef": "error",
            "js/no-redeclare": "error",
            "js/no-labels": "error",
            "js/new-cap": "error",
            "js/no-return-assign": "error",
            "js/no-useless-constructor": "error",
           "jsdoc/require-description": "error",
           "jsdoc/check-values": "error",
           "jsdoc/check-alignment": "error",
           "jsdoc/check-param-names": "error",
           "jsdoc/check-property-names": "error",
        },
    }
];