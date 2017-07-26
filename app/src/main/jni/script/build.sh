
#FFMpeg 的脚本，测试通过
# 基于 ffmpeg 3.3.2,windows
#!/bin/bash
export TMPDIR=E:/ffmpeg-3.3.2/ffmpeg-3.3.2/Android_libs_One #设置编译中临时文件目录，不然会报错 unable to create temporary file

# NDK的路径，根据自己的安装位置进行设置
NDK=C:/Users/Herve/AppData/Local/Android/Sdk/ndk-bundle
# 系统平台
HOST_PLATFORM=windows-x86_64
# 编译针对的平台，可以根据自己的需求进行设置
# 这里选择最低支持android-14, arm架构，生成的so库是放在
# libs/armeabi文件夹下的，若针对x86架构，要选择arch-x86
SYSROOT=$NDK/platforms/android-14/arch-arm/

# 工具链的路径，根据编译的平台不同而不同
# arm-linux-androideabi-4.9与上面设置的PLATFORM对应，4.9为工具的版本号，
# 根据自己安装的NDK版本来确定，一般使用最新的版本
TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/$HOST_PLATFORM
function build_one
{
echo "----------------ffmepg configure--------------------------"
./configure \
 --prefix=$PREFIX \
 --disable-shared \
        --enable-pthreads \
        --enable-gpl \
        --enable-version3 \
        --enable-nonfree \
        --enable-static \
        --enable-ffmpeg \
        --disable-ffplay \
        --disable-ffprobe \
        --disable-ffserver \
        --disable-doc \
        --disable-symver \
        --enable-avdevice \
        --enable-avfilter \
        --enable-libx264 \
        --enable-small \
        --enable-asm \
        --enable-neon \
        --target-os=linux \
        --arch=arm \
        --enable-cross-compile \
        --enable-runtime-cpudetect \
        --sysroot=$SYSROOT \
        --disable-parsers \
        --enable-parser=aac \
        --enable-parser=h264 \
        --enable-parser=mjpeg \
        --enable-parser=png \
        --enable-parser=bmp \
        --enable-parser=mpegvideo \
        --enable-parser=mpegaudio \
        --disable-encoders \
        --enable-encoder=h263 \
        --enable-encoder=libx264 \
        --enable-encoder=mpeg4 \
        --enable-encoder=aac \
        --enable-encoder=png \
        --enable-encoder=gif \
        --enable-encoder=bmp \
        --disable-decoders \
        --enable-decoder=h263 \
        --enable-decoder=h264 \
        --enable-decoder=mpeg4 \
        --enable-decoder=mjpeg \
        --enable-decoder=gif \
        --enable-decoder=mp3 \
        --enable-decoder=aac \
        --enable-decoder=png \
        --enable-decoder=bmp \
        --enable-decoder=yuv4 \
        --disable-network \
        --enable-protocols \
        --enable-protocol=concat \
        --enable-protocol=hls \
        --enable-protocol=file \
        --disable-demuxers \
        --enable-demuxer=h263 \
        --enable-demuxer=h264 \
        --enable-demuxer=flv \
        --enable-demuxer=gif \
        --enable-demuxer=aac \
        --enable-demuxer=ogg \
        --enable-demuxer=dts \
        --enable-demuxer=mp3 \
        --enable-demuxer=mov \
        --enable-demuxer=m4v \
        --enable-demuxer=concat \
        --enable-demuxer=mpegts \
        --enable-demuxer=mjpeg \
        --enable-demuxer=mpegvideo \
        --enable-demuxer=rawvideo \
        --enable-demuxer=yuv4mpegpipe \
        --disable-muxers \
        --enable-muxer=h264 \
        --enable-muxer=flv \
        --enable-muxer=gif \
        --enable-muxer=mp3 \
        --enable-muxer=dts \
        --enable-muxer=mp4 \
        --enable-muxer=mov \
        --enable-muxer=mpegts \
        --disable-filters \
        --enable-filter=aresample \
        --enable-filter=asetpts \
        --enable-filter=setpts \
        --enable-filter=ass \
        --enable-filter=scale \
        --enable-filter=concat \
        --enable-filter=atempo \
        --enable-filter=movie \
        --enable-filter=overlay \
        --enable-filter=rotate \
        --enable-filter=transpose \
        --enable-filter=hflip \
 --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
 --target-os=linux \
 --arch=arm \
 --cc=$TOOLCHAIN/bin/arm-linux-androideabi-gcc \
 --nm=$TOOLCHAIN/bin/arm-linux-androideabi-nm \
 --enable-cross-compile \
 --sysroot=$SYSROOT \
 --extra-cflags="-Os -fpic $ADDI_CFLAGS" \
 --extra-ldflags="$ADDI_LDFLAGS" \
 $ADDITIONAL_CONFIGURE_FLAG
 echo "----------------ffmepg build 1--------------------------"

make clean
make -j4
make install

$TOOLCHAIN/bin/arm-linux-androideabi-ld \
    -rpath-link=$SYSROOT/usr/lib \
    -L$SYSROOT/usr/lib \
    -L$PREFIX/lib \
    -soname libffmpeg.so -shared -nostdlib -Bsymbolic --whole-archive --no-undefined -o \
    $PREFIX/libffmpeg.so \
    $PREFIX/lib/libavcodec.a $PREFIX/lib/libavfilter.a \
    $PREFIX/lib/libavformat.a $PREFIX/lib/libavutil.a \
    $PREFIX/lib/libswresample.a $PREFIX/lib/libswscale.a \
    -lc -lm -lz -ldl -llog --dynamic-linker=/system/bin/linker \
    $TOOLCHAIN/lib/gcc/arm-linux-androideabi/4.9.x/libgcc.a
}
CPU=armV7-a
ADDI_CFLAGS="-marm"
PREFIX=$(pwd)/android/$CPU
build_one