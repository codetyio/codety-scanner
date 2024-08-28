import htmlPlugin from "@html-eslint/eslint-plugin";
import htmlParser from '@html-eslint/parser';
export default [
    {
        ... htmlPlugin.configs["flat/recommended"],
        files: ["**/*.html", "**/*.htm"], //required
        languageOptions: { //required
          parser: htmlParser,
          parserOptions : { project:true },
        },
        plugins: { //required
          "@html-eslint": htmlPlugin,
        },
        rules: {
           "@html-eslint/require-button-type": "error",
           "@html-eslint/no-duplicate-id": "error",
        },
    }
];