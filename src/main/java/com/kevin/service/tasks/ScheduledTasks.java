package com.kevin.service.tasks;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    //输出时间格式
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(initialDelay = 5000, fixedRate = 5000)  //系统启动后每隔5s触发
//    public void firstScheduledTasks(){
//        System.err.println("定时任务执行(方式一)，现在时间是 : "+ dateFormat.format(new Date()));
//    }
//
//    @Scheduled(cron="0 35 10 * * ?")        //每天上午10:35触发
//    private void secondScheduledTasks(){
//        System.out.println("定时任务执行（方式二)，现在时间是  "+ dateFormat.format(new Date()));
//    }

/*
 *   @Scheduled接受两种定时的设置：
 *   一种是cornexpression。
 *   一种是Rate/Delay表达式（毫秒值）：
 *   @Scheduled(fixedRate = 6000)：上一次开始执行时间点后每隔6秒执行一次。
 *   @Scheduled(fixedDelay = 6000)：上一次执行完毕时间点之后6秒再执行。
 *   @Scheduled(initialDelay=1000, fixedRate=6000)：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次。
*/

  /*  注：
    　　*表示所有值，在分钟里表示每一分钟触发。在小时，日期，月份等里面表示每一小时，每一日，每一月。
    　　？表示不指定值。表示不关心当前位置设置的值。 比如不关心是周几，则周的位置填写？。　　主要是由于日期跟周是有重复的所以两者必须有一者设置为？
    　　- 表示区间。小时设置为10-12表示10,11,12点均会触发。
    　　，表示多个值。 小时设置成10,12表示10点和12点会触发。
    　　/ 表示递增触发。 5/15表示从第5秒开始，每隔15秒触发。
    　　L 表示最后的意思。 日上表示最后一天。星期上表示星期六或7。 L前加数据，表示该数据的最后一个。
    　　星期上设置6L表示最后一个星期五。  6表示星期五
　　    W表示离指定日期最近的工作日触发。15W离该月15号最近的工作日触发。
    　　#表示每月的第几个周几。 6#3表示该月的第三个周五。
    */

//示例：
//        "0 0 12 * * ?"                  每天中午12点触发
//　　　　"0 15 10 ? * *"                 每天上午10:15触发
//　　　　"0 15 10 * * ?"                 每天上午10:15触发
//　　　　"0 15 10 * * ? *"               每天上午10:15触发
//　　　　"0 15 10 * * ? 2005"            2005年的每天上午10:15触发
//　　　　"0 * 14 * * ?"                  在每天下午2点到下午2:59期间的每1分钟触发
//　　　　"0 0/5 14 * * ?"                在每天下午2点到下午2:55期间的每5分钟触发
//　　　　"0 0/5 14,18 * * ?"             在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
//　　　　"0 0-5 14 * * ?"                在每天下午2点到下午2:05期间的每1分钟触发
//　　　　"0 10,44 14 ? 3 WED"            每年三月的星期三的下午2:10和2:44触发
//　　　　"0 15 10 ? * MON-FRI"           周一至周五的上午10:15触发
//　　　　"0 15 10 15 * ?"                每月15日上午10:15触发
//　　　　"0 15 10 L * ?"                 每月最后一日的上午10:15触发
//　　　　"0 15 10 ? * 6L"                每月的最后一个星期五上午10:15触发
//　　　　"0 15 10 ? * 6L 2002-2005"      2002年至2005年的每月的最后一个星期五上午10:15触发
//　　　　"0 15 10 ? * 6#3"               每月的第三个星期五上午10:15触发
//　　　　"0 6 * * *"                     每天早上6点
//        "0 */2 * * *"                   每两个小时
//        "0 23-7/2，8 * * *"  　　　　   晚上11点到早上8点之间每两个小时，早上八点
//        "0 11 4 * 1-3"　　　　          每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
//        "0 4 1 1 *"   　　　　          1月1日早上4点
}
