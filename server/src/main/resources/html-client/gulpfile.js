var path = require('path');
var gulp = require('gulp');
var webpack = require('webpack-stream');
var webserver = require('gulp-webserver');
var Proxy = require('gulp-connect-proxy');

function pack(entry, root, fileName, libraryName) {
    return gulp.src(entry)
        .pipe(webpack({
            resolve: {
                root: path.resolve(__dirname, root),
                fallback: path.resolve(__dirname, 'node_modules')
            },
            output: {
                library: libraryName,
                filename: fileName
                //libraryTarget: 'amd' // our client wants to load our library via AMD
            },
            module: {
                loaders: [
                    {loader: 'babel-loader'},
                    {test: /\.html$/, loader: 'html'}
                ]
            }
        }))
}

gulp.task('build', function () {
    //noinspection JSUnresolvedVariable
    return [
        pack('src/main/ogrid.js', 'src/main', 'ogrid-client.js', 'ogrid').pipe(gulp.dest('./dist')),
        pack('src/test/simple/index.js', 'src/test/simple', 'ogrid-test.js', 'ogrid-test').pipe(gulp.dest('./dist/test'))
    ];
});

gulp.task('default', ['build']);

gulp.task('server', function () {
    gulp.src('.')
        .pipe(webserver({
            livereload: false,
            directoryListing: true,
            open: true,
            proxies: [
                {
                    source: '/google',
                    target: 'https://docs.google.com/'
                }
            ]
        }));
});