
module.exports = {
    // ...other config
    "plugins": ["jsdoc"],
    "parserOptions": {
      'ecmaVersion': 'latest',
      'sourceType': 'script' //or module.
    },
    "rules": {
        "jsdoc/require-description": "error",
        "jsdoc/check-values": "error",
        "jsdoc/require-description": "error",
        "jsdoc/check-values": "error",
        "jsdoc/check-alignment": "error",
        "jsdoc/check-param-names": "error",
        "jsdoc/check-property-names": "error",
    }
    // ...other config
};