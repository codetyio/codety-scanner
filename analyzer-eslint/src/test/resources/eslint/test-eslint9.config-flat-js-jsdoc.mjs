import jsdoc from "eslint-plugin-jsdoc";
export default [
    {
        plugins: {
                    jsdoc,
        },
        rules: {
            "jsdoc/require-description": "error",
           "jsdoc/check-values": "error",
           "jsdoc/check-alignment": "error",
           "jsdoc/check-param-names": "error",
           "jsdoc/check-property-names": "error",
        }
    }
];