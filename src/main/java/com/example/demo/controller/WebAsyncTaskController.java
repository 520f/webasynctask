package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 *
 * 描述：springboot中替代servlet3.0异步请求的解决方案
 *                   新的解决方案使用线程池也特别方便
 * User:xuyalun
 * Date 2018/9/13
 * Time 10:41
 *
 * remark：
 *      case 1 :任务处理时间5s,不超时，打开38,39行注释
 *      case 2 :任务抛出异常 顺利执行，打开42,43行注释
 *      case 3 :任务执行超时 ，打开46,47行注释
 *      加强版：使用线程池，打开 35行的行内注释
 */
@RestController
public class WebAsyncTaskController {
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor executor;
    @RequestMapping("/webAsynctask")
    public WebAsyncTask webAsyncTask() {
        // 打印处理线程名
        System.out.println("The main Thread name is " + Thread.currentThread().getName());

        // 此处模拟开启一个异步任务,超时时间为10s
        WebAsyncTask task = new WebAsyncTask(10 * 1000L,/*executor,*/ () -> {
            System.out.println("task Thread name is " + Thread.currentThread().getName());
            /**=====================case 1 :任务处理时间5s,不超时 开始========================**/
            /* Thread.sleep(5 * 1000L);
            return "任务顺利执行成功！任何异常都没有抛出！";*/
            /**=====================case 1 :任务处理时间5s,不超时 结束========================**/
            /**=====================case 2 :任务抛出异常 开始           ========================**/
            /*  int i=1/0;
            return "任务抛出了异常！";*/
            /**=====================case 2 :任务抛出异常 结束           ========================**/
            /**=====================case 3 :任务执行超时 开始           ========================**/
            Thread.sleep(11 * 1000L);
            return "任务发生超时啦！任何发生超时啦！";
            /**=====================case 3 :任务执行超时 结束           ========================**/
        });

        // 任务执行完成时调用该方法
        task.onCompletion(() -> {
            System.out.println("====================================" + Thread.currentThread().getName()
                    + "==============================");
            System.out.println("任务执行完成啦！");
        });
        // 发生异常时调用该方法
        task.onError(() -> {
            System.out.println("====================================" + Thread.currentThread().getName()
                    + "==============================");
            System.out.println("任务发生error啦！");
            return "";
        });
        // 任务超时调用该方法
        task.onTimeout(() -> {
            System.out.println("====================================" + Thread.currentThread().getName()
                    + "==============================");
            return "任务发生超时啦！";
        });
        System.out.println("task继续处理其他事情！");
        return task;
    }
}
