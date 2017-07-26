package com.wanyueliang.ffmpeg.utils;

import java.util.ArrayList;

public class FFmpegHepler {

    /*裁剪*/
/*精准裁剪 http://trac.ffmpeg.org/wiki/Seeking*/
//      ffmpeg -ss 00:00:00 -t 00:00:30 -i test.mp4 -vcodec copy -acodec copy output.mp4
//      ffmpeg -y -i filename -ss start -t duratio -codec copy
//      ffmpeg -ss 10 -t 15 -accurate_seek -i test.mp4 -codec copy -avoid_negative_ts 1 cut.mp4
//      ffmpeg -ss 00:03:00 -i video.mp4 -t 60 -c copy -avoid_negative_ts 1 cut.mp4
/*精准裁剪 http://trac.ffmpeg.org/wiki/Seeking*/
//      ffmpeg -i INPUT -sameq -intra OUTPUT
//      ffmpeg -y -ss start -t duration -I filenam -c:v libx264 -perset superfast -c:a copy
//      ffmpeg -ss 00:50:00  -i RevolutionOS.rmvb sample.jpg  -r 1 -vframes 1 -an -vcodec mjpeg

    /**
     * 裁剪视频
     */
    public static String[] cutVideo(String input, String startTime, String endTime, String outPut) {
        ArrayList<String> commands = new ArrayList<>();

        commands.add("ffmpeg");
        commands.add("-y");
        commands.add("-ss");
        commands.add(startTime);
        commands.add("-i");
        commands.add(input);
        commands.add("-t");
        commands.add(endTime);
        commands.add("-c");
        commands.add("copy");
        commands.add("-avoid_negative_ts");
        commands.add("1");
        commands.add(outPut);
        return commands.toArray(new String[0]);
    }

    /**
     * 裁剪视频的某一帧
     */
    public static String[] cutFrameVideo(String input, String startTime, String outPut) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-y");
        commands.add("-ss");
        commands.add(startTime);
        commands.add("-i");
        commands.add(input);
        commands.add(outPut);
        commands.add("-r");
        commands.add("1");
        commands.add("-vframes");
        commands.add("1");
        commands.add("-an");
        commands.add("-vcodec");
        commands.add("mjpeg");
        return commands.toArray(new String[0]);
    }

    //        ffmpeg -i vid.avi -y -f image2 -vf fps=fps=1 foo-%03d.jpeg
    //        ffmpeg -ss 10 -i input.flv -y -f image2  -vframes 100 -s 352x240 b-%03d.jpg

    /**
     * 连续裁剪视频的帧
     */
    public static String[] cutVideoToArrayImage(String input, String startTime, String outputJpgArray) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-y");
        commands.add("-ss");
        commands.add(startTime);
        commands.add("-i");
        commands.add(input);
        commands.add("-f");
        commands.add("image2");
        commands.add("-vf");
        commands.add("fps=fps=1/20");
        commands.add(outputJpgArray);
        return commands.toArray(new String[0]);
    }

    /**
     * 图片转视频
     */
    public static String[] imageToVideo(String intPutJpgArray, String frameValue, String outputMp4) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-i");
        commands.add(intPutJpgArray);
        commands.add("-r");
        commands.add(frameValue);
        commands.add(outputMp4);
        return commands.toArray(new String[0]);
    }
}
