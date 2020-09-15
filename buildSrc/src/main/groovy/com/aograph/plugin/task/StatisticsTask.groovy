package com.aograph.plugin.task

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskState

/**
 * Created by tangqipeng on 2020/9/11.
 * email: tangqipeng@aograph.com
 */
class StatisticsTask extends DefaultTask {

    Project project
    def init(Project project){
        project = project
    }

    @TaskAction
    def statisticsTask(){
        println("p is "+project.path)
        final BuildTimeCostExtension timeCostExt = project.getExtensions().create("taskExecTime", BuildTimeCostExtension.class)
        timeCostExt.threshold = 1000
        timeCostExt.sorted = false

        //这里是监控task
        project.getGradle().addListener(new TaskExecutionListener() {
            @Override
            public void beforeExecute(Task task) {
                TaskExecTimeInfo info = new TaskExecTimeInfo();
                info.start = System.currentTimeMillis();
                info.path = task.getPath();
                timeInfoMap.put(task.getPath(), info);
                taskPathList.add(task.getPath());
            }

            @Override
            public void afterExecute(Task task, TaskState taskState) {
                TaskExecTimeInfo info = timeInfoMap.get(task.getPath());
                if (null != info){
                    info.end = System.currentTimeMillis();
                    info.total = info.end - info.start;
                }
            }
        });

        project.getGradle().addBuildListener(new BuildListener() {
            @Override
            public void buildStarted(Gradle gradle) {

            }

            @Override
            public void settingsEvaluated(Settings settings) {

            }

            @Override
            public void projectsLoaded(Gradle gradle) {

            }

            @Override
            public void projectsEvaluated(Gradle gradle) {

            }

            @Override
            public void buildFinished(BuildResult buildResult) {
                System.out.println("----------------------------------");
                System.out.println("-----------buildFinished----------");
                System.out.println("task size === "+taskPathList.size());

                if (timeCostExt.sorted){
                    List<TaskExecTimeInfo> taskExecTimeInfos = new ArrayList<>();
                    for (Map.Entry<String, TaskExecTimeInfo> entry : timeInfoMap.entrySet()) {
                        taskExecTimeInfos.add(entry.getValue());
                    }
                    Collections.sort(taskExecTimeInfos, new Comparator<TaskExecTimeInfo>() {
                        @Override
                        public int compare(TaskExecTimeInfo taskExecTimeInfo, TaskExecTimeInfo t1) {
                            return (int) (taskExecTimeInfo.total - t1.total);
                        }
                    });
                    for (TaskExecTimeInfo timeInfo : taskExecTimeInfos) {
                        if (timeInfo.total >= timeCostExt.threshold) {
                            System.out.println("info === " + timeInfo.toString());
                        }
                    }
                }else {
                    for (String path : taskPathList) {
                        TaskExecTimeInfo info = timeInfoMap.get(path);
//                        if (info.total >= timeCostExt.threshold) {
//                            System.out.println("info === " + info.toString());
//                        }
                        System.out.println("info === " + info.toString());
                    }
                }
            }
        });
    }

    static class TaskExecTimeInfo {
        long total
        String path
        long start
        long end

        @Override
        public String toString() {
            return "{" +
                    "total=" + total +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    static class BuildTimeCostExtension {

        public BuildTimeCostExtension() {
        }

        //task执行时间超过该值才会统计
        int threshold

        //是否按照task执行时长进行排序，true-表示从大到小进行排序，false-表示不排序
        boolean sorted

    }

}
