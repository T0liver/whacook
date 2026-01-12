const CopyWebpackPlugin = require('copy-webpack-plugin');

config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: require.resolve('@cashapp/sqldelight-sqljs-worker/sqljs.worker.js'),
                to: 'sqlite.worker.js'
            },
            {
                from: require.resolve('sql.js/dist/sql-wasm.wasm'),
                to: 'sql-wasm.wasm'
            }
        ]
    })
);

