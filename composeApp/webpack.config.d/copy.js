const CopyWebpackPlugin = require('copy-webpack-plugin');

config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: require.resolve('sql.js/dist/sql-wasm.js'),
                to: 'sql.js'
            },
            {
                from: require.resolve('sql.js/dist/sql-wasm.wasm'),
                to: 'sql-wasm.wasm'
            }
        ]
    })
);

